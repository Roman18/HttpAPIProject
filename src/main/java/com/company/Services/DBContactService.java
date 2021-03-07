package com.company.Services;

import com.company.properiessetting.SetUpDB;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
public class DBContactService implements ContactService {
    private SetUpDB setUpDB;
    private UserService userService;

    @Override
    public void add(Contact contact) {
        Connection connection = setUpDB.createConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO contacts(name, contact,type_contact,users_id) VALUES(?,?,?,?) "
            );
            statement.setString(1, contact.getName());
            statement.setString(2, contact.getContact());
            statement.setString(3, contact.getType().toString().toLowerCase());
            statement.setInt(4, Integer.parseInt(userService.getToken()));
            statement.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Integer id) {
        Connection connection=setUpDB.createConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM contacts WHERE id = ? AND users_id = ?"
            );
            statement.setInt(1, id);
            statement.setInt(2, Integer.parseInt(userService.getToken()));
           statement.execute();
           connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Contact> findByName(String name) {
        Connection connection = setUpDB.createConnection();
        List<Contact> list = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT id, name, contact, type_contact, users_id FROM contacts WHERE name LIKE ? AND users_id=?"
            );
            statement.setString(1, name+"%");
            statement.setInt(2, Integer.parseInt(userService.getToken()));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Contact contact = parseToContact(rs);
                list.add(contact);
            }
            connection.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Contact> findByValue(String value) {

        Connection connection = setUpDB.createConnection();
        List<Contact> list = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT id, name, contact, type_contact, users_id FROM contacts WHERE contact LIKE ? AND users_id = ?"
            );
            statement.setString(1, "%"+value+"%");
            statement.setInt(2, Integer.parseInt(userService.getToken()));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Contact contact =  parseToContact(rs);
                list.add(contact);
            }
            connection.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public List<Contact> getAll() {
        Connection connection = setUpDB.createConnection();
        List<Contact> list = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT id, name, contact, type_contact, users_id FROM contacts WHERE users_id = ?"
            );
            statement.setInt(1, Integer.parseInt(userService.getToken()));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Contact contact =  parseToContact(rs);
                list.add(contact);
            }
            connection.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Contact parseToContact(ResultSet rs) throws SQLException {
        Contact contact = new Contact();
        contact.setId(rs.getInt("id"));
        contact.setName(rs.getString("name"));
        contact.setContact(rs.getString("contact"));
        contact.setType(Contact.TypeContact.valueOf(rs.getString("type_contact").toUpperCase()));
        return contact;
    }
}
