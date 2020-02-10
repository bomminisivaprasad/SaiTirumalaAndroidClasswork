package com.example.userinfowithuserlocation.model;

public class User {
    private String uname,umobile,uemail,ulatlan,imagepath;

    public User() {

    }

    public User(String uname, String umobile, String uemail, String ulatlan, String imagepath) {
        this.uname = uname;
        this.umobile = umobile;
        this.uemail = uemail;
        this.ulatlan = ulatlan;
        this.imagepath = imagepath;
    }

    public String getUname() {
        return uname;
    }

    public String getUmobile() {
        return umobile;
    }

    public String getUemail() {
        return uemail;
    }

    public String getUlatlan() {
        return ulatlan;
    }

    public String getImagepath() {
        return imagepath;
    }
}
