package com.edward.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Bai02Activity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> ds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai02);

        listView = findViewById(R.id.listView);

        readBookmarks();
    }

    @SuppressLint("Range")
    private void readBookmarks() {
        Uri uri = Uri.parse("content://browser/bookmarks");
        String[] history = new String[]{
                "_id",
                "url",
                "title",
        };
        ContentResolver ctr = getContentResolver();
        Cursor c = ctr.query(uri, history, null, null, null);
        if (c.getCount()<1){
            ds.add("null");
        }
        if (c.moveToFirst()) {
            do {
                String url = c.getString(1);
                String title = c.getString(2);
                ds.add(url + " - " + title + " - ");
            } while (c.moveToNext());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Bai02Activity.this, android.R.layout.simple_list_item_1, ds);
        listView.setAdapter(adapter);
    }
}