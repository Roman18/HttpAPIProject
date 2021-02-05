package com.company.Services;

import com.company.dto.User;

import java.util.List;

public interface UserService {
    String getToken();
    boolean isAuth();
    void register(User user);
    void login(User user);
    List<User> getAll();

}
