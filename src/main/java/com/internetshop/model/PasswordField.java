package com.internetshop.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

public class PasswordField {
    @NotEmpty
    @Size(min = 6, max = 50)
    private String password;
    @NotEmpty
    @Size(min = 6, max = 50)
    private String newPasswordFirst;
    @NotEmpty
    @Size(min = 6, max = 50)
    private String newPasswordSecond;

    public PasswordField(){

    }
    public PasswordField(String password, String newIdFirst, String newPasswordSecond) {
        this.password = password;
        this.newPasswordFirst = newIdFirst;
        this.newPasswordSecond = newPasswordSecond;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPasswordFirst() {
        return newPasswordFirst;
    }

    public void setNewPasswordFirst(String newPasswordFirst) {
        this.newPasswordFirst = newPasswordFirst;
    }

    public String getNewPasswordSecond() {
        return newPasswordSecond;
    }

    public void setNewPasswordSecond(String newPasswordSecond) {
        this.newPasswordSecond = newPasswordSecond;
    }
}
