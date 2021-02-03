package com.company.Exceptions;

public class StatusException extends RuntimeException {
    public StatusException(){}
    public StatusException(String message){
        super(message);
    }
    public StatusException(String message,Throwable e){
        super(message,e);
    }
}
