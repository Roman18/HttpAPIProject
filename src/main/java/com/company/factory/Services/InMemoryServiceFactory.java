package com.company.factory.Services;

import com.company.Services.ContactService;
import com.company.Services.FictiveUserService;
import com.company.Services.InMemoryContactService;
import com.company.Services.UserService;

public class InMemoryServiceFactory implements ServiceFactory {
    @Override
    public UserService createUserService() {
        return new FictiveUserService();
    }

    @Override
    public ContactService createContactService(UserService userService) {
        return new InMemoryContactService();
    }
}
