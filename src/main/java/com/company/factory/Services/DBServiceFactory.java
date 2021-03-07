package com.company.factory.Services;

import com.company.Services.ContactService;
import com.company.Services.DBContactService;
import com.company.Services.DBUserService;
import com.company.Services.UserService;
import com.company.properiessetting.SetUpDB;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class DBServiceFactory implements ServiceFactory {
    private final String dsn;
    private final String user;
    private final String password;
    private SetUpDB setUpDB;

    @Override
    public UserService createUserService() {
        this.setUpDB = new SetUpDB(dsn,user,password);
        return new DBUserService(this.setUpDB,null);
    }

    @Override
    public ContactService createContactService(UserService userService) {
        return new DBContactService(this.setUpDB,userService);
    }
}
