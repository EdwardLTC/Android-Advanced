package com.edward.myapplication.modal;

public class User {
    private String _ID;
    private String _FullName;
    private String _DateOfBirth;
    private String _Address;
    private String _PhoneNum;

    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }

    public String get_FullName() {
        return _FullName;
    }

    public void set_FullName(String _FullName) {
        this._FullName = _FullName;
    }

    public String get_DateOfBirth() {
        return _DateOfBirth;
    }

    public void set_DateOfBirth(String _DateOfBirth) {
        this._DateOfBirth = _DateOfBirth;
    }

    public String get_Address() {
        return _Address;
    }

    public void set_Address(String _Address) {
        this._Address = _Address;
    }

    public String get_PhoneNum() {
        return _PhoneNum;
    }

    public void set_PhoneNum(String _PhoneNum) {
        this._PhoneNum = _PhoneNum;
    }

    public User(String _ID, String _FullName, String _DateOfBirth, String _Address, String _PhoneNum) {
        this._ID = _ID;
        this._FullName = _FullName;
        this._DateOfBirth = _DateOfBirth;
        this._Address = _Address;
        this._PhoneNum = _PhoneNum;
    }


}
