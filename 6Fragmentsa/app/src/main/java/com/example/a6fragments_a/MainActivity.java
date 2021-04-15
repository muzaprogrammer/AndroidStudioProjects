package com.example.a6fragments_a;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
public class MainActivity extends AppCompatActivity {
    private Button btnAgregarFragment ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAgregarFragment = findViewById(R.id.btnAgregarFragment);
        btnAgregarFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Paso 1: Obtener la instancia del administrador de fragmentos
                FragmentManager fragmentManager = getSupportFragmentManager();
                //Paso 2: Crear una nueva transacción
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                //Paso 3: Crear un nuevo fragmento y añadirlo/reemplazarlo
                ParImparFragment fragment = new ParImparFragment();
                transaction.replace(R.id.contenedor,fragment);
                //Paso 4: Confirmar el cambio
                transaction.commit();
            }
        });
    }
}