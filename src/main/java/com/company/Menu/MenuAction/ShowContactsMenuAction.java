package com.company.Menu.MenuAction;


import com.company.Exceptions.ProblemWithConnectException;
import com.company.Exceptions.StatusException;
import com.company.ResponseAndRequest.GetAll.GetAllResponse;
import com.company.Services.UserService;
import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class ShowContactsMenuAction implements MenuAction {
    private Scanner scanner;
    private UserService userService;

    @Override
    public void doAction(String token) {
        while (true) {
            GetAllResponse users = userService.getAll(token);
            try {
                validAdd(users);
                hasError(users);
                System.out.println(users.getContacts());
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

    private void validAdd(GetAllResponse users) {
        if (users == null) {
            throw new ProblemWithConnectException("Problem with connect");
        }
    }

    private void hasError(GetAllResponse users) {
        if (users.getStatus().equals("error")) {
            throw new StatusException(users.getError());
        }
    }
}
