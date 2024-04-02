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
    /* public static final String TB_USERS = "tbUsers";
    public static final String USER_ID = "idUser";
    public static final String NICKNAME = "nickname";
    public static final String EMAIL = "email"; */

    public static final String TB_POSTS = "tbPosts";
    public static final String FOTO = "foto";
    public static final String POST_ID = "postId";
    public static final String POST_TITLE = "postTitle";
    public static final String DESC = "desc";
    /* public static final String LIKE = "likes"; */

    private static final String TAG = "MyBD";

    public MyBD(@Nullable Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlPosts = "CREATE TABLE " + TB_POSTS + " ( " +
                POST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                POST_TITLE + " CHAR, " +
                FOTO + " BLOB, " +
                "`DESC`" + " CHAR " +
                ");";
        db.execSQL(sqlPosts);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*String sqlUsers = "DROP TABLE IF EXISTS " + TB_USERS + ";";
        db.execSQL(sqlUsers);*/

        String sqlPosts = "DROP TABLE IF EXISTS " + TB_POSTS + ";";
        db.execSQL(sqlPosts);

        onCreate(db);
    }

    public long insertPost(Post add) {
        long resp = 0;
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues cv = new ContentValues();
            cv.put(POST_TITLE, add.getTittle());
            cv.put(DESC, add.getDesc());
            cv.put(FOTO, add.getFoto());
            resp = db.insert(TB_POSTS, null, cv);
            db.setTransactionSuccessful();
        } catch (SQLException erro) {
            Log.i(TAG, erro.getMessage());
        } finally {
            db.endTransaction();
            db.close();
        }
        return resp;
    }
    List<Post> loadList(){
        List<Post> lista = new ArrayList<>();
        SQLiteDatabase db =getReadableDatabase();
        String sql="select * from "+ TB_POSTS +" ;";
        Cursor cur = db.rawQuery(sql,null);
        if(cur.getCount()>0){
            cur.moveToFirst();
            do{
                Post post = new Post(
                        cur.getInt(0),
                        cur.getString(1),
                        cur.getString(2),
                        cur.getBlob(3),
                        cur.getString(4)
                );
                lista.add(post);
            }while (cur.moveToNext());
        }
        return  lista;
    }
}
