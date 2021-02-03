package com.company.Menu.AuthorisationAction;

import com.company.Exceptions.ProblemWithConnectException;
import com.company.Exceptions.StatusException;
import com.company.ResponseAndRequest.SignUp.SignUpResponse;
import com.company.Services.AuthorisationService;
import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class SignUpAuthAction implements AuthorisationAction{
    private Scanner scanner;
    private AuthorisationService authorisationService;
    @Override
    public String doAction() {
        System.out.println("Login:");
        String login=scanner.nextLine();
        System.out.println("Password:");
        String passwd=scanner.nextLine();
        System.out.println("Date born:");
        String dateBorn=validDateBorn();
        SignUpResponse sur=authorisationService.singUp(login,passwd,dateBorn);
        try {
            validSignUp(sur);
            hasError(sur);
        } catch (StatusException | ProblemWithConnectException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    @Override
    public String getName() {
        return "Sign Up";
    }
    private void validSignUp(SignUpResponse sur) {
        if (sur == null) {
            throw new ProblemWithConnectException("Problem with connect");
        }
    }

    private void hasError(SignUpResponse sur) {
        if (sur.getStatus().equals("error")) {
            throw new StatusException(sur.getError());
        }
    }
    private String validDateBorn(){
        while (true){
            String dateBorn=scanner.nextLine();
            if (dateBorn.matches("(\\d){4}-(((0)[1-9])|(1)[0-2])-(([0-2][1-9])|([3][0-1]))")){
                return dateBorn;
            }
            System.out.println("The date form should be => yyyy-mm-dd\nTry again...");
        }

    }
}
