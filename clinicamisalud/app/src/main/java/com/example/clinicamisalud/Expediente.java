package com.example.clinicamisalud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Expediente extends AppCompatActivity {
    Spinner spEstados;
    Button btncerrar;
    TextView txtExpediente,txtDoctor,txtPaciente,txtFecha,txtInicioHora,txtFinHora,txtEspecialida,txtProcedim,txtSucurs,txtEstadocita,txtObservacion;
    String usuario,contraseña,vExpediente,vDoctor,vPaciente,vFecha,vInicioHora,vFinHora,vEspecialida,vProcedim,vSucurs,vEstadocita,vObservacion;
    int idReservaCita;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expediente);
        Bundle bundle = getIntent().getExtras();
        idReservaCita = bundle.getInt("idreserva");
        usuario = bundle.getString("email");
        contraseña= bundle.getString("pass");
        spEstados=findViewById(R.id.spEstados);
        txtExpediente=findViewById(R.id.txtExpediente); txtDoctor=findViewById(R.id.txtDoctor); txtPaciente=findViewById(R.id.txtPaciente);
        txtFecha=findViewById(R.id.txtFecha); txtInicioHora=findViewById(R.id.txtInicioHora); txtFinHora=findViewById(R.id.txtFinHora);
        txtEspecialida=findViewById(R.id.txtEspecialida); txtProcedim=findViewById(R.id.txtProcedim); txtSucurs=findViewById(R.id.txtSucurs);
        txtEstadocita=findViewById(R.id.txtEstadocita); txtObservacion=findViewById(R.id.txtObservacion);
        btncerrar= findViewById(R.id.btncerrar);

        //boton cerrar y regresa al menu

        btncerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Expediente.this, "PROCESO CULMINADO",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Expediente.this, Menu.class);
                intent.putExtra("email", usuario);
                intent.putExtra("pass", contraseña);
                startActivity(intent);
            }
        });



    //    txtExpediente.setText(""+idReservaCita);
        String agenda = ("https://apiapp.misalud.com.sv/citas/ver_citas?user=" + usuario + "&pass=" + contraseña + "&idestado=&idsucursal=&idespecialidad=&idmedico=&"+ "&idreservacita="+idReservaCita);
        ObtenerAgenda(agenda);

        String estados =("https://apiapp.misalud.com.sv/citas/estados?user="+usuario+"&pass="+contraseña);
        ObtenerEstados(estados);

    }


    public void ObtenerAgenda(String URL){
        Log.i("url consulta api: ",""+URL);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String response1 = response.replace("citas","");
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
                        JSONArray ag =new JSONArray(response12);
                        ListaAgenda(ag);
                        txtExpediente.setText(""+vExpediente);
                        txtDoctor.setText(""+vDoctor);
                        txtPaciente.setText(""+vPaciente);
                        txtFecha.setText(""+vFecha);
                        txtInicioHora.setText(""+vInicioHora);
                        txtFinHora.setText(""+vFinHora);
                        txtEspecialida.setText(""+vEspecialida);
                        txtProcedim.setText(""+vProcedim);
                        txtSucurs.setText(""+vSucurs);
                        txtEstadocita.setText(""+vEstadocita);
                        txtObservacion.setText(""+vObservacion);


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

    public void ListaAgenda(JSONArray ja){

        ArrayList<String> lista = new ArrayList<>();

        for(int i=0;i<ja.length();i+=48){
            try {
             String Hora1 =ja.getString(i+6); String Hora11=Hora1.replace(",",":"); vInicioHora=Hora11;
             String Hora2 =ja.getString(i+8); String Hora12=Hora2.replace(",",":"); vFinHora=Hora12;
             vExpediente=ja.getString(i+26);
             vDoctor=ja.getString(i+12);
             vPaciente=ja.getString(i+28);
             vFecha=ja.getString(i+4);
             vEspecialida=ja.getString(i+16);
             vProcedim=ja.getString(i+20);
             vSucurs=ja.getString(i+24);
             vEstadocita=ja.getString(i+40);
             vObservacion=ja.getString(i+42);

              lista.add("Hora inicio: "+Hora11+"      Hora fin: "+Hora12);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
       // Listado.setAdapter(adaptador);
     //   Listado.setEmptyView(findViewById(R.id.emptyElement));

    }

    public void ObtenerEstados(String URL){
        Log.i("url consulta api: ",""+URL);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String response1 = response.replace("sucursales","");
                String response2 = response1.replace(":[{",",");
                String response3 = response2.replace("},{",",");
                String response4 = response3.replace(":",",");
                String response5 = response4.replace("{","[");
                String response6 = response5.replace("}]}","]");


                if (response6.length()>0){
                    try {
                        JSONArray ja =new JSONArray(response6);
                        Log.i("sizejson",""+ja.length());
                        Estados(ja);
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

    public void Estados(JSONArray ja){

        ArrayList<String> lista = new ArrayList<>();
        for(int i=0;i<ja.length();i+=4){
            try {

                lista.add(ja.getString(i+4));
            } catch (JSONException e) {

                e.printStackTrace();
            }

        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        spEstados.setAdapter(adaptador);
    }

}