package com.example.a9retrofita;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.a9retrofita.interfaces.JSONPlaceHolderAPI;
import com.example.a9retrofita.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView tvContenido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContenido = findViewById(R.id.tvContenido);
        ObtenerDatos();
    }

    private void ObtenerDatos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JSONPlaceHolderAPI service = retrofit.create(JSONPlaceHolderAPI.class);
        Call<List<Post>> call = service.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()){
                    tvContenido.setText("Código de respuesta : " + response.code());
                    return;
                }
                List<Post> datos = response.body();
                String registro="";
                for ( Post post : datos) {
                    registro="";
                    registro += "User Id : " + post.getUserId() + "\n";
                    registro += "Id      : " + post.getId() + "\n";
                    registro += "Title   : " + post.getTitle() + "\n";
                    registro += "Body    : " + post.getBody() + "\n\n";
                    tvContenido.append(registro);
                }
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                tvContenido.setText(t.getMessage());
            }
        });
    }
}