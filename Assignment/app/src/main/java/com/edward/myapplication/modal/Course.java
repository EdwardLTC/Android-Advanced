package com.edward.myapplication.modal;

import android.content.Intent;

public class Course {
    private int _ID;
    private String _Name;
    private String _Schedule;
    private String _testSchedule;

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public String get_Name() {
        return _Name;
    }

    public void set_Name(String _Name) {
        this._Name = _Name;
    }

    public String get_Schedule() {
        return _Schedule;
    }

    public void set_Schedule(String _Schedule) {
        this._Schedule = _Schedule;
    }

    public String get_testSchedule() {
        return _testSchedule;
    }

    public void set_testSchedule(String _testSchedule) {
        this._testSchedule = _testSchedule;
    }


    public Course(int _ID, String _Name, String _Schedule, String _testSchedule) {
        this._ID = _ID;
        this._Name = _Name;
        this._Schedule = _Schedule;
        this._testSchedule = _testSchedule;
    }

    @Override
    public String toString() {
        return "Course{" +
                "_ID=" + _ID +
                ", _Name='" + _Name + '\'' +
                ", _Schedule='" + _Schedule + '\'' +
                ", _testSchedule='" + _testSchedule + '\'' +
                '}';
    }
}
