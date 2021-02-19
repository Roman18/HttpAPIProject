package com.company.Services;

import com.company.Exceptions.ProblemWithConnectException;
import com.company.Exceptions.StatusException;
import com.company.dto.Add.AddRequest;
import com.company.dto.Add.AddResponse;
import com.company.dto.GetAll.GetContactsResponse;
import com.company.dto.SearchByValue.SearchByNameRequest;
import com.company.dto.SearchByValue.SearchByValueRequest;
import com.company.factory.Requests.ContactServiceHttpRequestFactory;
import com.company.factory.Requests.HttpRequestFactory;
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


public class ApiContactService implements ContactService {
    private UserService userService;
    private ObjectMapper objectMapper;
    private HttpClient httpClient;
    private String baseUri;
    private final HttpRequestFactory factory;
    public ApiContactService(UserService userService, ObjectMapper objectMapper, HttpClient httpClient, String baseUri){
        this.userService=userService;
        this.objectMapper=objectMapper;
        this.httpClient=httpClient;
        this.baseUri=baseUri;
        this.factory=new ContactServiceHttpRequestFactory(baseUri,userService);
    }


    @Override
    public void add(Contact contact) {
        AddRequest request = new AddRequest();
        request.setType(contact.getType().toString().toLowerCase());
        request.setName(contact.getName());
        request.setValue(contact.getContact());
        try {
            HttpRequest httpRequest = factory.createPostRequest("/contacts/add",request);
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
            HttpRequest httpRequest = factory.createPostRequest("/find", searchByNameRequest);
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
            HttpRequest httpRequest = factory.createPostRequest("/find", searchByNameRequest);
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
        HttpRequest httpRequest = factory.createGetRequest("/contacts");
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
}
