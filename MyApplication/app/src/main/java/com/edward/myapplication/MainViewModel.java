package com.edward.myapplication;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.edward.myapplication.dao.DataAccessObject;
import com.edward.myapplication.models.User;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<User>> listMutableLiveData;
    private final DataAccessObject dataAccessObject;
    public MainViewModel(Context context) {
        listMutableLiveData = new MutableLiveData<>();
        dataAccessObject = new DataAccessObject(context).getInstance(context);
        notifyDataChange();
    }

    public MutableLiveData<ArrayList<User>> getListMutableLiveData() {
        if (listMutableLiveData == null) {
            listMutableLiveData = new MutableLiveData<>();
        }
        return listMutableLiveData;
    }

    public void notifyDataChange(){
        listMutableLiveData.setValue(dataAccessObject.getAll());
    }
}
