package com.example.takemyattendance;

public class TakeAttendanceClass {

    private String name;
    private String roll;

    public TakeAttendanceClass(String name, String roll) {
        this.name = name;
        this.roll = roll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }
}
