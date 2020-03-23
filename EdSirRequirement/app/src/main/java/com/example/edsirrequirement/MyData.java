package com.example.edsirrequirement;

class MyData {

    String rollno, bundleNumber, numberofBooklets, rollnumberMarks;


    public MyData(String rollno, String bundleNumber, String numberofBooklets, String rollnumberMarks) {
        this.rollno = rollno;
        this.bundleNumber = bundleNumber;
        this.numberofBooklets = numberofBooklets;
        this.rollnumberMarks = rollnumberMarks;
    }

    public MyData() {
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getBundleNumber() {
        return bundleNumber;
    }

    public void setBundleNumber(String bundleNumber) {
        this.bundleNumber = bundleNumber;
    }

    public String getNumberofBooklets() {
        return numberofBooklets;
    }

    public void setNumberofBooklets(String numberofBooklets) {
        this.numberofBooklets = numberofBooklets;
    }

    public String getRollnumberMarks() {
        return rollnumberMarks;
    }

    public void setRollnumberMarks(String rollnumberMarks) {
        this.rollnumberMarks = rollnumberMarks;
    }
}