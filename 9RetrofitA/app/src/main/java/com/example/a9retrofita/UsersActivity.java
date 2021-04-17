package com.example.a9retrofita;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.a9retrofita.interfaces.JSONPlaceHolderAPI;
import com.example.a9retrofita.models.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class UsersActivity extends AppCompatActivity {
    TextView tvContenido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        tvContenido = findViewById(R.id.tvContenido);
        ObtenerDatos();
    }
    private void ObtenerDatos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JSONPlaceHolderAPI service = retrofit.create(JSONPlaceHolderAPI.class);
        Call<List<User>> call = service.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()){
                    tvContenido.setText("Código de error: " + response.code());
                    return;
                }
                List<User> datos = response.body();
                String registro = "";
                for (User user : datos) {
                    registro="";
                    registro += "Id :" + user.getId() + "\n";
                    registro += "Name :" + user.getName() + "\n";
                    registro += "UserName :" + user.getUsername() + "\n";
                    registro += "Email :" + user.getEmail() + "\n";
                    registro += "Address :" + "\n\t";
                    registro += "Street :" + user.getAddress().getStreet() + "\n\t";
                    registro += "Suite :" + user.getAddress().getSuite() + "\n\t";
                    registro += "City :" + user.getAddress().getCity() + "\n\t";
                    registro += "ZipCode :" + user.getAddress().getZipcode() + "\n\t";
                    tvContenido.append(registro);
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
            }
        });
    }
}