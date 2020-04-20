package com.example.takemyattendance;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"subName","subCode","stream","section","batch","sem"})
public class ClassData {
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

    public ClassData(String subName, String subCode, String stream, String section, String batch, String sem) {
        this.subName = subName;
        this.subCode = subCode;
        this.stream = stream;
        this.section = section;
        this.batch = batch;
        this.sem = sem;
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
}
