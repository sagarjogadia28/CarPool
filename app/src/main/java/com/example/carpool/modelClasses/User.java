package com.example.carpool.modelClasses;

public class User {

    private String userid;
    private String fName;
    private String lName;
    private String eMail;
    private String phone;

    public User(String userid, String fName, String lName, String eMail, String phone) {
        this.userid = userid;
        this.fName = fName;
        this.lName = lName;
        this.eMail = eMail;
        this.phone = phone;
    }

    public User(){

    }

    public String getUserid() {
        return userid;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String geteMail() {
        return eMail;
    }

    public String getPhone() {
        return phone;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
