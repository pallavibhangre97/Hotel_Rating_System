package com.user_service.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String msg)
    {
        super(msg);
    }
}
