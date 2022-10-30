package com.edward.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.edward.myapplication.helper.Database;
import com.edward.myapplication.models.User;

import java.util.ArrayList;

public class DataAccessObject {
    public static String DATABASE_NAME = "USER";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FULLNAME = "fullname";
    private static Database database;
    private DataAccessObject instance;

    public DataAccessObject(Context context) {
        database = new Database(context).getInstance(context);
    }

    public synchronized DataAccessObject getInstance(Context context) {
        if (instance == null) instance = new DataAccessObject(context);
        return instance;
    }

    public ArrayList<User> getAll() {
        SQLiteDatabase sqLiteDatabase = database.getReadableDatabase();
        ArrayList<User> list = new ArrayList<>();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + DATABASE_NAME, null);
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    list.add(new User(cursor.getInt(0),cursor.getString(1),null,cursor.getString(3)));
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            return list;
        }
        return list;
    }

    public User handleLogin(String username, String passWord){
        SQLiteDatabase sqLiteDatabase = database.getReadableDatabase();
        User user = null;
        try {
            Cursor cursor = sqLiteDatabase.rawQuery(
                    "select * from " + DATABASE_NAME + " where " + KEY_USERNAME + "=? AND " + KEY_PASSWORD + "=?", new String[]{username,passWord});
            cursor.moveToFirst();
            if (cursor.getCount() != 0) {
                user = new User(cursor.getInt(0), cursor.getString(1), null, cursor.getString(3));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }


}

