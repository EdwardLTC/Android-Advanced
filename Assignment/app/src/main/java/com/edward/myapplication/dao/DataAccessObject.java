package com.edward.myapplication.dao;

import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_COURSE_ID;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_COURSE_NAME;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_COURSE_SCHEDULE;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_COURSE_TESTSCHEDULE;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_REGISTER_ID;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_USER_ID;
import static com.edward.myapplication.config.CONFIG.DATABASE_TABLE_COURSE;
import static com.edward.myapplication.config.CONFIG.DATABASE_TABLE_REGISINFO;
import static com.edward.myapplication.config.CONFIG.DATABASE_TABLE_USER;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.edward.myapplication.helper.CourseManagementDB;
import com.edward.myapplication.modal.Course;
import com.edward.myapplication.modal.RegisterInfo;
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
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + DATABASE_TABLE_COURSE, null);
        ArrayList<Course> list = new ArrayList<>();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new Course(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public ArrayList<Course> getAllRegisteredCourses(String m_userID) {
        SQLiteDatabase sqLiteDatabase = courseManagementDB.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + DATABASE_TABLE_COURSE + " co," + DATABASE_TABLE_REGISINFO
                + " info where co." + DATABASE_KEY_COURSE_ID + " = info." + DATABASE_KEY_COURSE_ID + " and info." + DATABASE_KEY_USER_ID + " like '" + m_userID + "'", null);
        ArrayList<Course> list = new ArrayList<>();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new Course(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean handleUnRegisterCourse(String m_userID, int m_courseID) {
        SQLiteDatabase sqLiteDatabase = courseManagementDB.getWritableDatabase();
        long i = sqLiteDatabase.delete(DATABASE_TABLE_REGISINFO, DATABASE_KEY_USER_ID + "=? and " + DATABASE_KEY_COURSE_ID + "=?", new String[]{m_userID, String.valueOf(m_courseID)});
        return i != -1;
    }

    public boolean handleRegisterCourse(String m_userID, int m_courseID) {
        SQLiteDatabase sqLiteDatabase = courseManagementDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATABASE_KEY_USER_ID, m_userID);
        contentValues.put(DATABASE_KEY_COURSE_ID, m_courseID);
        long i = sqLiteDatabase.insert(DATABASE_TABLE_REGISINFO, null, contentValues);
        return i != -1;
    }

    //admin role
    public ArrayList<User> getAllUser() {
        SQLiteDatabase sqLiteDatabase = courseManagementDB.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + DATABASE_TABLE_USER, null);
        ArrayList<User> list = new ArrayList<>();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean handleRemoveUser(String _userId) {
        SQLiteDatabase sqLiteDatabase = courseManagementDB.getWritableDatabase();
        long i = sqLiteDatabase.delete(DATABASE_TABLE_USER, DATABASE_KEY_USER_ID + "=?", new String[]{_userId});
        return i != -1;
    }

    public boolean handleRemoveCourse(int _courseId) {
        SQLiteDatabase sqLiteDatabase = courseManagementDB.getWritableDatabase();
        long i = sqLiteDatabase.delete(DATABASE_TABLE_COURSE, DATABASE_KEY_COURSE_ID + "=?", new String[]{String.valueOf(_courseId)});
        return i != -1;
    }

    public ArrayList<RegisterInfo> getAllRegisterInfo() {
        SQLiteDatabase sqLiteDatabase = courseManagementDB.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + DATABASE_TABLE_REGISINFO, null);
        ArrayList<RegisterInfo> list = new ArrayList<>();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new RegisterInfo(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean handleAddCourse(Course course) {
        SQLiteDatabase sqLiteDatabase = courseManagementDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATABASE_KEY_COURSE_ID, course.get_ID());
        contentValues.put(DATABASE_KEY_COURSE_NAME, course.get_Name());
        contentValues.put(DATABASE_KEY_COURSE_SCHEDULE, course.get_Schedule());
        contentValues.put(DATABASE_KEY_COURSE_TESTSCHEDULE, course.get_testSchedule());
        long i = sqLiteDatabase.insert(DATABASE_TABLE_COURSE, null, contentValues);

        return i != -1;
    }

}
