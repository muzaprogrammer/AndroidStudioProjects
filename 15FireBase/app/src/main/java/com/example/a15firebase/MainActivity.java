package com.example.a15firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a15firebase.interfaces.Service;
import com.example.a15firebase.models.Mensaje;
import com.example.a15firebase.models.Notification;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText txtTitle,txtBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtTitle = findViewById(R.id.txtTitle);
        txtBody = findViewById(R.id.txtBody);

    }

    public void enviar(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fcm.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String contentType = "application/json";
        String authorization = "KEY=AAAA9Mz-kvU:APA91bFJbWJUujP3NaRm0LtdSPf_HGfRkSK2HQ1GMGQjNrnx1wPlxMpLZL5N43BR6SofCAcmK_t30jZv1X5VSFAfz2i6QtDYsIQ9XvAXSRPH0T-0vNNZliCw7FKM-RvNdLLhH2WwYW0t";
        String to = "/topics/NOTICIAS";
        //"/topics/NOTICIAS";
        //"eyJhbGciOiJFUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6IjE6Mzk5NDY2OTg0OTUxOmFuZHJvaWQ6YjZjZjk0MDkwMmFiY2MwOGI5ZWFmMiIsImV4cCI6MTYyMTAyMzQxNSwiZmlkIjoiZFBVb1hMUVJTNi1TQ05MR25WVExsZyIsInByb2plY3ROdW1iZXIiOjM5OTQ2Njk4NDk1MX0.AB2LPV8wRQIhAKeL8J0-ghxpBjdnQpnFyxBzBO-Ml6cIc3HbiHdBJ9P_AiALtVeycFO8oB2vZ5kTbMdaXk1nQ-FSHTPuHyYTTTwtBg";


        Notification notification = new Notification(txtTitle.getText().toString(), txtBody.getText().toString());

        Mensaje mensaje = new Mensaje(to, notification);
        Service service = retrofit.create(Service.class);
        Call<ResponseBody> call =
                service.enviarNotificacion(contentType,authorization,mensaje);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Toast.makeText(MainActivity.this,
                            "Mensaje enviado a Firebase", Toast.LENGTH_SHORT).show();
                } else  {
                    Toast.makeText(MainActivity.this,
                            "Mensaje No se envio : "
                                    + response.code() , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}