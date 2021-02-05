package com.company.Services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryContactService implements ContactService {


    private List<Contact> contacts = new ArrayList<>();

    private int newId() {
        return contacts.stream().map(Contact::getId).
                max(Comparator.comparingInt(a -> a)).map(id -> id + 1).
                orElse(1);
    }

    @Override
    public void add(Contact contact) {
        contact.setId(newId());
        contacts.add(contact);
    }

    @Override
    public void remove(Integer id) {
    contacts=contacts.stream().
            filter(c->c.getId().
                    equals(id)).
            collect(Collectors.toList());
    }

    @Override
    public List<Contact> findByName(String name) {
        System.out.println("=====Result from memory=====");
        return contacts.stream().
                filter(c->c.getName().startsWith(name)).collect(Collectors.toList());
    }

    @Override
    public List<Contact> findByValue(String value) {
        System.out.println("=====Result from memory=====");
        return contacts.stream().
                filter(c->c.getContact().contains(value)).collect(Collectors.toList());
    }

    @Override
    public List<Contact> getAll() {
        System.out.println("=====Result from memory=====");
        return contacts;
    }
}
