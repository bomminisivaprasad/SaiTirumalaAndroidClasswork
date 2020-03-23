package com.example.firebaseupdatedelete;

public class Student {
    String rollno,name,mobile,email;

    public Student(String rollno, String name, String mobile, String email) {
        this.rollno = rollno;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
    }

    public Student() {
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
