package com.example.a15firebase.models;

public class Mensaje {
    String to;
    Notification notification;
    public Mensaje(String to, Notification notification) {
        this.to = to;
        this.notification = notification;
    }


    public String getTo() {
        return to;
    }
    public Notification getNotification() {
        return notification;
    }
}
