package com.simple.exception;

import net.bytebuddy.implementation.bytecode.Throw;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(){

    }

    public ResourceNotFoundException(String msg){
        super(msg);
    }

    public ResourceNotFoundException(String msg, Throwable cause){
        super(msg, cause);
    }

    public ResourceNotFoundException(Throwable cause){
        super(cause);
    }

    public ResourceNotFoundException(String message, Throwable cause, boolean enableSuperession, boolean writableStackTrace){
        super(message, cause, enableSuperession, writableStackTrace);
    }
}
