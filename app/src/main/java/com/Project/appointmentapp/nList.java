package com.Project.appointmentapp;

public class nList {
    String DocName, Doc_Email, Doc_Password;

    public nList() {
    }

    public String getDocName() {
        return DocName;
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

    public nList(String docName, String doc_Email, String doc_Password) {
        DocName = docName;
        Doc_Email = doc_Email;
        Doc_Password = doc_Password;
    }
}
