package com.example.clinicamisalud;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Menu extends AppCompatActivity {
    ImageView ImgCita, ImgConsulta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ImgCita = findViewById(R.id.ImgCita);
        ImgCita.setImageResource(R.drawable.procesos);
        // obteniendo el usuario y contraeña
        Bundle bundle = getIntent().getExtras();
        String usuario = bundle.getString("email");
        String contraseña= bundle.getString("pass");

        ImgCita.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Buscarpaciente.class);
               // Bundle bundle =new Bundle();
                intent.putExtra("email", usuario);
                intent.putExtra("pass", contraseña);
                startActivity(intent);
            }
        });

        ImgConsulta = findViewById(R.id.ImgConsulta);
        ImgConsulta.setImageResource(R.drawable.totalproceso);
        ImgConsulta.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Agenda.class);
                intent.putExtra("email", usuario);
                intent.putExtra("pass", contraseña);
                startActivity(intent);
            }
        });
    }
}