package com.example.emc;

public class SignIn {
    private String username;
    private String Password;

    public SignIn() {
    }

    public SignIn(String username, String password) {
        this.username = username;
        Password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return Password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
