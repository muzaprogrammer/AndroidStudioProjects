package com.example.usodefragmentsparte2;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentDetalle extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detalle, container, false);
    }
    public void mostrarDetalle(int pos) {
        TypedArray fotos; int[] banderas;	String[] paises,capitales;
        Resources res = this.getResources();fotos = res.obtainTypedArray(R.array.banderas);
        banderas = new int[fotos.length()];
        for (int i = 0; i < banderas.length; i++) {
            banderas[i] = fotos.getResourceId(i, 0);
        }
        // Llenamos los arreglos con las cadenas de paises y capitales
        paises = res.getStringArray(R.array.paises);
        capitales = res.getStringArray(R.array.capitales_paises);
        // Establecemos las referencias a los controles que se visualizarón con los datos
        ImageView imagen =  getView().findViewById(R.id.imgBandera);
        TextView tvpais =  getView().findViewById(R.id.tvPais);
        TextView tvcapital =  getView().findViewById(R.id.tvCapital);
        imagen.setImageResource(banderas[pos]);
        tvpais.setText(paises[pos]);
        tvcapital.setText(capitales[pos]);
    }
}