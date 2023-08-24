package com.socialportal.portal.controller;

import com.socialportal.portal.dto.LoginResponse;
import com.socialportal.portal.dto.RegisteredUserDto;
import com.socialportal.portal.pojo.request.LoginRequest;
import com.socialportal.portal.pojo.request.SignUpRequest;
import com.socialportal.portal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    ResponseEntity<RegisteredUserDto> signUp(@RequestPart("signUpRequest") SignUpRequest signUpRequest,
                                             @RequestPart(value = "image", required = false) MultipartFile image) {
        return new ResponseEntity<>(
                this.userService.signUp(signUpRequest, image),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    ResponseEntity<LoginResponse> logIn(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(this.userService.login(loginRequest));
    }

    @PutMapping(value = "/profilePic")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file, Authentication authentication) {
        return ResponseEntity.status(this.userService.updateProfilePicture(file, authentication)).build();
    }

    @GetMapping("/profilePic")
    public ResponseEntity<?> getImageInfoByName(Authentication authentication) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(this.userService.getProfilePic(authentication));
    }
}
