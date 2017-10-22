package com.internetshop.Exceptions;

import java.sql.SQLIntegrityConstraintViolationException;

public class EmailExistException extends Exception {

    private String enteredEmail;

    public EmailExistException(String enteredEmail) {
        this.enteredEmail = enteredEmail;
    }

    public String getEnteredEmail() {
        return enteredEmail;
    }

    public void setEnteredEmail(String enteredEmail) {
        this.enteredEmail = enteredEmail;
    }
}
