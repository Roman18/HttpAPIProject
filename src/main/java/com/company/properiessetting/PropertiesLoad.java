package com.company.properiessetting;

import com.company.Exceptions.PropertiesException;
import com.company.Services.*;
import com.company.factory.Services.ApiServiceFactory;
import com.company.factory.Services.FileServiceFactory;
import com.company.factory.Services.InMemoryServiceFactory;
import com.company.factory.Services.ServiceFactory;



public class PropertiesLoad {
    private ConfigLoader configLoader = new ConfigLoader();
    private SysProperties app;
    private FileProperties fileProp;
    private String mod;
    private String path;
    private String uri;
    private UserService userService;
    private ContactService[] contactServices;


    public void loadProp() throws PropertiesException {
        preLoadProp();
        setAllProp();
        validProp(this.uri);
        validProp(this.path);
        validProp(this.mod);
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

    private void setAllProp() {
        this.mod = fileProp.getMod();
        this.uri = fileProp.getUri();
        this.path = fileProp.getFile();
    }

    private void setServices(){  // Новый метод(работает с фабрикой)
        ServiceFactory serviceFactory;
        if ("api".equals(this.mod)){
            serviceFactory = new ApiServiceFactory(this.uri);
        }else if("file".equals(this.mod)){
            serviceFactory = new FileServiceFactory(this.path);
        }else {
            serviceFactory = new InMemoryServiceFactory();
        }
        this.userService = serviceFactory.createUserService();
        this.contactServices = new ContactService[]{serviceFactory.createContactService(this.userService)};
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
