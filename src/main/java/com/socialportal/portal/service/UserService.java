package com.socialportal.portal.service;

import com.socialportal.portal.dto.LoginResponse;
import com.socialportal.portal.dto.RegisteredUserDto;
import com.socialportal.portal.dto.UserImageDto;
import com.socialportal.portal.model.user.UserImage;
import com.socialportal.portal.pojo.request.LoginRequest;
import com.socialportal.portal.pojo.request.SignUpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.multipart.MultipartFile;


public interface UserService {
    RegisteredUserDto signUp(SignUpRequest signUpRequest, MultipartFile profilePic);
    LoginResponse login(LoginRequest loginRequest) throws AuthenticationException;
    UserImageDto getProfilePic(long id);

}
