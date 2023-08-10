package com.socialportal.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisteredUserDto {
    private String username;
    private String message;

}
