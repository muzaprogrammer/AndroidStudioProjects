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
import com.edu.sv.Parcial3.models.RespProducto;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ActivityMainBinding binding;
    ProductAdapter productAdapter;
    List<Producto> productos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        inicilizarInterface();

        mostrar_todos_los_productos();
    }

    private void inicilizarInterface() {
        productAdapter = new ProductAdapter(this,productos);
        binding.rcvProductos.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvProductos.setAdapter(productAdapter);
    }


    private void mostrar_todos_los_productos(){
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

    private  void mostrar_por_codigo(String codigo){
        Call<RespProducto> call = Servicio.service.getProductById(codigo);
        call.enqueue(new Callback<RespProducto>() {
            @Override
            public void onResponse(Call<RespProducto> call, Response<RespProducto> response) {
                if (response.code()==200) {
                    RespProducto producto = response.body();
                    if (producto.getResultado()==null) {
                        Toast.makeText(getBaseContext(),"Código NO existe",
                                Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        productos.clear();
                        Producto prod = response.body().getResultado();
                        productos.add(prod);
                        productAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailure(Call<RespProducto> call, Throwable t) {

            }
        });
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        if (!query.trim().equals("")) {
            mostrar_por_codigo(query);
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.trim().equals("")) mostrar_todos_los_productos();
        return true;
    }
}