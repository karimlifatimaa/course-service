package com.woofly.courseservice.dto.auth;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String token;
}
