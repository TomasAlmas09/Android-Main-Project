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

public class Num_Primos extends Fragment {

    private View view;
    private TextView txtprimos;
    private ProgressBar progressBar;
    private Button btprimos;
    private TextView txtpercentagem;


    public Num_Primos() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    /* ?????? */

    public static Num_Primos newInstance() {
        Num_Primos fragment = new Num_Primos();
        return fragment;
    } /* ?????? */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Chama o fragmento
        view = inflater.inflate(R.layout.fragment_numprimos, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btprimos = view.findViewById(R.id.bt_btprimos);
        txtprimos = view.findViewById(R.id.txt_num_primos);
        progressBar = view.findViewById(R.id.progressbar_primos);
        txtpercentagem = view.findViewById(R.id.txt_percentagem);

        btprimos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAsyncTask myAsyncTask = new MyAsyncTask(Num_Primos.this);
                myAsyncTask.execute(100);
            }
        });
    }

    // Corrigido: Movendo declarações de TextView, ProgressBar e Button para dentro do método onViewCreated

    class MyAsyncTask extends AsyncTask<Integer, Integer, String> {
        Num_Primos num_primos;

        public MyAsyncTask(Num_Primos p) {
            this.num_primos = p;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            num_primos.progressBar.setVisibility(View.VISIBLE);
            num_primos.progressBar.setProgress(0);
            num_primos.txtprimos.setText("Início");
            num_primos.btprimos.setEnabled(false);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            num_primos.txtprimos.setText(s);
            num_primos.progressBar.setVisibility(View.INVISIBLE);
            num_primos.btprimos.setEnabled(true);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            num_primos.progressBar.setProgress(values[1]);
            num_primos.txtprimos.setText(String.format("%d",values[0]));
            num_primos.txtpercentagem.setText(String.format("%d%%", values[1]));
        }

        @Override
        protected String doInBackground(Integer... integers) {
            int i, total, percentagem;
            for (i = 0, total = 0; total < integers[0]; i++) {
                if (Biblioteca.EPrimo(i)) {
                    total++;
                    percentagem = (total * 100) / integers[0];
                    publishProgress(i, percentagem);
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            return "Fim";
        }
    }

}
