package com.company.Menu.MenuAction;

import com.company.Exceptions.ProblemWithConnectException;
import com.company.Exceptions.StatusException;
import com.company.Services.Contact;
import com.company.Services.ContactService;
import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class AddContactMenuAction implements MenuAction {
    private Scanner scanner;
    private ContactService []contactServices;

    @Override
    public void doAction() {
        while (true) {
            System.out.println("Name:");
            String name = scanner.next();
            System.out.println("Please, enter phone number or email:");
            String info = validNumber();
            Contact contact=new Contact();
            contact.setName(name);
            contact.setContact(info);
            contact.setType(
                    info.matches("\\+380\\d{9}")?
                            Contact.TypeContact.valueOf("phone".toUpperCase()):
                            Contact.TypeContact.valueOf("email".toUpperCase())
            );
            try {
                for (int i = 0; i <contactServices.length ; i++) {
                    contactServices[i].add(contact);
                }
            } catch (ProblemWithConnectException | StatusException e) {
                System.out.println(e.getMessage());
            }
            if (!closeAfter()){
                break;
            }
        }
    }

    private String validNumber() {
        while (true) {
            String contact = scanner.next();
            if (contact.matches("\\+380\\d{9}")) {
                return contact;
            } else if (contact.matches("([\\w]+)(@)(.+)")) {
                return contact;
            } else {
                System.out.println("Invalid contact\nTry again...");
            }
        }
    }



    @Override
    public String getName() {
        return "Add contact";
    }

    @Override
    public boolean closeAfter() {
        System.out.println("Do you want to continue this operation? Y/n");
        String answer = scanner.next();
        if (answer.equals("y") || answer.equals("Y")) {
            return true;
        } else {
            return false;
        }
    }
}
