package com.edward.myapplication.adapter;

import android.widget.EditText;

public interface DialogCallback {
    void onClickYes(EditText name, EditText price, EditText amount, EditText des);
}
