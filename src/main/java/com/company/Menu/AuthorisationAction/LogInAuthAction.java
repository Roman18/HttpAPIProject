package com.company.Menu.AuthorisationAction;


import com.company.Exceptions.ProblemWithConnectException;
import com.company.Exceptions.StatusException;
import com.company.Services.UserService;
import com.company.dto.User;
import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class LogInAuthAction implements AuthorisationAction {
    private Scanner scanner;
    private UserService userService;

    @Override
    public void doAction() {
        System.out.println("Login:");
        String login = scanner.nextLine();
        System.out.println("Password:");
        String password = scanner.nextLine();
        User user=new User();
        user.setPassword(password);
        user.setLogin(login);
        try {
            userService.login(user);
        } catch (StatusException | ProblemWithConnectException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public String getName() {
        return "Log in";
    }

}
