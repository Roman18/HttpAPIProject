package com.company.Services;

import com.company.ResponseAndRequest.Add.AddRequest;
import com.company.ResponseAndRequest.Add.AddResponse;
import com.company.ResponseAndRequest.GetAll.GetAllResponse;
import com.company.ResponseAndRequest.SearchByValue.SearchByNameRequest;
import com.company.ResponseAndRequest.SearchByValue.SearchByValueResponse;
import com.company.ResponseAndRequest.SearchByValue.SearchParent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RequiredArgsConstructor
public class UserService {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;


    public GetAllResponse getAll(String token) {
        HttpRequest httpRequest = HttpRequest.newBuilder().
                uri(URI.create("https://mag-contacts-api.herokuapp.com/contacts")).
                GET().
                header("Authorization", "Bearer " + token).
                header("Accept", "application/json").
                build();
        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(),
                    GetAllResponse.class);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public AddResponse add(AddRequest user, String token) {

        try {
            String req = objectMapper.writeValueAsString(user);
            HttpRequest httpRequest = HttpRequest.newBuilder().
                    uri(URI.create("https://mag-contacts-api.herokuapp.com/contacts/add"))
                    .POST(HttpRequest.BodyPublishers.ofString(req)).
                            header("Accept", "application/json").
                            header("Authorization", "Bearer " + token).
                            header("Content-Type", "application/json").
                            build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(httpResponse.body(), AddResponse.class);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SearchByValueResponse searchByValue(SearchParent search, String token) {

        try {
            String req = objectMapper.writeValueAsString(search);
            HttpRequest httpRequest = HttpRequest.newBuilder().
                    uri(URI.create("https://mag-contacts-api.herokuapp.com/contacts/find"))
                    .POST(HttpRequest.BodyPublishers.ofString(req)).
                            header("Accept", "application/json").
                            header("Authorization", "Bearer " + token).
                            header("Content-Type", "application/json").
                            build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(httpResponse.body(), SearchByValueResponse.class);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;


    }
}


