package com.company;

import com.company.Menu.AuthorisationAction.LogInAuthAction;
import com.company.Menu.AuthorisationAction.SignUpAuthAction;
import com.company.Menu.MenuAction.*;
import com.company.Services.AuthorisationService;
import com.company.Services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.company.Menu.Menu;


import java.net.http.HttpClient;
import java.util.Scanner;


public class Main {

    /*в последнем задании интерфейс в идеале должен выглядеть так

        интерфейс в идеале должен работать так

        программа запущена:
        —————————-
        1 - войти
        2 - зарегистрироватся
        сделайте выбор: 1

        введите логин: vasia
        введите пароль: 12345hello

        Вход выполнен успешно

        1 - посмотреть контакты
        2 - поиск по имени
        3 - поиск по контакту
        4 - добавить контакт
        сделайте выбор: 1

        —————
        тип: Email
        имя: Вася
        контакт: vasia@ukr.net
        ————
        тип: Phone
        имя: Петя
        контакт: +380630001122
        ————

        1 - посмотреть контакты
        2 - поиск по имени
        3 - поиск по контакту
        4 - добавить контакт
        сделайте выбор:  4

        .......(дальше меню по кругу)*/


    public static void main(String[] args) {
        HttpClient httpClient=HttpClient.newBuilder().build();
ObjectMapper objectMapper=new ObjectMapper();
/*UserService userService=new UserService(httpClient,objectMapper);
userService.getAll();*/
        AuthorisationService authorisationService=new AuthorisationService(httpClient,objectMapper);
        UserService userService=new UserService(httpClient,objectMapper);
        Scanner scanner=new Scanner(System.in);
       Menu menu=new Menu(scanner);
       menu.addAction(new AddContactMenuAction(scanner,userService));
       menu.addAction(new ShowContactsMenuAction(scanner,userService));
       menu.addAction(new SearchByNameMenuAction(scanner,userService));
       menu.addAction(new SearchByContactMenuAction(scanner,userService));
       menu.addAuthAction(new LogInAuthAction(scanner,authorisationService));
       menu.addAuthAction(new SignUpAuthAction(scanner,authorisationService));
       menu.run();

    }
}


