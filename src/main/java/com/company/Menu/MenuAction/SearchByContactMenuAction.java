package com.company.Menu.MenuAction;

import com.company.Exceptions.ProblemWithConnectException;
import com.company.Exceptions.StatusException;
import com.company.ResponseAndRequest.SearchByValue.SearchByValueRequest;
import com.company.ResponseAndRequest.SearchByValue.SearchByValueResponse;
import com.company.Services.UserService;

import lombok.AllArgsConstructor;


import java.util.Scanner;

@AllArgsConstructor
public class SearchByContactMenuAction implements MenuAction {
    private Scanner scanner;
    private UserService userService;
    @Override
    public void doAction(String token) {
        while (true) {

            System.out.println("Please, enter contact");
            String name=scanner.next();
            SearchByValueResponse search=userService.searchByValue(new SearchByValueRequest(name),token);
            try {
                validSearch(search);
                hasError(search);
                System.out.println(search.getContacts());
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
    private void validSearch(SearchByValueResponse search) {
        if (search == null) {
            throw new ProblemWithConnectException("Problem with connect");
        }
    }

    private void hasError(SearchByValueResponse search) {
        if (search.getStatus().equals("error")) {
            throw new StatusException(search.getError());
        }
    }
}
