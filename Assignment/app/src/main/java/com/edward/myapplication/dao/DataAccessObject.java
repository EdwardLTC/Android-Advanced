package com.edward.myapplication.dao;

import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_COURSE_ID;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_USER_ID;
import static com.edward.myapplication.config.CONFIG.DATABASE_TABLE_COURSE;
import static com.edward.myapplication.config.CONFIG.DATABASE_TABLE_REGISINFO;
import static com.edward.myapplication.config.CONFIG.DATABASE_TABLE_USER;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.edward.myapplication.helper.CourseManagementDB;
import com.edward.myapplication.modal.Course;
import com.edward.myapplication.modal.User;

import java.util.ArrayList;

public class DataAccessObject extends CourseManagementDB {
    // TODO: 9/5/2022
    private static CourseManagementDB courseManagementDB;

    // TODO: 9/6/2022  
    public DataAccessObject(Context context) {
        super(context);
        courseManagementDB = new CourseManagementDB(context);
    }

    //user role
    public ArrayList<Course> getAllCourse() {
        SQLiteDatabase sqLiteDatabase = courseManagementDB.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+DATABASE_TABLE_COURSE, null );
        ArrayList<Course> list = new ArrayList<>();
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                list.add(new Course(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public ArrayList<Course> getAllRegisteredCourses(String m_userID){
        SQLiteDatabase sqLiteDatabase = courseManagementDB.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+DATABASE_TABLE_COURSE +" co," + DATABASE_TABLE_REGISINFO
                + " info where co."+DATABASE_KEY_COURSE_ID +" and info."+DATABASE_KEY_USER_ID+ " like '" + m_userID +"'", null );
        ArrayList<Course> list = new ArrayList<>();
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                list.add(new Course(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public void handleUnRegisterCourse(String m_userID, int m_courseID){
        SQLiteDatabase sqLiteDatabase = courseManagementDB.getWritableDatabase();
        sqLiteDatabase.delete(DATABASE_TABLE_REGISINFO,DATABASE_KEY_USER_ID + " = ? and " +DATABASE_KEY_COURSE_ID +" = ?",new String[]{m_userID,String.valueOf(m_courseID)});
    }

    public void handleRegisterCourse(String m_userID, int m_courseID){
        SQLiteDatabase sqLiteDatabase = courseManagementDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATABASE_KEY_USER_ID, m_userID);
        contentValues.put(DATABASE_KEY_COURSE_ID,m_courseID);
        sqLiteDatabase.insert(DATABASE_TABLE_REGISINFO,null,contentValues);
    }

    //admin role
    public ArrayList<User> getAllUser(){
        SQLiteDatabase sqLiteDatabase = courseManagementDB.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+DATABASE_TABLE_USER, null );
        ArrayList<User> list = new ArrayList<>();
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                list.add(new User(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

}
