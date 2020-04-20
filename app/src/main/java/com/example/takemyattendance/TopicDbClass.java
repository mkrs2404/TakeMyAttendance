package com.example.takemyattendance;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"date"})
public class TopicDbClass{

    @NonNull
    private String date;
    private String topic;
    private String period;
    private String subName;
    private String subCode;
    private String stream;
    private String section;
    private String batch;
    private String sem;

    public TopicDbClass(String subName, String subCode, String stream, String section, String batch, String sem,String date, String topic, String period) {
        this.subName = subName;
        this.subCode = subCode;
        this.stream = stream;
        this.section = section;
        this.batch = batch;
        this.sem = sem;
        this.date = date;
        this.topic = topic;
        this.period = period;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
