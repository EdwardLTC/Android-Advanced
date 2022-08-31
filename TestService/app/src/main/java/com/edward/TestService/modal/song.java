package com.edward.TestService.modal;

import java.io.Serializable;

public class song implements Serializable {
    private String title;
    private String author;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    private int img;
    private int resource;

    public song(String title, String author, int img, int resource) {
        this.title = title;
        this.author = author;
        this.img = img;
        this.resource = resource;
    }


}
