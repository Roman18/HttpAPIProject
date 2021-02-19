package com.company.factory.Services;

import com.company.Services.ApiContactService;
import com.company.Services.ApiUsersService;
import com.company.Services.ContactService;
import com.company.Services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.net.http.HttpClient;

@RequiredArgsConstructor
public class ApiServiceFactory implements ServiceFactory{
    private final String baseUri;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newBuilder().build();
    @Override
    public UserService createUserService() {
        return new ApiUsersService(baseUri,objectMapper,httpClient);
    }

    @Override
    public ContactService createContactService(UserService userService) {
        return new ApiContactService(userService,objectMapper,httpClient,baseUri);
    }

}
