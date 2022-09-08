package com.edward.myapplication.modal;

public class RSS {
    // TODO: 9/8/2022  
    public RSS(String title, String des) {
        this.title = title;
        this.des = des;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    private String des;

}
