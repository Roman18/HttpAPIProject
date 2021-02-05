package com.company.Menu.MenuAction;


import com.company.Exceptions.ProblemWithConnectException;
import com.company.Exceptions.StatusException;
import com.company.Services.ContactService;
import com.company.dto.GetAll.GetContactsResponse;
import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class ShowContactsMenuAction implements MenuAction {
    private Scanner scanner;
    private ContactService[]contactService;

    @Override
    public void doAction() {
        while (true) {

            try {
                for (int i = 0; i <contactService.length ; i++) {
                    System.out.println(contactService[i].getAll());
                }
            } catch (ProblemWithConnectException | StatusException e) {
                System.out.println(e.getMessage());
            }
            if (!closeAfter()){
                break;
            }
        }
    }

    @Override
    public String getName() {
        return "Show all contact";
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

    private void validAdd(GetContactsResponse users) {
        if (users == null) {
            throw new ProblemWithConnectException("Problem with connect");
        }
    }

    private void hasError(GetContactsResponse users) {
        if (users.getStatus().equals("error")) {
            throw new StatusException(users.getError());
        }
    }
}
