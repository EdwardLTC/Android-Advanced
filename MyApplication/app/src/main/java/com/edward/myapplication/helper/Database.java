package com.edward.myapplication.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Database extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "USER";
    public static int DATABASE_VERSION = 1;
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FULLNAME = "fullname";
    private static Database instance;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public synchronized Database getInstance(Context context) {
        if (instance == null) instance = new Database(context);
        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + DATABASE_NAME + "( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_USERNAME + " TEXT,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_FULLNAME + " TEXT "
                + ")");

        String init = "INSERT INTO " + DATABASE_NAME + " VALUES" +
                "(1,'Cong','123','Le Thanh Cong')," +
                "(2,'Mai','123','Vo Thi Xuan Mai')," +
                "(3,'Dung','123','Le Thanh Cong')," +
                "(4,'Thu','123','Minh Thu')," +
                "(5,'Vy','123','Pham Truong Yen Vy')";
        sqLiteDatabase.execSQL(init);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(sqLiteDatabase);
    }
}
