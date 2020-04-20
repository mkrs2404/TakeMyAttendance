package com.example.takemyattendance;

public class StudentData {

    private String roll;
    private String name;
    private String phone;
    private String email;
    private String parent_phone;

    public StudentData(String roll, String name, String phone, String email, String parent_phone) {
        this.roll = roll;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.parent_phone = parent_phone;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParent_phone() {
        return parent_phone;
    }

    public void setParent_phone(String parent_phone) {
        this.parent_phone = parent_phone;
    }
}
