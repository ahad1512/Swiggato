package com.example.Swiggato.exceptions;

public class EmptyCartException extends RuntimeException{
    public EmptyCartException(String message) {
        super(message);
    }
}
