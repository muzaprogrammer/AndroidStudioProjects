package com.example.usodefragmentsparte2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;


public class DetalleActivity extends FragmentActivity {
    public static final String EXTRA_POS = "POSICION";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        // Establecemos una referencia al fragment de detalle
        FragmentDetalle detalle =
                (FragmentDetalle) getSupportFragmentManager().findFragmentById(R.id.FrgDetalle);
        // Invocamos al método del fragment encargado de mostrar los datos
        // Y de ésta forma reutilizamos el código que está en mostrarDetalle()
        detalle.mostrarDetalle(getIntent().getIntExtra(EXTRA_POS, 0));
    }
}
