package com.example.firebasedatabasetest;

class Student {
    private String rollno,name,mobileno,branch,latlong,imagepath;

    public Student(String rollno, String name, String mobileno, String branch, String latlong, String imagepath) {
        this.rollno = rollno;
        this.name = name;
        this.mobileno = mobileno;
        this.branch = branch;
        this.latlong = latlong;
        this.imagepath = imagepath;
    }

    public Student() {
    }

    public String getRollno() {
        return rollno;
    }

    public String getName() {
        return name;
    }

    public String getMobileno() {
        return mobileno;
    }

    public String getBranch() {
        return branch;
    }

    public String getLatlong() {
        return latlong;
    }

    public String getImagepath() {
        return imagepath;
    }
}
