package com.company;

import com.company.Menu.AuthorisationAction.LogInAuthAction;
import com.company.Menu.AuthorisationAction.SignUpAuthAction;
import com.company.Menu.Menu;
import com.company.Menu.MenuAction.*;
import com.company.Serializer.ContactSerializer;
import com.company.Services.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;
import java.util.Scanner;


public class Main {


    public static void main(String[] args) {

        String baseUri="https://mag-contacts-api.herokuapp.com";
        HttpClient httpClient=HttpClient.newBuilder().build();
        ObjectMapper objectMapper=new ObjectMapper();
        UserService userService=new ApiUsersService(baseUri,objectMapper,httpClient);
        ContactService[] contactServices = {
                new InMemoryContactService(),
                new FileContactService(
                        new ContactSerializer(),
                        "D:\\MavenThirdProject\\src\\main\\resources\\addresses.txt"
                ),
                new ApiContactService(userService,objectMapper,httpClient,baseUri)
        };

        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu(scanner,userService);
        menu.addAction(new AddContactMenuAction(scanner, contactServices));
        menu.addAction(new RemoveContactMenuAction(scanner,contactServices));
        menu.addAction(new ShowContactsMenuAction(scanner, contactServices));
        menu.addAction(new SearchByNameMenuAction(scanner, contactServices));
        menu.addAction(new SearchByContactMenuAction(scanner, contactServices));
        menu.addAuthAction(new LogInAuthAction(scanner, userService));
        menu.addAuthAction(new SignUpAuthAction(scanner, userService));
        menu.run();

    }
}


