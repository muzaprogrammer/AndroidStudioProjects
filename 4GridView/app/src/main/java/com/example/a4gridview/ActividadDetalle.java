package com.example.a4gridview;


import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ActividadDetalle extends AppCompatActivity {
    private Playa playa;
    private ImageView imagenExtendida;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_detalle);
        int id = getIntent().getIntExtra("id", 0);
        playa = Playa.getItem(id);
        imagenExtendida = findViewById(R.id.imagen_extendida);
        setTitle(playa.getNombre());
        cargarImagenExtendida();
    }

    private void cargarImagenExtendida() {
        Glide.with(imagenExtendida.getContext())
                .load(playa.getIdImagen())
                .into(imagenExtendida);
    }
}
