package com.company.Serializer;

import com.company.Services.Contact;

public interface ContactsSerializer {
    String serialize(Contact c);
    Contact deserialize(String s);
}
