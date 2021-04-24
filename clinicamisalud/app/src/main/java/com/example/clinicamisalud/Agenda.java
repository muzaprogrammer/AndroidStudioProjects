package com.example.clinicamisalud;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.Inflater;

public class Agenda extends AppCompatActivity {
/////////////////////////// fecha
    EditText t1;
    private int mYearIni, mMonthIni, mDayIni, sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID = 0;
    Calendar C = Calendar.getInstance();
///////////////////////////////////////
Spinner spSucursal,spEspecialidad,spDoctor;
    Button btnAgenda;
    ListView Listado;
    TextView mostrarFecha;
    int idMedico,idSucursal,idEspecialidad,idEspecial,idMedic,idcitaAgendada,idCitaR;
    String fecha,usuario,contraseña,nombreEsp,nombreMedico,expediente0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        //obtener usuario y pass
        Bundle bundle = getIntent().getExtras();
       usuario = bundle.getString("email");
       contraseña= bundle.getString("pass");
        spSucursal=findViewById(R.id.spSucursal2);
        spEspecialidad=findViewById(R.id.spEspecialidad2);
        spDoctor=findViewById(R.id.spDoctor2);
        btnAgenda=findViewById(R.id.btnAgenda);
        Listado=findViewById(R.id.Listado);
        mostrarFecha=findViewById(R.id.mostrarFecha);
        //fecha
        sMonthIni = C.get(Calendar.MONTH);
        sDayIni = C.get(Calendar.DAY_OF_MONTH);
        sYearIni = C.get(Calendar.YEAR);
        t1 = (EditText) findViewById(R.id.editText3);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_ID);
                fecha = t1.getText().toString();
            }
        });  // fin fecha

        //consulta para obtener las sucursales.
        String sucursales =("https://apiapp.misalud.com.sv/citas/sucursales?user="+usuario+"&pass="+contraseña);
        ObtenerSucursales(sucursales);

        //utilizamos el evento de la sucursal para llenar el siguiente spinner de especialidad
        spSucursal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //   Toast.makeText(getApplicationContext(), String.valueOf(parent.getAdapter().getItem(position)), Toast.LENGTH_SHORT).show();
                String sucursal = spSucursal.getSelectedItem().toString();
                idSucursal = idSucursales(sucursal);
                String especialidad =("https://apiapp.misalud.com.sv/citas/especialidades?user="+usuario+"&pass="+contraseña+"&idsucursal="+idSucursal);
               ObtenerEspecialidad(especialidad);
            }

            public void onNothingSelected(AdapterView<?> parent)
            { Toast.makeText(getApplicationContext(), "Posicion Inicial", Toast.LENGTH_SHORT).show(); }
        });

    //se utiliza el evento de la especialidad para llenar el siguiente spinner de doctor
        spEspecialidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

                nombreEsp=String.valueOf(parent.getAdapter().getItem(position));
                String especialidad =("https://apiapp.misalud.com.sv/citas/especialidades?user="+usuario+"&pass="+contraseña+"&idsucursal="+idSucursal);
                ObtenerIdEspecialidad(especialidad);

            }

            public void onNothingSelected(AdapterView<?> parent)
            {
                Toast.makeText(getApplicationContext(), "Posicion Inicial", Toast.LENGTH_SHORT).show();
            }

        });

        //utilizamos el evento de doctor para llenar el siguiente spinner de Procedimiento
        spDoctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                nombreMedico=String.valueOf(parent.getAdapter().getItem(position));
                String medicos =("https://apiapp.misalud.com.sv/citas/medicos?user="+usuario+"&pass="+contraseña+"&idsucursal="+idSucursal+"&idespecialidad="+idEspecialidad);
                ObtenerIdDoctor(medicos);

            }

            public void onNothingSelected(AdapterView<?> parent)
            {
                Toast.makeText(getApplicationContext(), "Posicion Inicial", Toast.LENGTH_SHORT).show();
            }

        });

        btnAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fecha = t1.getText().toString();
                mostrarFecha.setText(fecha);
                if(spDoctor !=null && t1.getText().toString().trim().length() > 0 && fecha.toString().trim().length() > 0 && mostrarFecha.getText().toString().trim().length() > 0) {
                    String agenda = ("https://apiapp.misalud.com.sv/citas/ver_citas?user=" + usuario + "&pass=" + contraseña + "&idestado=" + "&idsucursal=" + idSucursal + "&idespecialidad=" + idEspecialidad + "&idmedico=" + idMedico + "&idreservacita=");
                    ObtenerAgenda(agenda);
                }else{
                    Toast.makeText(getApplicationContext(), "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
                }

            }
        });



        Listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int i, long l) {

                expediente0 =String.valueOf(adapterView.getAdapter().getItem(i));
                String agenda = ("https://apiapp.misalud.com.sv/citas/ver_citas?user=" + usuario + "&pass=" + contraseña + "&idestado=" + "&idsucursal=" + idSucursal + "&idespecialidad=" + idEspecialidad + "&idmedico=" + idMedico + "&idreservacita=");
                ObtenerIdCitas(agenda);
            }
        });


    }

            //metodo para agregar la fecha al textview
    private void colocar_fecha() {
       // t1.setText( mDayIni+ "-" + (mMonthIni + 1)+ "-" + mYearIni+"");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String date_str = mDayIni+ "-" + (mMonthIni + 1)+ "-" + mYearIni; // String obtenido de DatePicker
        try {
            Date date_date;
            date_date = sdf.parse(date_str);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String date_str2 = formatter.format(date_date);
            t1.setText(date_str2);

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    //
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYearIni = year;
                    mMonthIni = monthOfYear;
                    mDayIni = dayOfMonth;
                    colocar_fecha();

                }

            };


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID:
                return new DatePickerDialog(this, mDateSetListener, sYearIni, sMonthIni, sDayIni);


        }


        return null;
    }
