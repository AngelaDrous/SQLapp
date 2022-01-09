package com.example.sqlapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "Database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL("create Table users(username Text primary key, password Text)");  // create the USERS table

    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int oldVersion, int newVersion) {
        myDB.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String username, String password)  // function that inserts data in the USERS table in the database
    {
        SQLiteDatabase myDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = myDB.insert("users", null, contentValues);

        // if the data insertion is successful the function returns values, BUT if it is NOT successful it returns -1
        if (result == -1)
        {
            // registration failed
            return  false;
        }
        else
        {
            // registration successful
            return true;
        }

    }

    // function that checks if user exists in the database
    public Boolean checkUsername(String username)
    {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username = ?", new String[] {username}); // if the data exists the cursor will have some data

        // check if cursor has any data or not
        if (cursor.getCount() > 0)
        {
            // cursor has some data (user exists)
            return true;
        } else {
            return  false;
        }
    }

    // check the username and password
    public Boolean checkUsernamePassword (String username, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username = ? and password = ?", new String[] {username, password});

        // if the data exists then return true
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

}
