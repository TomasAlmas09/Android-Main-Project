package com.example.aula_26_01_2024;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class Posts_DB extends Fragment {
    private Context mContext;
    private View view;
    public static final int CANAL_FOTO = 1;
    RecyclePosts adpt;
    RecyclerView recyclerView;
    FloatingActionButton fab;

    public int pos;
    public static final int CANALINSERT = 2;

    public Posts_DB() {
        // Required empty public constructor
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public static Posts_DB newInstance() {
        Posts_DB fragment = new Posts_DB();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_posts, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab = view.findViewById(R.id.float_insert_main);
        recyclerView = view.findViewById(R.id.recycle_post_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace the current fragment with Insert_Post fragment
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.Insert_Post, Insert_Post.newInstance());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        adpt = new RecyclePosts(mContext, App.post);
        adpt.setOnSacaFotoListener(new IOnSacaFoto() {
            @Override
            public void SacaFoto(int posicao) {
                pos = posicao;
                Intent it = new Intent(Intent.ACTION_GET_CONTENT);
                it.setType("image/*");
                startActivityForResult(it, CANAL_FOTO);
            }
        });
        recyclerView.setAdapter(adpt);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CANALINSERT) {
            getActivity().finish();
            getActivity().overridePendingTransition(0, 0);
            startActivity(getActivity().getIntent());
            getActivity().overridePendingTransition(0, 0);
        }

        if (requestCode == CANAL_FOTO && resultCode == getActivity().RESULT_OK) {
            Uri uri = Uri.parse(data.getData().toString());
            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
                App.post.get(pos).setFoto(Post.bitmaptoarray(bmp));
                adpt.notifyDataSetChanged();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