// finaliza metodos necesarios para fecha

    //Agregando metodos para la sucursal
    public void ObtenerSucursales(String URL){
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
                        EnviarSucursales(ja);
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

    public void EnviarSucursales(JSONArray ja){

        ArrayList<String> lista = new ArrayList<>();
        for(int i=0;i<ja.length();i+=4){
            try {

                lista.add(ja.getString(i+4));
            } catch (JSONException e) {

                e.printStackTrace();
            }

        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        spSucursal.setAdapter(adaptador);
    }

    //metodo para asignar el id a sucursales y enviarlo a especialidades.
    public int idSucursales(String sucursales)
    {
        int id =0;
        if (sucursales.equals("BEETHOVEN")) { id=1; }
        if (sucursales.equals("METROSUR")) { id=2; }
        if (sucursales.equals("VIRTUAL")) { id=3; }
        return id;
    }


    //Agregando metodos para la Especialidad
    public void ObtenerEspecialidad(String URL){
        Log.i("url consulta api: ",""+URL);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String response1 = response.replace("especialidades","");
                String response2 = response1.replace(":[{",",");
                String response3 = response2.replace("},{",",");
                String response4 = response3.replace(":",",");
                String response5 = response4.replace("{","[");
                String response6 = response5.replace("}]}","]");
                String response7 = response6.replace("iÃ³","ió");

                if (response7.length()>0){
                    try {
                        JSONArray je =new JSONArray(response7);
                        Log.i("sizejson",""+je.length());
                        CargarEspecialidad(je);
                        //  CargarEspecialidadid(je);
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

    public void CargarEspecialidad(JSONArray je){

        ArrayList<String> lista = new ArrayList<>();


        for(int i=0;i<je.length();i+=4){
            try {
                lista.add(je.getString(i+4));
            } catch (JSONException e) {

                e.printStackTrace();
            }

        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        spEspecialidad.setAdapter(adaptador);

    }


//Agregando metodos para la Medico
    public void ObtenerMedicos(String URL){
        Log.i("url consulta api: ",""+URL);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //modificando
                String response1 = response.replace("medicos","");
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
                        JSONArray je =new JSONArray(response12);
                        Log.i("sizejson",""+je.length());
                        CargarMedicos(je);

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

    public void CargarMedicos(JSONArray je){

        ArrayList<String> lista = new ArrayList<>();

        for(int i=0;i<je.length();i+=4){
            try {

                lista.add(je.getString(i+4));
            } catch (JSONException e) {

                e.printStackTrace();
            }

        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        spDoctor.setAdapter(adaptador);
    }

    // Metodos para obtener los id necesarios
    // obtener id especialidad
    public void ObtenerIdEspecialidad(String URL){
        Log.i("url consulta api: ",""+URL);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //modificando
                String response1 = response.replace("especialidades","");
                String response2 = response1.replace(":[{",",");
                String response3 = response2.replace("},{",",");
                String response4 = response3.replace(":",",");
                String response5 = response4.replace("{","[");
                String response6 = response5.replace("}]}","]");
                String response7 = response6.replace("iÃ³","ió");

                if (response7.length()>0){
                    try {
                        JSONArray idEsp =new JSONArray(response7);
                        Log.i("sizejson",""+idEsp.length());
                        IdEspecialidad(idEsp);
                        idEspecialidad=idEspecial;
                        String medicos =("https://apiapp.misalud.com.sv/citas/medicos?user="+usuario+"&pass="+contraseña+"&idsucursal="+idSucursal+"&idespecialidad="+idEspecialidad);
                        ObtenerMedicos(medicos);

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

    public void IdEspecialidad(JSONArray je){

        ArrayList<String> lista = new ArrayList<>();


        for(int i=0;i<je.length();i+=4){
            try {
                if(nombreEsp.equals(je.getString(i+4))){
                    String idEspe =je.getString(i+2);
                    idEspecial =  Integer.parseInt(idEspe);
                }

                lista.add(je.getInt(i+2)+" "+je.getString(i+4));
            } catch (JSONException e) {

                e.printStackTrace();
            }

        }
    }

    // obtener id del Medico
    public void ObtenerIdDoctor(String URL){
        Log.i("url consulta api: ",""+URL);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //modificando
                String response1 = response.replace("medicos","");
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
                        JSONArray idDoctor =new JSONArray(response12);
                        Log.i("sizejson",""+idDoctor.length());
                        IdDoctor(idDoctor);
                        idMedico=idMedic;
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

    public void IdDoctor(JSONArray je){

        ArrayList<String> lista = new ArrayList<>();


        for(int i=0;i<je.length();i+=4){
            try {
                if(nombreMedico.equals(je.getString(i+4))){
                    String idMedi =je.getString(i+2);
                    idMedic =  Integer.parseInt(idMedi);
                }

                lista.add(je.getInt(i+2)+" "+je.getString(i+4));
            } catch (JSONException e) {

                e.printStackTrace();
            }

        }
    }

    //Agenda
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
                        Log.i("sizejson",""+ag.length());
                        ListaAgenda(ag);

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
                String Hora1 =ja.getString(i+6);
                String HoraInicio=Hora1.replace(",",":");
                String Hora2 =ja.getString(i+8);
                String HoraFin=Hora2.replace(",",":");

                if(fecha.equals(ja.getString(i+4))){

                    lista.add("Hora inicio: "+HoraInicio+"      Hora fin: "+HoraFin+" \nProcedimiento:  "+ja.getString(i+20)+"\nPaciente:  "+ja.getString(i+28)+" \nEstado:  "+ja.getString(i+40)+" \n           ");

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        Listado.setAdapter(adaptador);
        Listado.setEmptyView(findViewById(R.id.emptyElement));

    }

    //Obtener Id de la cita
    public void ObtenerIdCitas(String URL){
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
                        Log.i("sizejson",""+ag.length());
                        IdCitasReg(ag);
                        idcitaAgendada=idCitaR;
                        Intent intent = new Intent(Agenda.this, Expediente.class);
                        //  Toast.makeText(getApplicationContext(),expediente0, Toast.LENGTH_SHORT).show();
                        intent.putExtra("email", usuario);
                        intent.putExtra("pass", contraseña);
                        intent.putExtra("idreserva", idcitaAgendada);
                        startActivity(intent);
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

    public void IdCitasReg(JSONArray ja){

        ArrayList<String> lista = new ArrayList<>();

        for(int i=0;i<ja.length();i+=48){
            try {
                String Hora1 =ja.getString(i+6);
                String HoraInicio=Hora1.replace(",",":");
                String Hora2 =ja.getString(i+8);
                String HoraFin=Hora2.replace(",",":");

                String idcit=ja.getString(i+6);

String arreglosObtenidos="Hora inicio: "+HoraInicio+"      Hora fin: "+HoraFin+" \nProcedimiento:  "+ja.getString(i+20)+"\nPaciente:  "+ja.getString(i+28)+" \nEstado:  "+ja.getString(i+40)+" \n           ";
                if(expediente0.equals(arreglosObtenidos)){
                    String idCtas =ja.getString(i+2);
                    idCitaR =  Integer.parseInt(idCtas);
                  //  Toast.makeText(Agenda.this, ""+idCitaR,Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



}



