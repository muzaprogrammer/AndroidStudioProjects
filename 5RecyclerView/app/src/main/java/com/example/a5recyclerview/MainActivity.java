package com.example.a5recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager lmanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.reciclador);
        List items = new ArrayList();
        items.add(new Carro(R.drawable.audinegro,"Audi Negro",5500));
        items.add(new Carro(R.drawable.camaronegro,"Camaro Negro",6000));
        items.add(new Carro(R.drawable.ferrariamarillo,"Ferrari Amarillo",2000));
        items.add(new Carro(R.drawable.fordmustang,"Ford Mustang",10000));
        items.add(new Carro(R.drawable.jaguar,"Jaguar",4200));
        items.add(new Carro(R.drawable.audinegro,"Audi Negro",5500));
        items.add(new Carro(R.drawable.camaronegro,"Camaro Negro",6000));
        items.add(new Carro(R.drawable.ferrariamarillo,"Ferrari Amarillo",2000));
        items.add(new Carro(R.drawable.fordmustang,"Ford Mustang",10000));
        items.add(new Carro(R.drawable.jaguar,"Jaguar",4200));
        items.add(new Carro(R.drawable.audinegro,"Audi Negro",5500));
        items.add(new Carro(R.drawable.camaronegro,"Camaro Negro",6000));
        items.add(new Carro(R.drawable.ferrariamarillo,"Ferrari Amarillo",2000));
        items.add(new Carro(R.drawable.fordmustang,"Ford Mustang",10000));
        items.add(new Carro(R.drawable.jaguar,"Jaguar",4200));
        lmanager= new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(lmanager);
        adapter = new CarroAdapter(items,this);
        recyclerView.setAdapter(adapter);
    }
}