package com.example.firstapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomArrayAdapter  extends ArrayAdapter<String> {

    Activity context;
    String[] paises;
    String[] capitales;
    Integer[] banderas;


    public CustomArrayAdapter(@NonNull Activity context, String[] paises,String[] capitales,Integer[] banderas ) {
        super(context, R.layout.layout_item_lista_personalizada, paises);

        this.context = context;
        this.paises = paises;
        this.capitales = capitales;
        this.banderas = banderas;

    }

    // Método ejecutado automáticamente por el adaptador
    // según la cantidad de elementos que tenga el arreglo paises
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.layout_item_lista_personalizada, null );

        TextView tvPais = rowView.findViewById(R.id.txtPais);
        TextView tvCapital = rowView.findViewById(R.id.txtCapital);
        ImageView imgBandera = rowView.findViewById(R.id.imgBandera);

        tvPais.setText(paises[position]);
        tvCapital.setText(capitales[position]);
        imgBandera.setImageResource(banderas[position]);

        return rowView;

    }
}
