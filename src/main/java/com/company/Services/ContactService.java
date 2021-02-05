package com.company.Services;

import java.util.List;

public interface ContactService {


    void add(Contact contact);
    void remove(Integer id);
    List<Contact> findByName(String name);
    List<Contact> findByValue(String value);
    List<Contact> getAll();
}
