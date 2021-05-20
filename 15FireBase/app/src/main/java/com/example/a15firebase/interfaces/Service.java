package com.example.a15firebase.interfaces;

import com.example.a15firebase.models.Mensaje;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Service {

    @POST("fcm/send")
    Call<ResponseBody> enviarNotificacion(
            @Header("Content-Type") String contentType,
            @Header("Authorization") String authorization,
            @Body Mensaje mensaje);

}
