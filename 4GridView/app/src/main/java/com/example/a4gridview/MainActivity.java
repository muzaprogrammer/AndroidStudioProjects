package com.example.a4gridview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static Playa[] ITEMS = {
            new Playa("Acajutla", R.drawable.acajutla),
            new Playa("Bahía de Jiquilisco", R.drawable.bahia_jiquilisco),
            new Playa("Barra de Santiago", R.drawable.barra_de_santiago),
            new Playa("Costa del Sol", R.drawable.costa_del_sol),
            new Playa("Playa Dorada", R.drawable.dorada),
            new Playa("El Cuco", R.drawable.el_cuco),
            new Playa("El Majahual", R.drawable.el_majahual),
            new Playa("El Tunco", R.drawable.el_tunco),
            new Playa("El Zonte", R.drawable.el_zonte),
            new Playa("Las Hojas", R.drawable.las_hojas),
            new Playa("Los cóbanos", R.drawable.los_cobanos),
            new Playa("Salinitas", R.drawable.salinitas),
            new Playa("San Blas", R.drawable.san_blas),
            new Playa("San Diego", R.drawable.san_diego),
            new Playa("El Sunzal", R.drawable.sunzal)
    };

    private GridView gridView;
    private AdaptadorDePlayas adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = findViewById(R.id.grid);
        adaptador = new AdaptadorDePlayas(this);
        gridView.setAdapter(adaptador);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Playa item = (Playa) adapterView.getItemAtPosition(position);
        Intent intent = new Intent(this, ActividadDetalle.class);
        intent.putExtra("id", item.getId());
        startActivity(intent);
    }
}