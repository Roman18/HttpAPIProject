package com.company;

import com.company.Exceptions.PropertiesException;
import com.company.Menu.AuthorisationAction.LogInAuthAction;
import com.company.Menu.AuthorisationAction.SignUpAuthAction;
import com.company.Menu.Menu;
import com.company.Menu.MenuAction.*;
import com.company.Services.*;
import com.company.properiessetting.PropertiesLoad;

import java.util.Scanner;


public class Main {


    public static void main(String[] args) {

        PropertiesLoad propertiesLoad=new PropertiesLoad();
        try {
            propertiesLoad.loadProp();
            UserService userService = propertiesLoad.getUserService();
            ContactService[] contactServices = propertiesLoad.getContactServices();
            Scanner scanner = new Scanner(System.in);
            Menu menu = new Menu(scanner, userService);
            menu.addAction(new AddContactMenuAction(scanner, contactServices));
            menu.addAction(new RemoveContactMenuAction(scanner, contactServices));
            menu.addAction(new ShowContactsMenuAction(scanner, contactServices));
            menu.addAction(new SearchByNameMenuAction(scanner, contactServices));
            menu.addAction(new SearchByContactMenuAction(scanner, contactServices));
            menu.addAuthAction(new LogInAuthAction(scanner, userService));
            menu.addAuthAction(new SignUpAuthAction(scanner, userService));
            menu.run();
        }catch (PropertiesException e){
            e.getMessage();
        }
    }
}


