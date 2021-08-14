package com.Project.appointmentapp;

public class UserHelperClass_signup {

    String  fullName, email, password, uid;
    int usertype;

    public UserHelperClass_signup() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public UserHelperClass_signup(String uid, String fullName, String email, String password, int usertype) {
        this.uid = uid;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.usertype = usertype;
    }
}
