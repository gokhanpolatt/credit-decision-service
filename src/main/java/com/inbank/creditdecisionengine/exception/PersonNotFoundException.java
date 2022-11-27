package com.inbank.creditdecisionengine.exception;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException() {
        super("Person not found");
    }
}
