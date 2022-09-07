package com.edward.myapplication.modal;

public class RegisterInfo {
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_uid() {
        return _uid;
    }

    public void set_uid(String _uid) {
        this._uid = _uid;
    }

    public int get_cid() {
        return _cid;
    }

    public void set_cid(int _cid) {
        this._cid = _cid;
    }

    private int _id;
    private String _uid;
    private int _cid;

    public RegisterInfo(int _id, String _uid, int _cid) {
        this._id = _id;
        this._uid = _uid;
        this._cid = _cid;
    }

    @Override
    public String toString() {
        return "RegisterInfo{" +
                "_id=" + _id +
                ", _uid='" + _uid + '\'' +
                ", _cid=" + _cid +
                '}';
    }
}
