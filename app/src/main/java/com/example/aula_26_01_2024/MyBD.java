package com.example.aula_26_01_2024;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyBD extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "posts.db";
    public static final String TB_USERS = "tbUsers";
    public static final String USER_ID = "idUser";
    public static final String NICKNAME = "nickname";
    public static final String EMAIL = "email";
    public static final String FOTO = "foto";
    public static final String TB_POSTS = "tbPosts";
    public static final String POST_ID = "postid";
    public static final String LIKE = "likes";

    private static final String TAG = "MyBD";

    public MyBD(@Nullable Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlUsers = "CREATE TABLE " + TB_USERS + " ( " +
                USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NICKNAME + " TEXT, " +
                EMAIL + " TEXT, " +
                FOTO + " BLOB " +
                ");";
        db.execSQL(sqlUsers);

        String sqlPosts = "CREATE TABLE " + TB_POSTS + " ( " +
                POST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_ID + " INTEGER, " +
                FOTO + " BLOB, " +
                LIKE + " INTEGER, " +
                "FOREIGN KEY(" + USER_ID + ") REFERENCES " + TB_USERS + "(" + USER_ID + ")" +
                ");";
        db.execSQL(sqlPosts);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sqlUsers = "DROP TABLE IF EXISTS " + TB_USERS + ";";
        db.execSQL(sqlUsers);

        String sqlPosts = "DROP TABLE IF EXISTS " + TB_POSTS + ";";
        db.execSQL(sqlPosts);

        onCreate(db);
    }

    public long addUser(User add) {
        long resp = 0;
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues cv = add ContentValues();
            cv.put(NICKNAME, add.getNickname());
            cv.put(EMAIL, add.getEmail());
            cv.put(FOTO, add.getFoto());
            resp = db.insert(TB_USERS, null, cv);
            db.setTransactionSuccessful();
        } catch (SQLException erro) {
            Log.i(TAG, erro.getMessage());
        } finally {
            db.endTransaction();
            db.close();
        }
        return resp;
    }

}
