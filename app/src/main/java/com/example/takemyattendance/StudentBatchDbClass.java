package com.example.takemyattendance;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"subName","subCode","stream","section","batch","sem","roll","name","phone","email","parent_phone"})
public class StudentBatchDbClass {

    @NonNull
    private String subName;
    @NonNull
    private String subCode;
    @NonNull
    private String stream;
    @NonNull
    private String section;
    @NonNull
    private String batch;
    @NonNull
    private String sem;
    @NonNull
    private String roll;
    @NonNull
    private String name;
    @NonNull
    private String phone;
    @NonNull
    private String email;
    @NonNull
    private String parent_phone;

    private int attendedClasses;
    private int totalClasses;

    public StudentBatchDbClass(String subName, String subCode, String stream, String section, String batch, String sem, String roll, String name, String phone, String email, String parent_phone, int attendedClasses, int totalClasses) {
        this.subName = subName;
        this.subCode = subCode;
        this.stream = stream;
        this.section = section;
        this.batch = batch;
        this.sem = sem;
        this.roll = roll;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.parent_phone = parent_phone;
        this.attendedClasses = attendedClasses;
        this.totalClasses = totalClasses;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
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

    public int getAttendedClasses() {
        return attendedClasses;
    }

    public void setAttendedClasses(int attendedClasses) {
        this.attendedClasses = attendedClasses;
    }

    public int getTotalClasses() {
        return totalClasses;
    }

    public void setTotalClasses(int totalClasses) {
        this.totalClasses = totalClasses;
    }
}
