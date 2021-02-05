package com.company.Menu.MenuAction;

import com.company.Services.ContactService;
import lombok.AllArgsConstructor;

import java.util.Scanner;


@AllArgsConstructor
public class RemoveContactMenuAction implements MenuAction {
    private Scanner scanner;
    private ContactService[] contactServices;

    @Override
    public void doAction() {
        while (true) {
            System.out.println("Enter id of contact");
            Integer id = scanner.nextInt();
            try {

                for (int i = 0; i < contactServices.length; i++) {
                    contactServices[i].remove(id);
                }
            }catch (UnsupportedOperationException e){
                System.out.println(e.getMessage());
            }
            if (!closeAfter()) {
                break;
            }

        }
    }

    @Override
    public String getName() {
        return "Remove contact";
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
