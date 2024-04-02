package com.example.aula_26_01_2024;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    public static List<Post> post = new ArrayList<>();
    public  static Context ctx;
    public  static  final String TAG="Xpto";
    @Override
    public void onCreate() {
        super.onCreate();
        ctx=getApplicationContext();
        Log.i(TAG,"App Created");
        loadList();
        for(Post c : post)Log.i(TAG,c.toString());
    }
    public  static  void loadList(){
        MyBD myBD = new MyBD(ctx,1);
        post = myBD.loadList();
    }
}