package com.edu.sv.Parcial3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.edu.sv.Parcial3.databinding.ActivityMainBinding;
import com.edu.sv.Parcial3.interfaces.Servicio;
import com.edu.sv.Parcial3.models.Producto;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ProductAdapter productAdapter;
    List<Producto> productos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        inicilizarInterface();

        previa();
    }

    private void inicilizarInterface() {
        productAdapter = new ProductAdapter(this,productos);
        binding.rcvProductos.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvProductos.setAdapter(productAdapter);
    }

    public void previa(){
        List items = new ArrayList();
        items.add(new Producto("recarge","recarge",1));
        productos.clear();
        productos.addAll(items);
        productAdapter.notifyDataSetChanged();
    }

    public void reset(View view){
       previa();
    }

    public void mostrar_todos_los_productos(View view){
        Call<List<Producto>> call = Servicio.service.getProducts();
        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (response.code() == 200) {
                    productos.clear();
                    List<Producto> prods = response.body();
                    productos.addAll(prods);
                    productAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getBaseContext(),"Error:" + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Toast.makeText(getBaseContext(),"Error:" + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}