package com.company.Services;

import com.company.Serializer.ContactsSerializer;
import lombok.RequiredArgsConstructor;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class FileContactService implements ContactService {

private final ContactsSerializer contactsSerializer;
private final String path;



    private int newId() {
        return getAll().
                stream().
                map(Contact::getId).
                max(Comparator.comparingInt(a -> a)).
                map(id -> id + 1).
                orElse(1);
    }
    @Override
    public void add(Contact contact) {
        contact.setId(newId());
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(path, true))) {
            pw.write(contactsSerializer.serialize(contact) + "\n");
            pw.flush();
        } catch (IOException e) {
            System.out.println("Problem with file");
        }
    }

    @Override
    public void remove(Integer id) {
        PrintWriter pw = null;
        File newFile = new File("D:\\ContactBookProject\\src\\main\\resources\\addressesReplace.txt");
        Scanner scanner;
        BufferedInputStream bis;
        try (FileInputStream fis = new FileInputStream(path)) {
            bis = new BufferedInputStream(fis);
            pw = new PrintWriter(new FileOutputStream(newFile,true));
            scanner = new Scanner(bis);
            while (scanner.hasNextLine()) {
                String result = scanner.nextLine();
                if (!result.startsWith(String.valueOf(id))) {
                    pw.write(result + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Problem with file");
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
        try (FileInputStream fis = new FileInputStream(newFile)) {
            bis = new BufferedInputStream(fis);
            pw = new PrintWriter(new FileOutputStream(path));
            scanner = new Scanner(bis);
            while (scanner.hasNextLine()) {
                pw.write(scanner.nextLine() + "\n");
            }
            System.out.println("The contact has been removed from list in file!");
        } catch (IOException e) {
            System.out.println("Problem with file");
        } finally {
            if (pw != null) {
                pw.close();
            }
            newFile.delete();
        }
    }



    @Override
    public List<Contact> findByName(String name) {
        System.out.println("=====Result from file=====");
        return readContact(c->c.getName().startsWith(name));
    }

    @Override
    public List<Contact> findByValue(String value) {
        System.out.println("=====Result from file=====");
        return readContact(c->c.getContact().contains(value));
    }

    @Override
    public List<Contact> getAll() {
        System.out.println("=====Result from file=====");
        return readContact(c->true);
    }

    private List<Contact> readContact(Predicate<Contact> predicate){

        List<Contact> contactList=new ArrayList<>();
        try (FileInputStream fis=new FileInputStream(path)){
            Scanner scanner=new Scanner(fis);
            while (scanner.hasNextLine()){
                String string=scanner.nextLine();
                if (string.isBlank()) continue;
                Contact contact=contactsSerializer.deserialize(string);
                if (predicate.test(contact)){
                    contactList.add(contact);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contactList;

    }
}
