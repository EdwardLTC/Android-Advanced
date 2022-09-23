package com.edward.lab3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Bai01Activity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> ds = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai01);

        listView = findViewById(R.id.listView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(Bai01Activity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(Bai01Activity.this, Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Bai01Activity.this);
                    builder.setTitle("Contacts access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("please confirm Contacts access");
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 7979);
                        }
                    });
                    builder.show();

                } else {
                    ActivityCompat.requestPermissions(Bai01Activity.this, new String[]{Manifest.permission.READ_CONTACTS}, 7979);
                }
            } else {
                getContact();
            }
        } else {
            getContact();
        }

    }

    @SuppressLint("Range")
    void getContact() {
        ContentResolver ctr = getContentResolver();

        Cursor c = ctr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        Log.e(String.valueOf(c.getCount()), "getContact: " );
        if (c.getCount()<1){
            ds.add("null");
        }else {
            if (c.moveToFirst()) {
                do {
                    String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    if(Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0){
                        Cursor cursor = ctr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                        while (cursor.moveToNext()){
                            String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            ds.add(id + " - " + name + " - " + phone);
                        }
                    }
                } while (c.moveToNext());
            }
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Bai01Activity.this, android.R.layout.simple_list_item_1, ds);
        listView.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 7979) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getContact();
            } else {
                Toast.makeText(this, "khong co permission", Toast.LENGTH_SHORT).show();
            }
        }
    }
}