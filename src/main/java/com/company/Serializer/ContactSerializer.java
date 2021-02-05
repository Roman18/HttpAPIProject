package com.company.Serializer;

import com.company.Exceptions.ContactSerializeException;
import com.company.Services.Contact;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactSerializer implements ContactsSerializer {
    private static final String PATTERN="(\\d+)\\[(\\w+):(.+)\\|(.+)]";
    @Override
    public String serialize(Contact c) {
        return String.format(
                "%d[%s:%s|%s]",
                c.getId(),
                c.getType().toString().toLowerCase(),
                c.getName(),
                c.getContact()

        );
    }

    @Override
    public Contact deserialize(String s) {
        Pattern pattern=Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches()){
            Contact contact=new Contact();
            contact.setId(Integer.parseInt(matcher.group(1)));
            contact.setType(Contact.TypeContact.valueOf(matcher.group(2).toUpperCase()));
            contact.setName(matcher.group(3));
            contact.setContact(matcher.group(4));
            return contact;
        }
        throw new ContactSerializeException("Invalid contact data");
    }
}
