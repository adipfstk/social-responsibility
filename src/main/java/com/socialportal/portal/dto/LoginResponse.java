package com.socialportal.portal.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String access;
    private String tokenType = "Bearer";
}
