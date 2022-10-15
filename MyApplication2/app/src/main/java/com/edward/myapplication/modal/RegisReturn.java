package com.edward.myapplication.modal;

public class RegisReturn {
    private String Notification;
    private int Status;
    public RegisReturn(String notification, int status) {
        Notification = notification;
        Status = status;
    }

    public String getNotification() {
        return Notification;
    }

    public void setNotification(String notification) {
        Notification = notification;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
