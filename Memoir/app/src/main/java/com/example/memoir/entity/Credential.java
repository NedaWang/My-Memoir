package com.example.memoir.entity;

import java.sql.Date;

public class Credential {
    private String id;
    private String password;
    private String signupdate;
    private Person username;

    public Credential(){}

    public Credential(String id, String password, String signUpDate, Person person) {
        this.id = id;
        this.password = password;
        this.signupdate = signUpDate;
        this.username = person;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSignUpDate() {
        return signupdate;
    }

    public void setSignUpDate(String signUpDate) {
        this.signupdate = signUpDate;
    }

    public Person getUsername() {
        return username;
    }

    public void setUsername(Person username) {
        this.username = username;
    }
}
