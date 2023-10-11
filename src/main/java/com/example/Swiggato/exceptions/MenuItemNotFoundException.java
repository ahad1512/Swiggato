package com.example.Swiggato.exceptions;

public class MenuItemNotFoundException extends RuntimeException{
    public MenuItemNotFoundException(String message) {
        super(message);
    }
}
