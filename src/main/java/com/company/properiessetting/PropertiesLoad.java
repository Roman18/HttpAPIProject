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
    private ObjectMapper objectMapper = new ObjectMapper();
    private HttpClient httpClient = HttpClient.newBuilder().build();

    public PropertiesLoad() {
        this.value = (String) sprop.get("contactbook.profile");
    }

    public void loadProp() throws PropertiesException {
        checkProp();
        setAllProp();
        validProp(this.uri);
        validProp(this.path);
        validProp(this.mod);
        validMod();
        this.userService = createUserService();
        this.contactServices = createContactService();
    }


    public ContactService[] getContactServices() throws PropertiesException {
        validProp(this.contactServices);
        return contactServices;
    }


    public UserService getUserService() throws PropertiesException {
        validProp(this.userService);
        return userService;
    }

    private void setAllProp() {
        this.mod = sprop.getProperty("app.service.workmode");
        this.uri = sprop.getProperty("api.base-uri");
        this.path = sprop.getProperty("file.path");
    }

    private UserService createUserService() {
        return "api".equals(this.mod) ?
                new ApiUsersService(
                        this.uri,
                        objectMapper,
                        httpClient) : new FictiveUserService();
    }

    private ContactService[] createContactService() {
        if ("api".equals(this.mod)) {
            return new ContactService[]{new ApiContactService(userService,
                    objectMapper, httpClient, this.uri)};
        } else if ("file".equals(this.mod)) {
            return new ContactService[]{new FileContactService(new ContactSerializer(), this.path)};
        } else {
            return new ContactService[]{new InMemoryContactService()};
        }
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

    private void validMod() throws PropertiesException {
        if (!("api".equals(this.mod)) && !("file".equals(this.mod)) && !("memory".equals(this.mod))) {
            throw new PropertiesException("unsupported app.service.workmode");
        }
    }

    private void validProp(Object str) throws PropertiesException {
        if (str == null) {
            throw new PropertiesException("properties equals null");
        }
    }
}
