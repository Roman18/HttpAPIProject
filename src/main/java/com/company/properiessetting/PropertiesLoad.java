package com.company.properiessetting;

import com.company.Exceptions.PropertiesException;
import com.company.Serializer.ContactSerializer;
import com.company.Services.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;


public class PropertiesLoad {
    private ConfigLoader configLoader = new ConfigLoader();
    private SysProperties app;
    private FileProperties fileProp;
    private String mod;
    private String path;
    private String uri;
    private UserService userService;
    private ContactService[] contactServices;
    private ObjectMapper objectMapper = new ObjectMapper();
    private HttpClient httpClient = HttpClient.newBuilder().build();


    public void loadProp() throws PropertiesException {
        preLoadProp();
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
        this.mod = fileProp.getMod();
        this.uri = fileProp.getUri();
        this.path = fileProp.getFile();
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

    private void preLoadProp() throws PropertiesException {
        this.app = configLoader.getSystemProp(SysProperties.class);
        validParam(app.getProperties());
        this.fileProp = configLoader.getFileProp(FileProperties.class, "app-" + app.getProperties() + ".properties");
    }

    private void validMod() throws PropertiesException {
        if (!("api".equals(this.mod)) && !("file".equals(this.mod)) && !("memory".equals(this.mod))) {
            throw new PropertiesException("unsupported app.service.workmode");
        }
    }

    private void validParam(String prop) throws PropertiesException {
        if (!("prod".equals(prop)) && !("dev".equals(prop))) {
            throw new PropertiesException("unsupported properties " + prop);
        }
    }

    private void validProp(Object str) throws PropertiesException {
        if (str == null) {
            throw new PropertiesException("properties equals null");
        }
    }
}
