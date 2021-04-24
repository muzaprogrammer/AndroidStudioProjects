package com.example.clinicamisalud;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Buscarpaciente extends AppCompatActivity {
    ListView listPaciente;
    ImageButton btnBuscar;
    EditText edtPacient;
    String paciente,namePaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscarpaciente);
        btnBuscar = findViewById(R.id.btnBuscar);
        listPaciente = findViewById(R.id.listPaciente);
        edtPacient = findViewById(R.id.edtPacient);
        Bundle bundle = getIntent().getExtras();
     String usuario = bundle.getString("email");
      String contraseña= bundle.getString("pass");

        String consulta =("https://apiapp.misalud.com.sv/citas/buscar_pacientes?user="+usuario.toString()+"&pass="+contraseña.toString()+"&text=");
        EnviarRecibirDatos(consulta);
        //modificando 07/04/2021
        btnBuscar .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paciente= edtPacient.getText().toString().replaceAll(" ","%20");
                String consulta =("https://apiapp.misalud.com.sv/citas/buscar_pacientes?user="+usuario.toString()+"&pass="+contraseña.toString()+"&text="+paciente);
                EnviarRecibirDatos(consulta);

            }
        });

        // agregar el evento al listview
        listPaciente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int i, long l) {

                Intent intent = new Intent(Buscarpaciente.this, Cita.class);
                namePaciente =String.valueOf(adapterView.getAdapter().getItem(i));
                intent.putExtra("email", usuario);
                intent.putExtra("pass", contraseña);
                intent.putExtra("paciente", namePaciente);
                startActivity(intent);
            }
        });

    }


    //modificando 07/04/2021
    public void EnviarRecibirDatos(String URL){
        Log.i("url consulta api: ",""+URL);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
           String response1 = response.replace("pacientes","");
           String response2 = response1.replace(":[{",",");
           String response3 = response2.replace("},{",",");
           String response4 = response3.replace(":",",");
           String response5 = response4.replace("{","[");
           String response6 = response5.replace("}]}","]");
                String response7 = response6.replace("Ã³","ó");
                String response8 = response7.replace("Ã¡","á");
                String response9 = response8.replace("Ã©","é");
                String response10 = response9.replace("Ã±","í");
                String response11 = response10.replace("Ãº","ú");
                String response12 = response11.replace("Ã","í");




                if (response12.length()>0){
                    try {
                        JSONArray ja =new JSONArray(response12);
                        Log.i("sizejson",""+ja.length());
                        CargarListView(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    public void CargarListView(JSONArray ja){

        ArrayList<String> lista = new ArrayList<>();

        for(int i=0;i<ja.length();i+=4){
            try {

                lista.add(ja.getString(i+4));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        listPaciente.setAdapter(adaptador);

    }

}