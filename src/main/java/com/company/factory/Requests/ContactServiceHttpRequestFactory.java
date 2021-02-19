package com.company.factory.Requests;

import com.company.Services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.net.http.HttpRequest;

@RequiredArgsConstructor
public class ContactServiceHttpRequestFactory implements HttpRequestFactory {
    private final String baseUri;
    private final UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public HttpRequest createGetRequest(String url) {
        return HttpRequest.newBuilder().
                uri(URI.create(baseUri + url)).
                GET()
                .header("Authorization", "Bearer " + userService.getToken())
                .header("Accept", "application/json")
                .headers("Content-type", "application/json")
                .build();
    }

    @Override
    public HttpRequest createPostRequest(String url, Object request) {
        try {
            return HttpRequest.newBuilder().
                    uri(URI.create(baseUri + url)).
                    POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(request)))
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer " + userService.getToken())
                    .headers("Content-type", "application/json")
                    .build();
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException();
        }
    }
}

