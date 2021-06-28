package com.Project.appointmentapp;

public class UserHelperClass_signup {
    String  username, email, password, type;
    public UserHelperClass_signup() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserHelperClass_signup(String username, String email, String password, String type) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.type = type;
    }
}
