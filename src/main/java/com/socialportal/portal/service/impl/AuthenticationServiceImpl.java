package com.socialportal.portal.service.impl;

import com.socialportal.portal.dto.LoginResponse;
import com.socialportal.portal.dto.RegisteredUserDto;
import com.socialportal.portal.exception.user.NoRolesDataBase;
import com.socialportal.portal.exception.user.UserAlreadyExists;
import com.socialportal.portal.pojo.request.LoginRequest;
import com.socialportal.portal.pojo.request.SignUpRequest;
import com.socialportal.portal.repository.RoleRepository;
import com.socialportal.portal.repository.UserEntityRepository;
import com.socialportal.portal.security.JwtService;
import com.socialportal.portal.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserEntityRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public RegisteredUserDto signUp(SignUpRequest signUpRequest)  {
        String username = signUpRequest.getUserEntity().getUsername();
        var roles = signUpRequest.getUserEntity().getRoles();

        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExists("The username/email is already taken");
        }

        var userToCommit = signUpRequest.getUserEntity();
        userToCommit.setUserLocation(signUpRequest.getGeoLocation());

        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new NoRolesDataBase("No role present in db"));
        roles.add(userRole);
        userToCommit.setRoles(roles);

        var bcryptPassword = userToCommit.getPassword();
        bcryptPassword = this.passwordEncoder.encode(bcryptPassword);
        userToCommit.setPassword(bcryptPassword);

        userRepository.save(userToCommit);
        return new RegisteredUserDto(userToCommit.getUsername(), "User registered successfully");
    }

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
}
