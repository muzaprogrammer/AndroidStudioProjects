package com.example.clinicamisalud;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class Cita extends AppCompatActivity {
    TextView Paciente,edt6;
    Spinner spSucursal,spEspecialidad,spDoctor,spProcedimiento,spHora,spHoraFin;
    EditText idObservaciones;
    int idProcedimiento,idMedico,idSucursal,respnuevaCita,idExpediente,idEspecialidad;
    int idEspecial,idMedic,idProced;
    String fecha,horaInicio,horaFin,observaciones;
    String nombreEsp,usuario,contraseña,nombreMedico,nombreProcedimiento;

    Button btnHorarios,crearCita;

    //iniciamos declarando para utilizar calendario
    private int mYearIni, mMonthIni, mDayIni, sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID = 0;
    Calendar C = Calendar.getInstance();
//termina lo del calendario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cita);
        // Referencias del calendario
        sMonthIni = C.get(Calendar.MONTH);
        sDayIni = C.get(Calendar.DAY_OF_MONTH);
        sYearIni = C.get(Calendar.YEAR);
        edt6 = findViewById(R.id.edt6);
        // fin referencias del calendario


        Bundle bundle = getIntent().getExtras();
        String nombrepaciente = bundle.getString("paciente");
         usuario = bundle.getString("email");
         contraseña= bundle.getString("pass");
        Paciente=findViewById(R.id.paciente);
        String name=nombrepaciente;
        Paciente.setText(name);
        spSucursal=findViewById(R.id.spSucursal);
        spEspecialidad=findViewById(R.id.spEspecialidad);
        spDoctor=findViewById(R.id.spDoctor);
        spProcedimiento=findViewById(R.id.spProcedimiento);
        spHora =findViewById(R.id.spHora);
        btnHorarios = findViewById(R.id.btnHorarios);
        spHoraFin= findViewById(R.id.spHoraFin);
        crearCita= findViewById(R.id.crearCita);
        idObservaciones= findViewById(R.id.idObservaciones);
        String namePaciente=name.replaceAll(" ","%20");
        //consulta para obtener expediente
        String consulta =("https://apiapp.misalud.com.sv/citas/buscar_pacientes?user="+usuario+"&pass="+contraseña+"&text="+namePaciente);
        RecibirExpediente(consulta);
        //consulta para obtener las sucursales.
        String sucursales =("https://apiapp.misalud.com.sv/citas/sucursales?user="+usuario+"&pass="+contraseña);
        RecibirSucursales(sucursales);

        //utilizamos el evento de la sucursal para llenar el siguiente spinner de especialidad
        spSucursal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
             //   Toast.makeText(getApplicationContext(), String.valueOf(parent.getAdapter().getItem(position)), Toast.LENGTH_SHORT).show();
                String sucursal = spSucursal.getSelectedItem().toString();
                idSucursal = idSucursales(sucursal);
                String especialidad =("https://apiapp.misalud.com.sv/citas/especialidades?user="+usuario+"&pass="+contraseña+"&idsucursal="+idSucursal);
                RecibirEspecialidad(especialidad);
            }

            public void onNothingSelected(AdapterView<?> parent)
            { Toast.makeText(getApplicationContext(), "Posicion Inicial", Toast.LENGTH_SHORT).show(); }
        });



        //utilizamos el evento de la especialidad para llenar el siguiente spinner de doctor
        spEspecialidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

                nombreEsp=String.valueOf(parent.getAdapter().getItem(position));
                String especialidad =("https://apiapp.misalud.com.sv/citas/especialidades?user="+usuario+"&pass="+contraseña+"&idsucursal="+idSucursal);
                RecibirIdEspecialidad(especialidad);

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
                 RecibirIdDoctor(medicos);

            }

            public void onNothingSelected(AdapterView<?> parent)
            {
                Toast.makeText(getApplicationContext(), "Posicion Inicial", Toast.LENGTH_SHORT).show();
            }

        });

        //Procedimiento validando metodo onclick
        spProcedimiento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

                nombreProcedimiento=String.valueOf(parent.getAdapter().getItem(position));

                String procedimientos =("https://apiapp.misalud.com.sv/citas/procedimientos?user="+usuario+"&pass="+contraseña+"&idmedico="+idMedico);
                RecibirIdProcedimiento(procedimientos);
            }

            public void onNothingSelected(AdapterView<?> parent)
            {
                Toast.makeText(getApplicationContext(), "Posicion Inicial", Toast.LENGTH_SHORT).show();
            }

        });

        // Accion para mostrar la fecha
        edt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_ID);
                fecha = edt6.getText().toString();
            }
        });
        // finaliza la utilidad del calendario

        //boton para verificar si hay horarios disponibles en una fecha especifica
        btnHorarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fecha = edt6.getText().toString();
                String horaInicio =("https://apiapp.misalud.com.sv/citas/horas_desde?user="+usuario+"&pass="+contraseña+"&idprocedimiento="+idProcedimiento+"&fecha="+fecha+"&idmedico="+idMedico);
                RecibirHoras(horaInicio);
            }
        });


        //seleccionar una hora.
        spHora.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                horaInicio = String.valueOf(parent.getAdapter().getItem(position));
                String horaFin =("https://apiapp.misalud.com.sv/citas/hora_hasta?user="+usuario+"&pass="+contraseña+"&idprocedimiento="+idProcedimiento+"&fecha="+fecha+"&idmedico="+idMedico+"&hora_inicio="+horaInicio);
                RecibirHorasFin(horaFin);
            }

            public void onNothingSelected(AdapterView<?> parent)
            {
                Toast.makeText(getApplicationContext(), "Posicion Inicial", Toast.LENGTH_SHORT).show();
            }

        });


        //Boton Crear cita
        crearCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                observaciones= idObservaciones.getText().toString().replaceAll(" ","%20");
                if(fecha !=null && horaInicio !=null && horaFin !=null && idObservaciones.getText().toString().trim().length() > 0){

                    String crearnuevacita =( "https://apiapp.misalud.com.sv/citas/nueva_cita?user="+usuario+"&pass="+contraseña+
                            "&idexpediente="+idExpediente+"&fecha="+fecha+"&hora_desde="+horaInicio+"&hora_hasta="+horaFin+"&idprocedimiento="+idProcedimiento+
                            "&idmedico="+idMedico+"&idsucursal="+idSucursal+"&observaciones="+observaciones);
                    NuevaCita(crearnuevacita);


                } else {
                    Toast.makeText(Cita.this, "TODOS LOS CAMPOS SON OBLIGATORIOS",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //Agregando metodos para la sucursal
    public void RecibirSucursales(String URL){
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

    public void RecibirEspecialidad(String URL){
        Log.i("url consulta api: ",""+URL);

        //   Toast.makeText(getApplicationContext(), ""+URL, Toast.LENGTH_SHORT).show();
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

    public void RecibirMedicos(String URL){
        Log.i("url consulta api: ",""+URL);

        //   Toast.makeText(getApplicationContext(), ""+URL, Toast.LENGTH_SHORT).show();
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

    // Agregar Procedimientos
    public void RecibirProcedimientos(String URL){
        Log.i("url consulta api: ",""+URL);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String response1 = response.replace("procedimientos","");
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
                        CargarProcedimientos(je);

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

    public void CargarProcedimientos(JSONArray je){

        ArrayList<String> lista = new ArrayList<>();
        for(int i=0;i<je.length();i+=8){
            try {
                lista.add(je.getString(i+8));
            } catch (JSONException e) {

                e.printStackTrace();
            }

        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        spProcedimiento.setAdapter(adaptador);

    }


// agregar metodo para fecha.

    public void RecibirHoras(String URL){
        Log.i("url consulta api: ",""+URL);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //modificando
                String response1 = response.replace("horas_desde","");
                String response2 = response1.replace(":[",",");
                String response5 = response2.replace("{","[");
                String response6 = response5.replace("]}","]");


                if (response6.length()>0){
                    try {
                        JSONArray je =new JSONArray(response6);
                        Log.i("sizejson",""+je.length());
                        CargarHoras(je);

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Cita.this, "No se encontraron horas disponibles",Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);

    }

    public void CargarHoras(JSONArray je){

        ArrayList<String> lista = new ArrayList<>();

        for(int i=0;i<je.length();i+=1){
            try {

                lista.add(je.getString(i+1));
            } catch (JSONException e) {

                e.printStackTrace();
            }

        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        spHora.setAdapter(adaptador);

    }

    //
    public void RecibirHorasFin(String URL){
        Log.i("url consulta api: ",""+URL);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //modificando
                String response1 = response.replace("horas_hasta","");
                String response2 = response1.replace(":",",");
                String response5 = response2.replace("{","[");
                String response6 = response5.replace("}","]");


                if (response6.length()>0){
                    try {
                        JSONArray je =new JSONArray(response6);
                        Log.i("sizejson","Pruebassss"+je.length());
                        CargarHorasFin(je);

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Cita.this, "No se encontraron horas",Toast.LENGTH_SHORT).show();
                horaFin=" ";
            }
        });

        queue.add(stringRequest);

    }

    public void CargarHorasFin(JSONArray je){

        ArrayList<String> lista = new ArrayList<>();

        for(int i=0;i<je.length();i+=1){
            try {
                String fechaF =je.getString(i+1);
                String FechaFin=fechaF.replace(",",":");
                horaFin=FechaFin;
                lista.add(FechaFin);
            } catch (JSONException e) {

                e.printStackTrace();
            }

        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        spHoraFin.setAdapter(adaptador);

    }
    //Obtener el numero de Expediente.
    public void RecibirExpediente(String URL){
        Log.i("url consulta api: ",""+URL);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String response1 = response.replace("pacientes","");
                String response2 = response1.replace(":[{",",");
                String response4 = response2.replace(":",",");
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
                        Log.i("sizejson","Pruebassss"+je.length());
                        CargarExpediente(je);
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

    public void CargarExpediente(JSONArray je){

        ArrayList<String> lista = new ArrayList<>();

        for(int i=0;i<je.length();i+=4){
            try {
                String expediente =je.getString(i+2);
                idExpediente =  Integer.parseInt(expediente);
                lista.add(je.getString(idExpediente));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    // para vereficar lo que retorna nueva cita
    public void NuevaCita(String URL){
        Log.i("url consulta api: ",""+URL);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //modificando
                String response1 = response.replace("nueva_citas","");
                String response2 = response1.replace(":",",");
                String response5 = response2.replace("{","[");
                String response6 = response5.replace("}","]");


                if (response6.length()>0){
                    try {
                        JSONArray je =new JSONArray(response6);
                        Log.i("sizejson","Pruebassss"+je.length());

                        CargarNuevaCita(je);

                        switch (respnuevaCita) {
                            case 1:
                                Toast.makeText(Cita.this, "LA CITA SE CREO CON EXITO ",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Cita.this, Menu.class);
                                intent.putExtra("email", usuario);
                                intent.putExtra("pass", contraseña);
                                startActivity(intent);
                                break;
                            case 2:
                                Toast.makeText(Cita.this, "LA CITA YA EXISTE ",Toast.LENGTH_SHORT).show();
                                break;
                            case 3:
                                Toast.makeText(Cita.this, "EL HORARIO YA ESTA OCUPADO ",Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }

            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Cita.this, "Complete todos lo campos",Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);

    }

    public void CargarNuevaCita(JSONArray je){

        ArrayList<String> lista = new ArrayList<>();

        for(int i=0;i<je.length();i+=2){
            try {
                int resultado=je.getInt(i+1);
                respnuevaCita=resultado;
                //   Toast.makeText(cita.this, "Confirmacion: "+respnuevaCita,Toast.LENGTH_SHORT).show();
                lista.add(je.getString(resultado));
            } catch (JSONException e) {

                e.printStackTrace();
            }

        }


    }

    //Metodo para usar calendario.
    private void colocar_fecha() {
       // edt6.setText(mDayIni + "-" +( mMonthIni + 1) + "-" + mYearIni);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String date_str = mDayIni+ "-" + (mMonthIni + 1)+ "-" + mYearIni;
        try {
            Date date_date;
            date_date = sdf.parse(date_str);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String date_str2 = formatter.format(date_date);
            edt6.setText(date_str2);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

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
    // termina metodo de calendario




    // obtener id especialidad
    public void RecibirIdEspecialidad(String URL){
        Log.i("url consulta api: ",""+URL);

        //   Toast.makeText(getApplicationContext(), ""+URL, Toast.LENGTH_SHORT).show();
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
                        RecibirMedicos(medicos);

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
    public void RecibirIdDoctor(String URL){
        Log.i("url consulta api: ",""+URL);

        //   Toast.makeText(getApplicationContext(), ""+URL, Toast.LENGTH_SHORT).show();
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
                        String procedimientos =("https://apiapp.misalud.com.sv/citas/procedimientos?user="+usuario+"&pass="+contraseña+"&idmedico="+idMedico);
                        RecibirProcedimientos(procedimientos);

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


    // obtener id del Medico
    public void RecibirIdProcedimiento(String URL){
        Log.i("url consulta api: ",""+URL);

        //   Toast.makeText(getApplicationContext(), ""+URL, Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String response1 = response.replace("procedimientos","");
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
                        JSONArray Proc =new JSONArray(response12);
                        Log.i("sizejson",""+Proc.length());
                        IdProcedimiento(Proc);
                        idProcedimiento=idProced;

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

    public void IdProcedimiento(JSONArray je){

        ArrayList<String> lista = new ArrayList<>();


        for(int i=0;i<je.length();i+=8){
            try {
                if(nombreProcedimiento.equals(je.getString(i+8))){
                    String idproc =je.getString(i+2);
                    idProced =  Integer.parseInt(idproc);
                }

                lista.add(je.getInt(i+2)+" "+je.getString(i+8));
            } catch (JSONException e) {

                e.printStackTrace();
            }

        }
    }


}





