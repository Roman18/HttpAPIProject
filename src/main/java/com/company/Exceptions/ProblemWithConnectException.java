package com.company.Exceptions;

public class ProblemWithConnectException extends RuntimeException {

    public ProblemWithConnectException(){
        super();
    }
    public ProblemWithConnectException(String message){
        super(message);
    }
    public ProblemWithConnectException(String message, Throwable e){
        super(message,e);
    }
}
