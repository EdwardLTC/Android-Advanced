package com.edward.myapplication.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

public class User extends BaseObservable {
    public ObservableField<Integer> id = new ObservableField<>();
    public ObservableField<String> userName = new ObservableField<>();
    public ObservableField<String> passWord = new ObservableField<>();
    public ObservableField<String> fullName = new ObservableField<>();

    public User(int id, String userName,String passWord,String fullName) {
        this.id.set(id);
        this.userName.set(userName);
        this.passWord.set(passWord);
        this.fullName.set(fullName);
    }


}
