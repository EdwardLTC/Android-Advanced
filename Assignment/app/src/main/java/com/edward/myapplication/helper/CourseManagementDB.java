package com.edward.myapplication.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CourseManagementDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CourseManagement";
    private static final int DATABASE_VERSION = 1;

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

    public CourseManagementDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //user table
        String sqlUser = "CREATE TABLE " + TABLE_USER +
                "( " +
                KEY_USER_ID + " TEXT primary key, " +
                KEY_USER_NAME + " TEXT, " +
                KEY_USER_DATEOFBIRTH + " TEXT, " +
                KEY_USER_ADDRESS + " TEXT, " +
                KEY_USER_PHONENUM + " TEXT, " +
                "FOREIGN  KEY (" + KEY_USER_ID + ") REFERENCES "+TABLE_REGISINFO+" (" + KEY_USER_ID + ") ON DELETE CASCADE" +
                ")";

        sqLiteDatabase.execSQL(sqlUser);

        //course table
        String sqlCourse = " CREATE TABLE " + TABLE_COURSE +
                "(" +
                KEY_COURSE_ID + " INTEGER primary key, " +
                KEY_COURSE_NAME + " TEXT, " +
                KEY_COURSE_SCHEDULE + " TEXT, " +
                KEY_COURSE_TESTSCHEDULE + " TEXT, " +
                "FOREIGN  KEY (" + KEY_COURSE_ID + ") REFERENCES "+TABLE_REGISINFO+" (" + KEY_COURSE_ID + ") ON DELETE CASCADE" +
                ")";

        sqLiteDatabase.execSQL(sqlCourse);

        //regis info table
        String sqlRegist = " CREATE TABLE " + TABLE_REGISINFO +
                "(" +
                KEY_REGIST_ID + " INTEGER primary key autoincrement, " +
                KEY_USER_ID + " TEXT, " +
                KEY_COURSE_ID + " INTEGER " +
                ")";
        sqLiteDatabase.execSQL(sqlRegist);

        String tempData ="INSERT INTO " + TABLE_COURSE +
                " VALUES ('1','Android','case 2','22/4'),"+
                "('2','Android 2','case 2','22/4'),"+
                "('3','Android 3','case 2','22/4')";
        sqLiteDatabase.execSQL(tempData);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_USER);
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_COURSE);
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_REGISINFO);
        onCreate(sqLiteDatabase);
    }
}
