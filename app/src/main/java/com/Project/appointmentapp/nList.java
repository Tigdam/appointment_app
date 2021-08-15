package com.Project.appointmentapp;

public class nList {
    String DocName, Doc_Email, Doc_Password ,uid, doctor;
    int usertype;

    public nList() {
    }

    public String getDocName() {
        return DocName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public void setDocName(String docName) {
        DocName = docName;
    }

    public String getDoc_Email() {
        return Doc_Email;
    }

    public void setDoc_Email(String doc_Email) {
        Doc_Email = doc_Email;
    }

    public String getDoc_Password() {
        return Doc_Password;
    }

    public void setDoc_Password(String doc_Password) {
        Doc_Password = doc_Password;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public nList(String uid, String docName, String doc_Email, String doc_Password,int usertype , String doctor ) {
        this.uid = uid;
        DocName = docName;
        Doc_Email = doc_Email;
        Doc_Password = doc_Password;
        this.usertype = usertype;
        this.doctor = doctor;
    }
}
