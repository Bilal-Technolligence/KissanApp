package com.example.kissanapp;

public class notificationAttr {
    String receiverid;
    String title;
    String id;
    String status;
    String description;
    String orderId;

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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public notificationAttr(String receiverid, String title, String id, String status, String description, String orderId) {
        this.receiverid = receiverid;
        this.title = title;
        this.id = id;
        this.status = status;
        this.description = description;
        this.orderId = orderId;
    }
}
