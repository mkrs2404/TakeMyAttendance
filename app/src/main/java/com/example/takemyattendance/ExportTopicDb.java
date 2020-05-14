package com.example.takemyattendance;

public class ExportTopicDb {

    private String date;
    private String topic;
    private String period;

    public ExportTopicDb(String date, String topic, String period) {
        this.date = date;
        this.topic = topic;
        this.period = period;
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
