package com.company.Menu.MenuAction;

import com.company.Exceptions.ProblemWithConnectException;
import com.company.Exceptions.StatusException;
import com.company.Services.ContactService;

import lombok.AllArgsConstructor;


import java.util.Scanner;

@AllArgsConstructor
public class SearchByContactMenuAction implements MenuAction {
    private Scanner scanner;
    private ContactService[]contactService;
    @Override
    public void doAction() {
        while (true) {

            System.out.println("Please, enter contact");
            String value=scanner.next();
            System.out.println("");
            try {
                for (int i = 0; i<contactService.length ; i++) {
                    System.out.println(contactService[i].findByValue(value));
                }
            }catch (ProblemWithConnectException| StatusException e){
                System.out.println(e.getMessage());
            }
            if (!closeAfter()) {
                break;
            }
        }
    }

    @Override
    public String getName() {
        return "Search by contact";
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
