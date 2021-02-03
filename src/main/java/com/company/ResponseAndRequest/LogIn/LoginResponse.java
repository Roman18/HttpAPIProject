package com.company.ResponseAndRequest.LogIn;


import lombok.Data;

@Data
public class LoginResponse {
    private String status;
    private String error;
    private String token;
}
