package com.example.aula_26_01_2024;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Selos extends Fragment {

    private EditText inputEuros;
    private TextView resultSelos3TextView;
    private TextView resultSelos5TextView;
    private ImageView gifImageView;

    private View view;

    public Selos() {
        // Construtor vazio necessário para Fragment
    }
    /* ?????? */
    public static Selos newInstance() {
        Selos fragment = new Selos();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    } /* ?????? */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Chama o layout para este fragmento
        view = inflater.inflate(R.layout.fragment_selos, container, false);

        // Inicializa os elementos de interface do utilizador
        inputEuros = view.findViewById(R.id.editTextEuros);
        resultSelos3TextView = view.findViewById(R.id.textViewSelos3Result);
        resultSelos5TextView = view.findViewById(R.id.textViewSelos5Result);
        Button calculateButton = view.findViewById(R.id.buttonCalculate);
        gifImageView = view.findViewById(R.id.imageViewGifPopup); // Adicionado para inicializar o ImageView

        // Adiciona um ouvinte de clique para o botão de cálculo
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calcularSelos();
            }
        });

        return view;
    }

    private void exibirGifComAnimacao() {
        // Torna a ImageView visível
        gifImageView.setVisibility(View.VISIBLE);

        // Configura uma animação de transparência para criar um efeito de aparecimento suave
        AlphaAnimation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(1000); // Tempo de 1 segundo para aparecer
        fadeIn.setFillAfter(true);

        // Configura uma animação de transparência para criar um efeito de desaparecimento suave
        AlphaAnimation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setStartOffset(2000); // Inicia após 2 segundos (após a animação de aparecer)
        fadeOut.setDuration(1000); // Tempo de 1 segundo para desaparecer
        fadeOut.setFillAfter(true);

        // Combina as duas animações em um conjunto
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(fadeIn);
        animationSet.addAnimation(fadeOut);

        // Adiciona um ouvinte para detectar o término da animação
        animationSet.setAnimationListener(new AlphaAnimation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Executa ações necessárias no início da animação
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Executa ações necessárias no término da animação
                gifImageView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Executa ações necessárias ao repetir a animação
            }
        });

        // Inicia a animação
        gifImageView.startAnimation(animationSet);
    }

    private void calcularSelos() {
        // Obtém o texto inserido no campo de entrada
        String eurosText = inputEuros.getText().toString();

        if (!eurosText.isEmpty()) {
            // Converte o texto para um número inteiro
            int euros = Integer.parseInt(eurosText);
            int s3 = 0, s5 = 0, quoc, r;

            if (euros >= 8) {
                // Calcula os selos para euros maiores ou iguais a 8
                quoc = euros / 8;
                r = euros % 8;

                switch (r) {
                    case 0:
                        s3 = quoc;
                        s5 = quoc;
                        break;
                    case 1:
                        s3 = quoc + 2;
                        s5 = quoc - 1;
                        break;
                    case 2:
                        s3 = quoc - 1;
                        s5 = quoc + 1;
                        break;
                    case 3:
                        s3 = quoc + 1;
                        s5 = quoc;
                        break;
                    case 4:
                        s3 = quoc + 3;
                        s5 = quoc - 1;
                        break;
                    case 5:
                        s3 = quoc;
                        s5 = quoc + 1;
                        break;
                    case 6:
                        s3 = quoc + 2;
                        s5 = quoc;
                        break;
                    case 7:
                        s3 = quoc - 1;
                        s5 = quoc + 2;
                        break;
                }
            } else {

                // Calcula os selos para euros menores que 8
                if (euros == 3) {
                    s3 = 1;
                    s5 = 0;
                } else if (euros == 5) {
                    s3 = 0;
                    s5 = 1;
                } else if (euros == 6) {
                    s3 = 2;
                    s5 = 0;
                } else {
                    // Mostra um aviso para quantias inválidas
                    exibirAviso("Quantia Inválida, insira um valor maior ou igual a 3€");
                    return;
                }
            }
            exibirGifComAnimacao();
            // Atualiza os resultados nos TextViews
            resultSelos3TextView.setText(String.valueOf(s3));
            resultSelos5TextView.setText(String.valueOf(s5));
        } else {
            // Mostra um aviso para quantias vazias
            exibirAviso("Quantia Inválida!");
        }
    }

    private void exibirAviso(String mensagem) {
        // Mostra um pop-up de aviso usando AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Aviso")
                .setMessage(mensagem)
                .setPositiveButton("OK", null)
                .show();
    }
}
