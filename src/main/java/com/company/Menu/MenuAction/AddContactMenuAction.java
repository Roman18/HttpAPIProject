package com.company.Menu.MenuAction;

import com.company.Exceptions.ProblemWithConnectException;
import com.company.Exceptions.StatusException;
import com.company.ResponseAndRequest.Add.AddResponse;
import com.company.Services.UserService;
import com.company.ResponseAndRequest.Add.AddRequest;
import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class AddContactMenuAction implements MenuAction {
    private Scanner scanner;
    private UserService userService;

    @Override
    public void doAction(String token) {
        while (true) {
            System.out.println("Name:");
            String name = scanner.next();
            System.out.println("Please, enter phone number or email:");
            String info = validNumber();
            AddRequest user = info.matches("\\+380\\d{9}") ?
                    new AddRequest("phone", info, name) :
                    new AddRequest("email", info, name);
            AddResponse add = userService.add(user, token);
            try {
                validAdd(add);
                hasError(add);
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
            } else if (contact.matches("([\\w]+)(@)((gmail\\.com)|(mail\\.ru)|(ukr\\.net))")) {
                return contact;
            } else {
                System.out.println("Invalid contact\nTry again...");
            }
        }
    }

    private void validAdd(AddResponse add) {
        if (add == null) {
            throw new ProblemWithConnectException("Problem with connect");
        }
    }

    private void hasError(AddResponse add) {
        if (add.getStatus().equals("error")) {
            throw new StatusException(add.getError());
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
