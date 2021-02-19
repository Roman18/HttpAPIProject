package com.company.factory.Services;

import com.company.Serializer.ContactSerializer;
import com.company.Services.ContactService;
import com.company.Services.FictiveUserService;
import com.company.Services.FileContactService;
import com.company.Services.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FileServiceFactory implements ServiceFactory {
    private final String path;
    @Override
    public UserService createUserService() {
        return new FictiveUserService();
    }

    @Override
    public ContactService createContactService(UserService userService) {
        return new FileContactService(new ContactSerializer(),path);
    }
}
