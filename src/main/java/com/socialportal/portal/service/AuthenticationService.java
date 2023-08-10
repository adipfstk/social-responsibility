package com.socialportal.portal.service;

import com.socialportal.portal.dto.LoginResponse;
import com.socialportal.portal.dto.RegisteredUserDto;
import com.socialportal.portal.pojo.request.LoginRequest;
import com.socialportal.portal.pojo.request.SignUpRequest;
import org.springframework.security.core.AuthenticationException;



public interface AuthenticationService {
    RegisteredUserDto signUp(SignUpRequest signUpRequest);
    LoginResponse login(LoginRequest loginRequest)  throws AuthenticationException;
}
