package com.edward.myapplication.helper;

import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_COURSE_ID;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_COURSE_NAME;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_COURSE_SCHEDULE;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_COURSE_TESTSCHEDULE;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_REGISTER_ID;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_USER_ADDRESS;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_USER_DATEOFBIRTH;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_USER_ID;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_USER_NAME;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_USER_PHONENUM;
import static com.edward.myapplication.config.CONFIG.DATABASE_NAME;
import static com.edward.myapplication.config.CONFIG.DATABASE_TABLE_COURSE;
import static com.edward.myapplication.config.CONFIG.DATABASE_TABLE_REGISINFO;
import static com.edward.myapplication.config.CONFIG.DATABASE_TABLE_USER;
import static com.edward.myapplication.config.CONFIG.DATABASE_VERSION;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CourseManagementDB extends SQLiteOpenHelper {
    // TODO: 9/5/2022

    public CourseManagementDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //user table
        String sqlUser = "CREATE TABLE " + DATABASE_TABLE_USER +
                "( " +
                DATABASE_KEY_USER_ID + " TEXT primary key, " +
                DATABASE_KEY_USER_NAME + " TEXT, " +
                DATABASE_KEY_USER_DATEOFBIRTH + " TEXT, " +
                DATABASE_KEY_USER_ADDRESS + " TEXT, " +
                DATABASE_KEY_USER_PHONENUM + " TEXT " +
                ")";

        sqLiteDatabase.execSQL(sqlUser);

        //course table
        String sqlCourse = " CREATE TABLE " + DATABASE_TABLE_COURSE +
                "(" +
                DATABASE_KEY_COURSE_ID + " INTEGER primary key , " +
                DATABASE_KEY_COURSE_NAME + " TEXT, " +
                DATABASE_KEY_COURSE_SCHEDULE + " TEXT, " +
                DATABASE_KEY_COURSE_TESTSCHEDULE + " TEXT " +
                ")";

        sqLiteDatabase.execSQL(sqlCourse);

        //regis info table
        String sqlRegister = " CREATE TABLE " + DATABASE_TABLE_REGISINFO +
                "(" +
                DATABASE_KEY_REGISTER_ID + " INTEGER primary key autoincrement, " +
                DATABASE_KEY_USER_ID + " TEXT, " +
                DATABASE_KEY_COURSE_ID + " INTEGER, " +
                "FOREIGN  KEY (" + DATABASE_KEY_COURSE_ID + ") REFERENCES " + DATABASE_TABLE_COURSE + " (" + DATABASE_KEY_COURSE_ID + ") ON DELETE CASCADE," +
                "FOREIGN  KEY (" + DATABASE_KEY_USER_ID + ") REFERENCES " + DATABASE_TABLE_USER + " (" + DATABASE_KEY_USER_ID + ") ON DELETE CASCADE " +
                ")";
        sqLiteDatabase.execSQL(sqlRegister);

        String tempDataCourse = "INSERT INTO " + DATABASE_TABLE_COURSE +
                " VALUES ('1','Android','case 2','22/4')," +
                "('2','Android 2','case 2','22/4')," +
                "('3','Android 3','case 2','22/4')";
        sqLiteDatabase.execSQL(tempDataCourse);

        String tempDataUser = "INSERT INTO " + DATABASE_TABLE_USER + " VALUES" +
                "('ps01','Cong','null','null','null')," +
                "('ps02','Mai','null','null','null')," +
                "('ps03','Dung','null','null','null')";
        sqLiteDatabase.execSQL(tempDataUser);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + DATABASE_TABLE_USER);
        sqLiteDatabase.execSQL("drop table if exists " + DATABASE_TABLE_COURSE);
        sqLiteDatabase.execSQL("drop table if exists " + DATABASE_TABLE_REGISINFO);
        onCreate(sqLiteDatabase);
    }
}
