package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class ListaPersonalizada extends ListActivity {

    String[] paises = {"El Salvador","Guatemala","Honduras","Nicaragua",
            "Costa Rica","Panamá","Colombia","Perú","Brasil","Chile",
            "Argentina","Bolivia","Uruguay"};

    String[] capitales = {"San Salvador","Guatemala","Tegucigalpa","Managua",
            "San José","Panamá","Bogota","Lima","Brasilia","Santiago de Chile",
            "Buenos Aires","Sucre","Montevideo"};

    Integer[] imgIDs = {R.drawable.elsalvador,R.drawable.guatemala,
            R.drawable.honduras,R.drawable.nicaragua,  R.drawable.costarica,
            R.drawable.panama,R.drawable.colombia,R.drawable.peru,R.drawable.brazil,
            R.drawable.chile,R.drawable.argentina,R.drawable.bolivia,R.drawable.uruguay};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CustomArrayAdapter adapter = new CustomArrayAdapter(this,
                paises,capitales,imgIDs);

        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        /*
        Intent i = new Intent(this,Pais_Activity.class);
        i.putExtra("img",imgIDs[position]);
        i.putExtra("pais",paises[position]);
        i.putExtra("capital",capitales[position]);

        startActivity(i);
        */
    }
}