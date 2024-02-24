package com.nicsi.ceda.controller;

public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }
}