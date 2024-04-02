package com.example.aula_26_01_2024;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;

public class Insert_Post extends Fragment {

    private View view;

    public Insert_Post() {
        // Required empty public constructor
    }

    public static Insert_Post newInstance() {
        Insert_Post fragment = new Insert_Post();
        return fragment;
    }

    private static final int CANALFOTO = 3;
    EditText editTitle, editDescription;
    Button btInsert, btCancel;
    ImageView imgPhoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_insert, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTitle = view.findViewById(R.id.edit_posttitle_insert);
        editDescription = view.findViewById(R.id.edit_desc_insert);
        imgPhoto = view.findViewById(R.id.img_foto_insert);
        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itfoto = new Intent(Intent.ACTION_GET_CONTENT);
                itfoto.setType("image/*");
                startActivityForResult(itfoto, CANALFOTO);
            }
        });

        btInsert = view.findViewById(R.id.bt_insert_insert);
        btInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString();
                String desc = editDescription.getText().toString();
                BitmapDrawable drw = (BitmapDrawable) imgPhoto.getDrawable();
                Bitmap bmp = drw.getBitmap();
                Post novo = new Post(title, desc, bmp);
                MyBD myBD = new MyBD(requireContext(), 1);
                myBD.insertPost(novo);
                App.loadList();
                requireActivity().finish();
            }
        });
        btCancel = view.findViewById(R.id.bt_cancel_insert);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CANALFOTO && resultCode == requireActivity().RESULT_OK) {
            Uri uri = data.getData();
            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), uri);
                imgPhoto.setImageBitmap(bmp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
