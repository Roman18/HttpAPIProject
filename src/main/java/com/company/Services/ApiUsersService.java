package com.company.Services;

import com.company.Exceptions.StatusException;
import com.company.dto.User;
import com.company.dto.LogIn.LoginRequest;
import com.company.dto.LogIn.LoginResponse;
import com.company.dto.SignUp.SignUpRequest;
import com.company.dto.SignUp.SignUpResponse;
import com.company.factory.Requests.HttpRequestFactory;
import com.company.factory.Requests.UserServiceHttpRequestFactory;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


public class ApiUsersService implements UserService {
    private final String baseUri;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;
    private final HttpRequestFactory factory;
    public ApiUsersService(String baseUri, ObjectMapper objectMapper, HttpClient httpClient){
        this.baseUri = baseUri;
        this.objectMapper=objectMapper;
        this.httpClient = httpClient;
       this.factory = new UserServiceHttpRequestFactory(baseUri);
    }

    private String token;
    private LocalDateTime lct;

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public boolean isAuth() {
        if (token == null || lct == null) return false;
        return Duration.between(
                lct,
                LocalDateTime.now()).toMinutes() < 55;
    }

    @Override
    public void register(User user) {
        SignUpRequest signUpRequest=new SignUpRequest();
        signUpRequest.setLogin(user.getLogin());
        signUpRequest.setPassword(user.getPassword());
        signUpRequest.setDateBorn(user.getDateBorn().toString());
        try {

            HttpRequest httpRequest = factory.createPostRequest("/register",signUpRequest);
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            SignUpResponse signUpResponse= objectMapper.readValue(response.body(), SignUpResponse.class);

            if (!"ok".equals(signUpResponse.getStatus())){
                throw new StatusException(signUpResponse.getError());
            }
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void login(User user) {
        LoginRequest loginRequest=new LoginRequest();
        loginRequest.setPassword(user.getPassword());
        loginRequest.setLogin(user.getLogin());
        try {
            HttpRequest httpRequest = factory.createPostRequest("/login",loginRequest);
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            LoginResponse loginResponse = objectMapper.readValue(response.body(), LoginResponse.class);
            if ("ok".equals(loginResponse.getStatus())){
                token=loginResponse.getToken();
                lct=LocalDateTime.now();
            }else{
                throw new StatusException(loginResponse.getError());
            }
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAll() {

        return null;
    }



}
