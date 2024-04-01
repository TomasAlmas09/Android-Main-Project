package com.example.aula_26_01_2024;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Calculadora extends Fragment {

    private TextView calc;
    private View view;

    public Calculadora() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_calculadora, container, false);
        calc = view.findViewById(R.id.calc);

        // Números
        setNumberButtonClickHandler(R.id.button1, "1");
        setNumberButtonClickHandler(R.id.button2, "2");
        setNumberButtonClickHandler(R.id.button3, "3");
        setNumberButtonClickHandler(R.id.button4, "4");
        setNumberButtonClickHandler(R.id.button5, "5");
        setNumberButtonClickHandler(R.id.button6, "6");
        setNumberButtonClickHandler(R.id.button7, "7");
        setNumberButtonClickHandler(R.id.button8, "8");
        setNumberButtonClickHandler(R.id.button9, "9");
        setNumberButtonClickHandler(R.id.button0, "0");

        // Operadores
        setOperatorButtonClickHandler(R.id.addButton, "+");
        setOperatorButtonClickHandler(R.id.minorButton, "-");
        setOperatorButtonClickHandler(R.id.multiButton, "*");
        setOperatorButtonClickHandler(R.id.diveButton, "/");

        // Ponto
        setPointButtonClickHandler(R.id.pointButton, ".");

        // Igual
        setBtnIgualClickHandler();

        // Limpar
        setClearCalcClickHandler();

        // Desfazer
        setUndoCalcClickHandler();

        return view;
    }

    private void undoLastCharacter() {
        // Obtém o texto atual da TextView
        String currentText = calc.getText().toString();

        // Verifica se há pelo menos um caractere para desfazer
        if (currentText.length() > 0) {
            // Remove o último caractere do texto
            String newText = currentText.substring(0, currentText.length() - 1);

            // Atualiza a TextView com o novo texto
            calc.setText(newText);
        }
    }

    private void setUndoCalcClickHandler() {
        Button undoButton = view.findViewById(R.id.undoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                undoLastCharacter();
            }
        });
    }

    private void setClearCalcClickHandler() {
        Button clearButton = view.findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearCalc();
            }
        });
    }

    private void clearCalc() {
        // Limpa o conteúdo da TextView
        calc.setText("");
    }

    // Ao clicar nos botões adicionar a textview
    private void setNumberButtonClickHandler(int buttonId, final String number) {
        Button button = view.findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Assume o valor da textview + o do botão
                appendToCalc(number);
            }
        });
    }

    private void setOperatorButtonClickHandler(int buttonId, final String operator) {
        Button button = view.findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToCalc(operator);
            }
        });
    }

    private void setPointButtonClickHandler(int buttonId, final String point) {
        Button button = view.findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verifica se já existe um ponto na expressão
                String currentText = calc.getText().toString();
                if (!currentText.contains(",")) {
                    appendToCalc(point);
                }
            }
        });
    }


    public static Calculadora newInstance() {
        Calculadora fragment = new Calculadora();
        return fragment;
    }

    private void setBtnIgualClickHandler() {
        Button BtnIgual = view.findViewById(R.id.BtnIgual);
        BtnIgual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Divida a expressão em partes usando regex para encontrar operadores
                String expressao = calc.getText().toString();
                String[] partes = expressao.split("((?<=\\+)|(?=\\+)|(?<=-)|(?=-)|(?<=\\*)|(?=\\*)|(?<=/)|(?=/))");

                // Só executa caso a função tiver no mínimo 3 partes
                if (partes.length >= 3) {
                    double resultado = Double.parseDouble(partes[0]);

                    for (int i = 1; i < partes.length; i += 2) {
                        String operator = partes[i];
                        double num = Double.parseDouble(partes[i + 1]);

                        switch (operator) {
                            case "+":
                                resultado += num;
                                break;
                            case "-":
                                resultado -= num;
                                break;
                            case "*":
                                resultado *= num;
                                break;
                            case "/":
                                resultado /= num;
                                break;
                        }
                    }
                    // Mostra o resultado fora do loop para que seja exibido apenas uma vez
                    calc.setText(String.valueOf(resultado));
                } else {
                    exibirAviso();
                }
            }
        });
    }

    private void appendToCalc(String value) {
        // Recebe o texto da TextView e adiciona o valor da string recebido
        calc.append(value);
    }

    private void exibirAviso() {
        // Mostra um pop-up de aviso usando AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder.setTitle("SystemError")
                .setPositiveButton("OK", null)
                .show();
    }


}
