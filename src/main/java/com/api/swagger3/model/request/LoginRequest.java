package com.api.swagger3.model.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String id;
    private String pw;
}
