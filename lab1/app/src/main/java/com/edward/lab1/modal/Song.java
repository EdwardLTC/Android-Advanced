package com.edward.lab1.modal;

import java.io.Serializable;

public class Song implements Serializable {
    private String title;

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

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    private String author;
    private int resource;
    public Song(String title, String author, int resource) {
        this.title = title;
        this.author = author;
        this.resource = resource;
    }


}
