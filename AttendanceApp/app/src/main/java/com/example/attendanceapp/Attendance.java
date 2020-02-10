package com.example.attendanceapp;

public class Attendance {
    private String name;
    private String rollnumber;
    private boolean attendance=false;

    public Attendance(String name, String rollnumber, boolean attendance) {
        this.name = name;
        this.rollnumber = rollnumber;
        this.attendance = attendance;
    }

    public Attendance() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRollnumber(String rollnumber) {
        this.rollnumber = rollnumber;
    }

    public void setAttendance(boolean attendance) {
        this.attendance = attendance;
    }

    public String getName() {
        return name;
    }

    public String getRollnumber() {
        return rollnumber;
    }

    public boolean isAttendance() {
        return attendance;
    }
}
