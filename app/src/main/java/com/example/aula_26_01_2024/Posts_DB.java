package com.example.aula_26_01_2024;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Posts_DB extends Fragment {

    private View view;

    public Posts_DB() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    public static Posts_DB newInstance() {
        Posts_DB fragment = new Posts_DB();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Chama o fragmento
        view = inflater.inflate(R.layout.fragment_posts, container, false);
        return view;
    }

}
