package com.company.Menu.AuthorisationAction;

import com.company.Exceptions.ProblemWithConnectException;
import com.company.Exceptions.StatusException;
import com.company.Services.UserService;
import com.company.dto.User;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.Scanner;

@AllArgsConstructor
public class SignUpAuthAction implements AuthorisationAction{
    private Scanner scanner;
    private UserService userService;
    @Override
    public void doAction() {
        System.out.println("Login:");
        String login=scanner.nextLine();
        System.out.println("Password:");
        String password=scanner.nextLine();
        System.out.println("Date born:");
        String dateBorn=validDateBorn();
        User user=new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setDateBorn(LocalDate.parse(dateBorn));
        try {
            userService.register(user);
        } catch (StatusException | ProblemWithConnectException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public String getName() {
        return "Sign Up";
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
