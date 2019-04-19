package com.login.mobi.loginapp;

/**
 * Created by Zoom on 10/1/2018.
 */

public class DatabaseOptions {

    public static final String DB_NAME = "local.db";
    public static final int DB_VERSION = 1;

    public static final String USERS_TABLE = "users";

    public static final String ID = "id";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    public static final String CREATE_USERS_TABLE_ =
            "CREATE TABLE  " + USERS_TABLE + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    EMAIL + " TEXT NOT NULL," +
                    PASSWORD + " TEXT );";

    public static final String DIARY_TABLE = "diary";

    public static final String DID = "did";
    public static final String DATE = "date";
    public static final String TITLE = "title";
    public static final String ADDRESS = "address";
    public static final String TEXT = "text";

    public static final String CREATE_DIARY_TABLE_ =
            "CREATE TABLE  " + DIARY_TABLE + "(" +
                    DID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DATE + " TEXT NOT NULL," +
                    TITLE + "TEXT," +
                    ADDRESS + "TEXT," +
                    TEXT + " TEXT );";

}
