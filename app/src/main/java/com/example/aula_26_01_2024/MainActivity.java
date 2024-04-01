package com.example.aula_26_01_2024;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar_toolbar_main);
        setSupportActionBar(toolbar);
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        Fragment um = Um.newInstance("", ""); /* ?????? */
        t.add(R.id.fragmentContainerView, um);
        t.addToBackStack(null);
        t.commit();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        ((MenuBuilder) menu).setOptionalIconsVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mnu_calculadora_mainmenu) {

            FragmentTransaction t = getSupportFragmentManager().beginTransaction();

            // Cria uma nova instância do fragmento Calculadora
            Calculadora frgcalc = Calculadora.newInstance();

            // Adiciona o fragmento Calculadora à transação
            t.add(R.id.fragmentContainerView, frgcalc);

            // Adiciona a transação à pilha de fragmentos (para permitir o botão de voltar)
            t.addToBackStack(null);

            // Executa a transação
            t.commit();

            // Exibe um mini Pop-Up (Toast) para informar que a Calculadora foi selecionada
            Toast.makeText(this, "Calculadora", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.mnu_selos_mainmenu) {

            FragmentTransaction t = getSupportFragmentManager().beginTransaction(); /* ?????? */
            Selos frgselos = Selos.newInstance();
            t.add(R.id.fragmentContainerView, frgselos);
            t.addToBackStack(null);
            t.commit();

            Toast.makeText(this, "Selos", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.mnu_numpares_mainmenu) {

            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
            Dois frgDois = Dois.newInstance("", ""); /* ?????? */
            t.add(R.id.fragmentContainerView, frgDois);
            t.addToBackStack(null);
            t.commit();

            Toast.makeText(this, "Pares", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.mnu_numprimos_mainmenu) {

            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
            Num_Primos frgprimos = Num_Primos.newInstance();
            t.add(R.id.fragmentContainerView, frgprimos);
            t.addToBackStack(null);
            t.commit();

            Toast.makeText(this, "Primos", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}