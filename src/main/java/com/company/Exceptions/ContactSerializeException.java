package com.company.Exceptions;

public class ContactSerializeException extends RuntimeException {
    public ContactSerializeException(String message) {
        super(message);
    }

    public ContactSerializeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContactSerializeException(Throwable cause) {
        super(cause);
    }
}
