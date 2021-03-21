package com.example.kissanapp;

public class notificationAttr {
    String receiverid;
    String title;
    String date;
    String time;
    String status;
    String description;

    public notificationAttr() {
    }

    public String getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public notificationAttr(String receiverid, String title, String date, String time, String status, String description) {
        this.receiverid = receiverid;
        this.title = title;
        this.date = date;
        this.time = time;
        this.status = status;
        this.description = description;
    }
}
