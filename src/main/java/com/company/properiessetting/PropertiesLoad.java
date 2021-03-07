package com.company.properiessetting;

import com.company.Exceptions.PropertiesException;
import com.company.Services.ContactService;
import com.company.Services.UserService;
import com.company.factory.Services.*;


public class PropertiesLoad {
    private ConfigLoader configLoader = new ConfigLoader();
    private SysProperties app;
    private FileProperties fileProp;
    private String mod;
    private String path;
    private String uri;
    private String dsn;
    private String user;
    private String password;
    private UserService userService;
    private ContactService[] contactServices;


    public void loadProp() throws PropertiesException {
        preLoadProp();
        setAllProp();
        validMod();
        setServices();
    }


    public ContactService[] getContactServices() throws PropertiesException {
        validProp(this.contactServices);
        return contactServices;
    }


    public UserService getUserService() throws PropertiesException {
        validProp(this.userService);
        return userService;
    }

    private void setAllProp() throws PropertiesException {
        this.mod = fileProp.getMod();
        this.uri = fileProp.getUri();
        this.path = fileProp.getFile();
        this.dsn = fileProp.getDsn();
        this.user = fileProp.getUser();
        this.password = fileProp.getPassword();
        String[] allProp = {this.mod, this.uri, this.path, this.dsn, this.user, this.password};
        for (String prop : allProp) {
            validProp(prop);
        }
    }

    private void setServices() {  // Новый метод(работает с фабрикой)
        ServiceFactory serviceFactory;
        if ("api".equals(this.mod)) {
            serviceFactory = new ApiServiceFactory(this.uri);
        } else if ("file".equals(this.mod)) {
            serviceFactory = new FileServiceFactory(this.path);
        } else if ("db".equals(this.mod)) {
            serviceFactory = new DBServiceFactory(this.dsn, this.user, this.password);
        } else {
            serviceFactory = new InMemoryServiceFactory();
        }
        this.userService = serviceFactory.createUserService();
        this.contactServices = new ContactService[]{serviceFactory.createContactService(this.userService)};
    }


    private void preLoadProp() throws PropertiesException {
        this.app = configLoader.getSystemProp(SysProperties.class);
        try {
            this.fileProp = configLoader.getFileProp(FileProperties.class, "app-" + app.getProperties() + ".properties");
        } catch (Exception e) {
            throw new PropertiesException("file not exist");
        }

    }

    private void validMod() throws PropertiesException {
        if (!("api".equals(this.mod)) &&
                !("file".equals(this.mod)) &&
                !("memory".equals(this.mod)) &&
                !("db".equals(this.mod))) {
            throw new PropertiesException("unsupported app.service.workmode");
        }
    }


    private void validProp(Object str) throws PropertiesException {
        if (str == null) {
            throw new PropertiesException("properties equals null");
        }
    }
}
