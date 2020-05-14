package com.example.takemyattendance;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"subName","subCode","stream","section","batch","sem","roll","name","phone","email","parent_phone","date"})
public class DailyAttendanceDbClass{

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
    @NonNull
    private String date;

    private String topic;
    private int totalPeriods;
    private int attendedPeriods;

    public DailyAttendanceDbClass(@NonNull String subName, @NonNull String subCode, @NonNull String stream, @NonNull String section, @NonNull String batch, @NonNull String sem, @NonNull String roll, @NonNull String name, @NonNull String phone, @NonNull String email, @NonNull String parent_phone, @NonNull String date, String topic,int attendedPeriods, int totalPeriods) {
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
        this.date = date;
        this.topic = topic;
        this.attendedPeriods = attendedPeriods;
        this.totalPeriods = totalPeriods;
    }

    @NonNull
    public String getSubName() {
        return subName;
    }

    public void setSubName(@NonNull String subName) {
        this.subName = subName;
    }

    @NonNull
    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(@NonNull String subCode) {
        this.subCode = subCode;
    }

    @NonNull
    public String getStream() {
        return stream;
    }

    public void setStream(@NonNull String stream) {
        this.stream = stream;
    }

    @NonNull
    public String getSection() {
        return section;
    }

    public void setSection(@NonNull String section) {
        this.section = section;
    }

    @NonNull
    public String getBatch() {
        return batch;
    }

    public void setBatch(@NonNull String batch) {
        this.batch = batch;
    }

    @NonNull
    public String getSem() {
        return sem;
    }

    public void setSem(@NonNull String sem) {
        this.sem = sem;
    }

    @NonNull
    public String getRoll() {
        return roll;
    }

    public void setRoll(@NonNull String roll) {
        this.roll = roll;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getPhone() {
        return phone;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getParent_phone() {
        return parent_phone;
    }

    public void setParent_phone(@NonNull String parent_phone) {
        this.parent_phone = parent_phone;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getTotalPeriods() {
        return totalPeriods;
    }

    public void setTotalPeriods(int totalPeriods) {
        this.totalPeriods = totalPeriods;
    }

    public int getAttendedPeriods() {
        return attendedPeriods;
    }

    public void setAttendedPeriods(int attendedPeriods) {
        this.attendedPeriods = attendedPeriods;
    }
}
