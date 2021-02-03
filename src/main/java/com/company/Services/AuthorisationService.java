package com.company.Services;

import com.company.ResponseAndRequest.LogIn.LoginRequest;
import com.company.ResponseAndRequest.LogIn.LoginResponse;

import com.company.ResponseAndRequest.SignUp.SignUpRequest;
import com.company.ResponseAndRequest.SignUp.SignUpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@RequiredArgsConstructor
public class AuthorisationService {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;


    public LoginResponse logIn(String login,String passwd){
        try {
            String req=objectMapper.writeValueAsString(new LoginRequest(login,passwd));
            HttpRequest httpRequest=HttpRequest.newBuilder().
                    uri(URI.create("https://mag-contacts-api.herokuapp.com/login"))
                    .POST(HttpRequest.BodyPublishers.ofString(req)).
                            header("Accept","application/json").
                            header("Content-Type","application/json").
                            build();
            HttpResponse <String> httpResponse=httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
             return objectMapper.readValue(httpResponse.body(), LoginResponse.class);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

return null;
    }

    public SignUpResponse singUp(String login,String passwd,String dateBorn){
        try {
            String req=objectMapper.writeValueAsString(new SignUpRequest(login,passwd,dateBorn));
            HttpRequest httpRequest=HttpRequest.newBuilder().
                    uri(URI.create("https://mag-contacts-api.herokuapp.com/register"))
                    .POST(HttpRequest.BodyPublishers.ofString(req)).
                            header("Accept","application/json").
                            header("Content-Type","application/json").
                            build();
            HttpResponse <String> httpResponse=httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
          return   objectMapper.readValue(httpResponse.body(), SignUpResponse.class);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;

    }

}
