package com.edu.sv.Parcial3.interfaces;


import com.edu.sv.Parcial3.models.Producto;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;

public interface APIService {

    @GET(".")
    Call<List<Producto>> getProducts();

}
