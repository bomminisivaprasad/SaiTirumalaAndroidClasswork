package com.example.attendanceapp;

public class  Student {
    private String rollno,name,mobile,branch,year,section;

    public Student() {
    }

    public Student(String rollno, String name, String mobile, String branch, String year, String section) {
        this.rollno = rollno;
        this.name = name;
        this.mobile = mobile;
        this.branch = branch;
        this.year = year;
        this.section = section;
    }

    public String getRollno() {
        return rollno;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getBranch() {
        return branch;
    }

    public String getYear() {
        return year;
    }

    public String getSection() {
        return section;
    }
}
