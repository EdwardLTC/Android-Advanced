package com.edward.myapplication.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.edward.myapplication.R;

public class DialogCustom extends Dialog {
    Context context;
    DialogCallback dialogCallback;

    public DialogCustom(@NonNull Context context, DialogCallback dialogCallback) {
        super(context);
        this.context = context;
        this.dialogCallback = dialogCallback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_components);
        Button yes = findViewById(R.id.btn_yes);
        Button no = findViewById(R.id.btn_no);
        EditText name = findViewById(R.id.name);
        EditText price = findViewById(R.id.price);
        EditText amount = findViewById(R.id.amount);
        EditText des = findViewById(R.id.des);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCallback.onClickYes(name,price,amount,des);
                dismiss();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
