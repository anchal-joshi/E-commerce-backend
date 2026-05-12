package com.ecommerce.project.exception;

public class EmptyCartException extends RuntimeException{
    public EmptyCartException(String message){
        super(message);
    }
}
