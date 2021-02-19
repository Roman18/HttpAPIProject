package com.company.factory.Services;

import com.company.Services.ContactService;
import com.company.Services.UserService;

public interface ServiceFactory {
    UserService createUserService();
    ContactService createContactService(UserService userService);
}
