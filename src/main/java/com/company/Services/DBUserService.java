package com.company.Services;

import com.company.dto.User;
import com.company.properiessetting.SetUpDB;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
public class DBUserService implements UserService {
    private SetUpDB setUpDB;
    private Integer id;

    @Override
    public String getToken() {
        return String.valueOf(id); // для передачи id что-бы заполнять в таблице contacts поле users_id и для фильтрации запросов
    }


    @Override
    public boolean isAuth() {
        return id != null;    // проверяем по id: если id есть, то пользователь авторизован бесконечно
    }

    @Override
    public void register(User user) {
        Connection connection = setUpDB.createConnection();
        if (isExist(user.getLogin(), connection)) {
            System.out.println("User is exist!");
        } else {
            try {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO users(login, password, date_born) VALUES (?,?,?)"
                );
                statement.setString(1, user.getLogin());
                statement.setString(2, user.getPassword());
                statement.setObject(3, user.getDateBorn());
                statement.execute();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void login(User user) {
        Connection connection = setUpDB.createConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT id, login, password FROM users WHERE login = (?) AND password = (?)"
            );
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString("login").equals(user.getLogin()) &&
                        resultSet.getString("password").equals(user.getPassword())) {
                    this.id = resultSet.getInt("id");

                } else {
                    System.out.println("Invalid login or password!");
                }
            } else {
                System.out.println("User is not exist!");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private boolean isExist(String login, Connection connection) {  // что бы не повторялись логины в таблице
        try {

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT login FROM users WHERE login = ?"
            );
            statement.setString(1, login);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                connection.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
