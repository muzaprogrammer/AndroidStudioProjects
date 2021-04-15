package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    String[] paisesCA = {"El Salvador","Guatemala","Nicaragua",
            "Costa Rica","Panamá","Belize"};
    String[] paisesSA = {"Colombia","Argentina","Chile",
            "Bolivia","Venezuela","Paraguay"};
    ListView l1,l2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        l1 = findViewById(R.id.lstCA);
        l2 = findViewById(R.id.lstSA);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,paisesCA);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,paisesSA);
        l1.setAdapter(adapter1);
        l2.setAdapter(adapter2);
        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String pais = paisesCA[position];
                Toast.makeText(getApplicationContext(),pais,Toast.LENGTH_LONG).show();
            }

        });
        l2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String pais = paisesSA[position];
                Toast.makeText(getApplicationContext(),pais,
                        Toast.LENGTH_LONG).show();
            }
        });
        /*
        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String pais = paisesCA[i];
                Toast.makeText(getApplicationContext(),pais,
                        Toast.LENGTH_LONG).show();
            }
        });

        l2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String pais = paisesSA[i];
                Toast.makeText(getApplicationContext(),pais,
                        Toast.LENGTH_LONG).show();
            }
        });
        */
        /*ListView lista = getListView();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,paises);
        //lista.setTextFilterEnabled(true);
        setListAdapter(adapter);
         */
    }

    /*@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String pais = paises[position];
        Toast.makeText(this,pais, Toast.LENGTH_LONG).show();
    }
     */
}