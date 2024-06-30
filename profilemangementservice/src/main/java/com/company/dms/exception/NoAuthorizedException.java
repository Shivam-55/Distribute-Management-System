package com.company.dms.exception;

public class NoAuthorizedException extends RuntimeException{
    public NoAuthorizedException(String s){
        super(s);
    }
}
