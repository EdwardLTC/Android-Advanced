package com.edward.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.edward.myapplication.helper.CourseManagementDB;
import com.edward.myapplication.modal.Course;
import com.edward.myapplication.modal.User;

import java.util.ArrayList;

public class DataAccsesObject extends CourseManagementDB {
    // TODO: 9/5/2022
    private static CourseManagementDB courseManagementDB;
    private static final String TABLE_USER = "user";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_full_name";
    private static final String KEY_USER_DATEOFBIRTH = "user_date_of_birth";
    private static final String KEY_USER_ADDRESS = "user_address";
    private static final String KEY_USER_PHONENUM = "user_phone_number";

    private static final String TABLE_COURSE = "course";
    private static final String KEY_COURSE_ID = "course_id";
    private static final String KEY_COURSE_NAME = "course_name";
    private static final String KEY_COURSE_SCHEDULE = "course_schedule";
    private static final String KEY_COURSE_TESTSCHEDULE = "user_test_schedule";

    private static final String TABLE_REGISINFO ="RegisInfo";
    private static final String KEY_REGIST_ID ="id";

    public DataAccsesObject(Context context) {
        super(context);
        courseManagementDB = new CourseManagementDB(context);
    }

    //user role
    public ArrayList<Course> getAllCourse() {
        SQLiteDatabase sqLiteDatabase = courseManagementDB.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+TABLE_COURSE, null );
        ArrayList<Course> list = new ArrayList<>();
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                list.add(new Course(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    public ArrayList<Course> getAllRegisteredCourses(String m_userID){
        SQLiteDatabase sqLiteDatabase = courseManagementDB.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+TABLE_COURSE +" co," + TABLE_REGISINFO
                + " info where co."+KEY_COURSE_ID +" and info."+KEY_USER_ID+ " like '" + m_userID +"'", null );
        ArrayList<Course> list = new ArrayList<>();
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                list.add(new Course(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    public void handleUnRegisterCourse(String m_userID, int m_courseID){
        SQLiteDatabase sqLiteDatabase = courseManagementDB.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_REGISINFO,KEY_USER_ID + " = ? and " +KEY_COURSE_ID +" = ?",new String[]{m_userID,String.valueOf(m_courseID)});
    }

    public void handleRegisterCourse(String m_userID, int m_courseID){
        SQLiteDatabase sqLiteDatabase = courseManagementDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USER_ID, m_userID);
        contentValues.put(KEY_COURSE_ID,m_courseID);
        sqLiteDatabase.insert(TABLE_REGISINFO,null,contentValues);
    }

    //admin role
    public ArrayList<User> getAllUser(){
        SQLiteDatabase sqLiteDatabase = courseManagementDB.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+TABLE_USER, null );
        ArrayList<User> list = new ArrayList<>();
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                list.add(new User(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)));
            }while (cursor.moveToNext());
        }

        return list;
    }

}
