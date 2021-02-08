package com.company.Services;

import com.company.dto.User;

import java.util.Collections;
import java.util.List;

public class FictiveUserService implements UserService {
    @Override
    public String getToken() {
        return null;
    }

    @Override
    public boolean isAuth() {
        return true;
    }

    @Override
    public void register(User user) {
        throw new UnsupportedOperationException("register not supported");
    }

    @Override
    public void login(User user) {
        throw new UnsupportedOperationException("login not supported");
    }

    @Override
    public List<User> getAll() {
        return Collections.emptyList();
    }
}
