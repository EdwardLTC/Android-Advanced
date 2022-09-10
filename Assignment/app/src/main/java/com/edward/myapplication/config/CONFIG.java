package com.edward.myapplication.config;


import java.net.PortUnreachableException;

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

    //Service Get all User
    public static final String INTENT_GETALLUSER_KEY_USERLIST = "GetAllUserList";
    public static final String SERVICE_GETALLUSER_NAME = "GetAllUserService";
    public static final String INTENT_GETALLUSER_ACTION ="GetAllUser";

    //Service Get all Register Info
    public static final String INTENT_GETREGISTERINFO_KEY_REGISTINFO = "GetAllUserList";
    public static final String SERVICE_GETREGISTERINFO_NAME = "GetAllUserService";
    public static final String INTENT_GETREGISTERINFO_ACTION ="GetAllUser";

    //service Remove user
    public static final String SERVICE_REMOVE_NAME = "HandleRemoveUserCourse" ;
    public static final String INTENT_REMOVE_ACTION ="RemoveUserCourse";
    public static final String ACTION_REMOVE_KEY_REMOVEUSER ="ResultRemoveUser";
    public static final String ACTION_REMOVE_KEY_REMOVECOURSE ="ResultRemoveCourse";


    //text
    public static final float MAP_LATITUDE = (float) 10.8529391;
    public static final float MAP_LONGITUDE = (float) 106.6295448;
    public static final String MAP_TITLE = "FPT Polytechnic";
    public static final String MAP_CONTENT = "Thanh Cong cute lam a nha :>";
    public static final String RSS_LINK = "https://vnexpress.net/rss/giao-duc.rss";
    public static final String RSS_LOADING = "Loading RSS Feed...";
    public static final String CHOOSE_FLATFORM = "choose a platform to share";
    public static final String SHARE_TITLE = "Edward share";


}
