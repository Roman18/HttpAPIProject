package com.company.Menu.AuthorisationAction;


import com.company.Exceptions.ProblemWithConnectException;
import com.company.Exceptions.StatusException;
import com.company.ResponseAndRequest.LogIn.LoginResponse;
import com.company.Services.AuthorisationService;
import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class LogInAuthAction implements AuthorisationAction {
    private Scanner scanner;
    private AuthorisationService authorisationService;

    @Override
    public String doAction() {
        System.out.println("Login:");
        String login = scanner.nextLine();
        System.out.println("Password:");
        String passwd = scanner.nextLine();
        LoginResponse lr = authorisationService.logIn(login, passwd);
        try {
            validLogIn(lr);
            hasError(lr);
            return lr.getToken();
        } catch (StatusException | ProblemWithConnectException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String getName() {
        return "Log in";
    }
    private void validLogIn(LoginResponse lr) {
        if (lr == null) {
            throw new ProblemWithConnectException("Problem with connect");
        }
    }

    private void hasError(LoginResponse lr) {
        if (lr.getStatus().equals("error")) {
            throw new StatusException(lr.getError());
        }
    }
}
