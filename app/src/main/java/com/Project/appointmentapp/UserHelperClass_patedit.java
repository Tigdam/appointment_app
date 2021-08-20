package com.Project.appointmentapp;

public class UserHelperClass_patedit {
    String fullName, email, dob,mob,gen,prof,weight,height,history,address, uid;

    public UserHelperClass_patedit() {
    }

    public String getFname() {
        return fullName;
    }

    public void setFname(String fname) {
        this.fullName = fname;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUid() {
        return uid;
    }

    public void setUi(String uid) {
        this.uid = uid;
    }

    public UserHelperClass_patedit(String fname, String email, String dob, String mob, String gen, String prof, String weight, String height, String history, String address, String uid) {
        this.fullName = fname;
        this.email = email;
        this.dob = dob;
        this.mob = mob;
        this.gen = gen;
        this.prof = prof;
        this.weight = weight;
        this.height = height;
        this.history = history;
        this.address = address;
        this.uid = uid;
    }
}
