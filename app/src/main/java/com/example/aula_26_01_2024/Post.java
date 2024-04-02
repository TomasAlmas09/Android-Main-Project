package com.example.aula_26_01_2024;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;

public class Post implements Parcelable {
    //region campos
    private  String tittle;
    private String  desc;

    private  byte[] foto;
    // endregion

    //region Getters & Setters

    protected Post(Parcel in) {
        tittle = in.readString();
        desc = in.readString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            foto = in.readBlob();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tittle);
        dest.writeString(desc);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            dest.writeBlob(foto);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle){
        this.tittle = tittle;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    //endregion


    @NonNull
    @Override
    public String toString() {
        return String.format("%d %s", tittle,desc);
    }

    public Post(int postId,String tittle, byte[] foto, String desc) {
        this.tittle = tittle;
        this.desc = desc;
        this.foto = foto;
    }
    public Post(String tittle, String desc,Bitmap bmp) {
        this.tittle = tittle;
        this.desc = desc;
        this.foto = bitmaptoarray(bmp);
    }

    public  static byte[] bitmaptoarray(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,stream);
        return stream.toByteArray();
    }

    public  static  Bitmap arraytobitmap(byte[] foto){
        return BitmapFactory.decodeByteArray(foto,0,foto.length);
    }
}
