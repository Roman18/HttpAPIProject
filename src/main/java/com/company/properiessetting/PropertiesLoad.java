package com.company.properiessetting;

import com.company.Exceptions.PropertiesException;
import com.company.Serializer.ContactSerializer;
import com.company.Services.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.http.HttpClient;
import java.util.Properties;

public class PropertiesLoad {
    private String value;
    private Properties sprop = System.getProperties();
    private String mod;
    private String path;
    private String uri;
    private UserService userService;
    private ContactService[] contactServices;

    public PropertiesLoad() {
        this.value = (String) sprop.get("contactbook.profile");
    }

    public void loadProp() throws PropertiesException {
        checkProp();
        HttpClient httpClient = HttpClient.newBuilder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        this.mod = sprop.getProperty("app.service.workmode");
        switch (this.mod) {
            case "api":
                this.uri = sprop.getProperty("api.base-uri");
                validProp(this.uri);
                this.userService = new ApiUsersService(
                        this.uri,
                        objectMapper,
                        httpClient);
                this.contactServices = new ContactService[]{new ApiContactService(userService,
                        objectMapper, httpClient, this.uri)};
                break;
            case "file":
                this.path = sprop.getProperty("file.path");
                validProp(this.path);
                System.out.println(this.path);
                this.userService = new FictiveUserService();
                this.contactServices = new ContactService[]{new FileContactService(new ContactSerializer(), this.path)};
                break;
            case "memory":
                this.userService = new FictiveUserService();
                this.contactServices = new ContactService[]{new InMemoryContactService()};
                break;
            default:
                throw new PropertiesException("unsupported app.service.workmode");
        }


    }


    public ContactService[] getContactServices() throws PropertiesException {
        validProp(this.contactServices);
        return contactServices;
    }

    public UserService getUserService() throws PropertiesException {
        validProp(this.userService);
        return userService;
    }

    private void checkProp() throws PropertiesException {
        sprop = new Properties();
        try {
            switch (this.value) {
                case "dev":
                    sprop.load(new FileInputStream("app-dev.properties"));
                    break;
                case "prod":
                    sprop.load(new FileInputStream("app-prod.properties"));
                    break;
                default:
                    throw new PropertiesException("Problem with launch parameter");
            }

        } catch (IOException e) {
            throw new PropertiesException("Problem with launch parameter");
        }
    }

    private void validProp(Object str) throws PropertiesException {
        if (str == null) {
            throw new PropertiesException("properties equals null");
        }
    }
}
