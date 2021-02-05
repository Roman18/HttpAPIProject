package com.company.Menu;

import com.company.Menu.AuthorisationAction.AuthorisationAction;
import com.company.Menu.MenuAction.MenuAction;
import com.company.Services.UserService;


import java.util.Arrays;
import java.util.Scanner;

public class Menu {
    private MenuAction[] actions;
    private Scanner sc;
    private AuthorisationAction[] authorisationActions;
    private UserService userService;


    public Menu(Scanner sc,UserService userService) {
        this.userService=userService;
        this.actions = new MenuAction[0];
        this.authorisationActions = new AuthorisationAction[0];
        this.sc = sc;
    }


    public void addAction(MenuAction ma) {
        actions = Arrays.copyOf(actions, actions.length + 1);
        actions[actions.length - 1] = ma;
    }

    public void addAuthAction(AuthorisationAction aa) {
        authorisationActions = Arrays.copyOf(authorisationActions, authorisationActions.length + 1);
        authorisationActions[authorisationActions.length - 1] = aa;

    }


    public void runAuth() {
        while (!userService.isAuth()) {
            showAuthMenu();
            int choose = sc.nextInt();
            sc.nextLine();
            if (validAuthIndex(choose)) {
             authorisationActions[choose - 1].doAction();
            } else {
                System.out.println("Invalid index...");
            }

        }
    }

    public void run() {
        runAuth();
        System.out.println("Welcome");
        while (true) {
            showMenu();
            int choose = sc.nextInt();
            sc.nextLine();
            if (choose == actions.length + 1) {
                break;
            }
            if (validIndex(choose)) {
                actions[choose - 1].doAction();
            } else {
                System.out.println("Invalid index...");
            }
        }
    }


    private void showAuthMenu() {
        for (int i = 0; i < authorisationActions.length; i++) {
            System.out.println(i + 1 + " - " + authorisationActions[i].getName());
        }
    }


    private void showMenu() {
        for (int i = 0; i < actions.length; i++) {
            System.out.println(i + 1 + " - " + actions[i].getName());
        }
        System.out.println(actions.length + 1 + " - Exit");

    }

    private boolean validIndex(int index) {
        return !(index <= 0 || index > actions.length);
    }

    private boolean validAuthIndex(int index) {
        return !(index <= 0 || index > authorisationActions.length);
    }


}
