package com.login.mobi.loginapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context) {
        super(context, DatabaseOptions.DB_NAME, null, DatabaseOptions.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table
        db.execSQL(DatabaseOptions.CREATE_USERS_TABLE_);
        db.execSQL(DatabaseOptions.CREATE_DIARY_TABLE_);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseOptions.USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseOptions.DIARY_TABLE);
        // Create tables again
        onCreate(db);
    }

    public User queryUser(String email, String password) {

        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        Cursor cursor = db.query(DatabaseOptions.USERS_TABLE, new String[]{DatabaseOptions.ID,
                        DatabaseOptions.EMAIL, DatabaseOptions.PASSWORD}, DatabaseOptions.EMAIL + "=? and " + DatabaseOptions.PASSWORD + "=?",
                new String[]{email, password}, null, null, null, "1");
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            user = new User(cursor.getString(1), cursor.getString(2));
        }
        // return user
        return user;
    }

    public Diary queryDiary(String uid, String date, String title, String text, String address) {
        SQLiteDatabase db = this.getReadableDatabase();
        Diary diary = null;

        Cursor cursor = db.query(DatabaseOptions.DIARY_TABLE, new String[] {
                DatabaseOptions.UID, DatabaseOptions.DID, DatabaseOptions.DATE, DatabaseOptions.TITLE, DatabaseOptions.TEXT, DatabaseOptions.ADDRESS
        }, DatabaseOptions.UID + "=? and" + DatabaseOptions.DATE + "=? and" + DatabaseOptions.TITLE + "=? and" + DatabaseOptions.TEXT + "=? and" + DatabaseOptions.ADDRESS + "=?",
                new String[] {uid, date, title, text, address}, null, null, null, "1");
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            diary = new Diary(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
        }
        // return diary
        return diary;
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseOptions.EMAIL, user.getEmail());
        values.put(DatabaseOptions.PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(DatabaseOptions.USERS_TABLE, null, values);
        db.close(); // Closing database connection

    }

    public void addDiary(Diary diary) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseOptions.UID, diary.getUid());
        values.put(DatabaseOptions.DATE, diary.getDate());
        values.put(DatabaseOptions.TITLE, diary.getTitle());
        values.put(DatabaseOptions.TEXT, diary.getText());
        values.put(DatabaseOptions.ADDRESS, diary.getAddress());

        // Inserting Row
        db.insert(DatabaseOptions.DIARY_TABLE, null, values);
        db.close(); // Closing database connection
    }
    }
