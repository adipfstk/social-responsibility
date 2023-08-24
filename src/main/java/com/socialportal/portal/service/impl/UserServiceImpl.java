package com.socialportal.portal.service.impl;

import com.socialportal.portal.dto.LoginResponse;
import com.socialportal.portal.dto.RegisteredUserDto;
import com.socialportal.portal.exception.image.ImageUploadFailure;
import com.socialportal.portal.exception.user.NoRolesDataBase;
import com.socialportal.portal.exception.user.UserAlreadyExists;
import com.socialportal.portal.model.user.UserEntity;
import com.socialportal.portal.pojo.request.LoginRequest;
import com.socialportal.portal.pojo.request.SignUpRequest;
import com.socialportal.portal.repository.RoleRepository;
import com.socialportal.portal.repository.UserEntityRepository;
import com.socialportal.portal.security.JwtService;
import com.socialportal.portal.service.UserService;
import com.socialportal.portal.service.ImageService;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ImageService imageService;

    public UserServiceImpl(UserEntityRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtService jwtService,
                           @Qualifier(value = "userImageServiceImpl") ImageService imageService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.imageService = imageService;
    }

    @Override
    @Transactional
    public RegisteredUserDto signUp(SignUpRequest signUpRequest, MultipartFile profilePic) {
        String username = signUpRequest.getUserEntity().getUsername();

        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExists("The username/email is already taken");
        }

        UserEntity userToCommit = prepareUserForSignUp(signUpRequest);

        userRepository.save(userToCommit);

        if (profilePic != null) {
            uploadProfilePicture(profilePic, userToCommit.getId());
        }

        return new RegisteredUserDto(userToCommit.getUsername(), "User registered successfully");
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws AuthenticationException {
        UserDetails userCredentials = User
                .withUsername(loginRequest.getUsername())
                .password(loginRequest.getPassword())
                .build();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userCredentials.getUsername(), userCredentials.getPassword());
        this.authenticationManager.authenticate(token);
        LoginResponse jwtToken = new LoginResponse();
        jwtToken.setTokenType("Bearer");
        jwtToken.setAccess(jwtService.generateToken(userCredentials));
        return jwtToken;
    }

    @Override
    @Transactional
    public Integer updateProfilePicture(@NonNull MultipartFile image, Authentication authentication) {
        UserEntity user = getUserFromAuthentication(authentication);
        uploadProfilePicture(image, user.getId());

        return HttpStatus.CREATED.value();
    }

    @Override
    public byte[] getProfilePic(Authentication authentication) {
        UserEntity user = getUserFromAuthentication(authentication);
        return imageService.getImages(user.getId()).get(0);
    }

    private UserEntity prepareUserForSignUp(SignUpRequest signUpRequest) {
        UserEntity userToCommit = signUpRequest.getUserEntity();
        userToCommit.setUserLocation(signUpRequest.getGeoLocation());

        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new NoRolesDataBase("No role present in db"));

        userToCommit.setRoles(Set.of(userRole));

        String bcryptPassword = passwordEncoder.encode(userToCommit.getPassword());
        userToCommit.setPassword(bcryptPassword);

        return userToCommit;
    }

    private UserEntity getUserFromAuthentication(Authentication authentication) {
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Cannot locate user in db"));
    }

    private void uploadProfilePicture(MultipartFile image, Long userId) {
        if (!image.isEmpty()) {
            try {
                this.imageService.uploadImage(image, userId);
            } catch (IOException e) {
                throw new ImageUploadFailure("Image cannot be uploaded");
            }
        }
    }
}