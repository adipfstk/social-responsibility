package com.socialportal.portal.controller;


import com.socialportal.portal.dto.LoginResponse;
import com.socialportal.portal.dto.RegisteredUserDto;
import com.socialportal.portal.pojo.request.LoginRequest;
import com.socialportal.portal.pojo.request.SignUpRequest;
import com.socialportal.portal.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    ResponseEntity<RegisteredUserDto> signUp(@RequestBody SignUpRequest signUpRequest) {
        return new ResponseEntity<>(
                this.authenticationService.signUp(signUpRequest),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    ResponseEntity<LoginResponse> logIn(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(this.authenticationService.login(loginRequest));
    }
}
