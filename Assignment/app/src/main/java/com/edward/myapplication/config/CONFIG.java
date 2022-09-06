package com.edward.myapplication.config;


public class CONFIG {
    // TODO: 9/6/2022
    // Database
    public static final String DATABASE_NAME = "CourseManagement";
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_TABLE_USER = "user";
    public static final String DATABASE_KEY_USER_ID = "user_id";
    public static final String DATABASE_KEY_USER_NAME = "user_full_name";
    public static final String DATABASE_KEY_USER_DATEOFBIRTH = "user_date_of_birth";
    public static final String DATABASE_KEY_USER_ADDRESS = "user_address";
    public static final String DATABASE_KEY_USER_PHONENUM = "user_phone_number";

    public static final String DATABASE_TABLE_COURSE = "course";
    public static final String DATABASE_KEY_COURSE_ID = "course_id";
    public static final String DATABASE_KEY_COURSE_NAME = "course_name";
    public static final String DATABASE_KEY_COURSE_SCHEDULE = "course_schedule";
    public static final String DATABASE_KEY_COURSE_TESTSCHEDULE = "user_test_schedule";

    public static final String DATABASE_TABLE_REGISINFO = "RegisInfo";
    public static final String DATABASE_KEY_REGISTER_ID = "id";


    // Service Get All Course
    public static final String SERVICE_GETALLCOURSE_NAME = "GetAllCourseServices";
    public static final String SERVICE_GETALLCOURSE_USER_ID = "userID";
    public static final String SERVICE_GETALLCOURSE_KEY ="isMine";
    public static final String INTENT_GETALLCOURSE_ACTION ="GetAllCourse";
    public static final String INTENT_GETALLCOURSE_KEY_ALLCOURSE = "allCourse";
    public static final String INTENT_GETALLCOURSE_KEY_REGISTERED = "allCourseRegistered";

    //Service Handle Course
    public static final String SERVICE_HANDLE_NAME = "HandleCourseService";
    public static final String INTENT_HANDLE_KEY_ALLCOURSE = "allCourse";
    public static final String INTENT_HANDLE_KEY_ALLCOURSEREGISTERED = "allCourseRegistered";
    public static final String INTENT_HANDLE_ACTION = "HandleCourseService";
    public static final String INTENT_HANDLE_KEY_ISREGISTERED = "handleCourse";

    public static final String SERVICE_RESULT = "resultCode";
    public static final String SERVICE_ACTION = "action" ;
}
