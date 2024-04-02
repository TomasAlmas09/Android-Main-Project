package com.example.aula_26_01_2024;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

public class Insert_Post extends AppCompatActivity {
    private static final int CANALFOTO = 3;
    EditText editTitle,editDesc;
    Button btinsert, btcancel;

    ImageView imgfoto;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CANALFOTO && resultCode==RESULT_OK){
            Uri uri = Uri.parse(data.getData().toString());
            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                imgfoto.setImageBitmap(bmp);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        editTitle =findViewById(R.id.edit_posttitle_insert);
        editDesc=findViewById(R.id.edit_desc_insert);
        imgfoto = findViewById(R.id.img_foto_insert);
        imgfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itfoto = new Intent(Intent.ACTION_GET_CONTENT);
                itfoto.setType("image/*");
                startActivityForResult(itfoto,CANALFOTO);
            }
        });

        btinsert= findViewById(R.id.bt_insert_insert);
        btinsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posttitle = Integer.parseInt(editTitle.getText().toString());
                String desc = editDesc.getText().toString();
                BitmapDrawable drw =(BitmapDrawable) imgfoto.getDrawable();
                Bitmap bmp = drw.getBitmap();
                Post novo = new Post(posttitle,desc,bmp);
                MyBD myBD = new MyBD(Insert_Post.this,1);
                myBD.insertPost(novo);
                App.loadList();
                finish();
            }
        });
        btcancel =findViewById(R.id.bt_cancel_insert);
        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
