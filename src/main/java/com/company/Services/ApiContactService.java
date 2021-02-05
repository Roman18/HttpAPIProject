package com.company.Services;

import com.company.Exceptions.ProblemWithConnectException;
import com.company.Exceptions.StatusException;
import com.company.dto.Add.AddRequest;
import com.company.dto.Add.AddResponse;
import com.company.dto.GetAll.GetContactsResponse;
import com.company.dto.SearchByValue.SearchByNameRequest;
import com.company.dto.SearchByValue.SearchByValueRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ApiContactService implements ContactService {
    private UserService userService;
    private ObjectMapper objectMapper;
    private HttpClient httpClient;
    private String baseUri;

    @Override
    public void add(Contact contact) {
        AddRequest request = new AddRequest();
        request.setType(contact.getType().toString().toLowerCase());
        request.setName(contact.getName());
        request.setValue(contact.getContact());
        try {
            HttpRequest httpRequest = createAuthorizationRequest("/contacts/add", request);
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            AddResponse addResponse = objectMapper.readValue(response.body(), AddResponse.class);
            if (addResponse == null) {
                throw new ProblemWithConnectException("problem with connect");
            } else if ("ok".equals(addResponse.getStatus())) {
                throw new StatusException(addResponse.getError());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


    }



    @Override
    public void remove(Integer id) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("operation not supported");
    }

    @Override
    public List<Contact> findByName(String name) {
        System.out.println("=====Result from api=====");
        SearchByNameRequest searchByNameRequest = new SearchByNameRequest();
        searchByNameRequest.setName(name);
        try {
            String req = objectMapper.writeValueAsString(searchByNameRequest);
            HttpRequest httpRequest = HttpRequest.newBuilder().
                    uri(URI.create("https://mag-contacts-api.herokuapp.com/contacts/find"))
                    .POST(HttpRequest.BodyPublishers.ofString(req)).
                            header("Accept", "application/json").
                            header("Authorization", "Bearer " + userService.getToken()).
                            header("Content-Type", "application/json").
                            build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            GetContactsResponse getContactsResponse = objectMapper.readValue(response.body(), GetContactsResponse.class);
            if ("error".equals(getContactsResponse.getStatus())) {
                throw new StatusException(getContactsResponse.getError());
            }
            return getContactsResponse.getContacts().stream().
                    filter(c -> c.getName().startsWith(name))
                    .map(c -> {
                        Contact contact = new Contact();
                        contact.setType(Contact.TypeContact.valueOf(c.getType().toUpperCase()));
                        contact.setId(c.getId());
                        contact.setContact(c.getValue());
                        contact.setName(c.getName());
                        return contact;
                    }).collect(Collectors.toList());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Contact> findByValue(String value) {
        System.out.println("=====Result from api=====");
        SearchByValueRequest searchByNameRequest = new SearchByValueRequest();
        searchByNameRequest.setValue(value);
        try {
            String req = objectMapper.writeValueAsString(searchByNameRequest);
            HttpRequest httpRequest = HttpRequest.newBuilder().
                    uri(URI.create("https://mag-contacts-api.herokuapp.com/contacts/find"))
                    .POST(HttpRequest.BodyPublishers.ofString(req)).
                            header("Accept", "application/json").
                            header("Authorization", "Bearer " + userService.getToken()).
                            header("Content-Type", "application/json").
                            build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            GetContactsResponse getContactsResponse = objectMapper.readValue(response.body(), GetContactsResponse.class);
            if ("error".equals(getContactsResponse.getStatus())) {
                throw new StatusException(getContactsResponse.getError());
            }
            return getContactsResponse.getContacts().stream().
                    filter(c -> c.getValue().contains(value))
                    .map(c -> {
                        Contact contact = new Contact();
                        contact.setType(Contact.TypeContact.valueOf(c.getType().toUpperCase()));
                        contact.setId(c.getId());
                        contact.setContact(c.getValue());
                        contact.setName(c.getName());
                        return contact;
                    }).collect(Collectors.toList());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Contact> getAll() {
        System.out.println("=====Result from api=====");
        HttpRequest httpRequest = HttpRequest.newBuilder().
                uri(URI.create(baseUri + "/contacts")).
                GET()
                .header("Authorization", "Bearer " + userService.getToken())
                .headers("Content-type", "application/json")
                .build();

        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            GetContactsResponse getContactsResponse = objectMapper.readValue(response.body(), GetContactsResponse.class);
            if ("error".equals(getContactsResponse.getStatus())) {
                throw new StatusException(getContactsResponse.getError());
            }
            return getContactsResponse.getContacts().stream().map(c -> {
                Contact contact = new Contact();
                contact.setType(Contact.TypeContact.valueOf(c.getType().toUpperCase()));
                contact.setId(c.getId());
                contact.setContact(c.getValue());
                contact.setName(c.getName());


                return contact;
            }).collect(Collectors.toList());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    private HttpRequest createAuthorizationRequest(String path, AddRequest request) throws JsonProcessingException {
        return HttpRequest.newBuilder().
                uri(URI.create(baseUri + path)).
                POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(request)))
                .header("Authorization", "Bearer " + userService.getToken())
                .headers("Content-type", "application/json")
                .build();
    }
}
