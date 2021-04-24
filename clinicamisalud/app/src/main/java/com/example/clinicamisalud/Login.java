package com.example.clinicamisalud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText inputEmail, inputPassword;
    Button btnLogin;
    TextView gotoRegister;
    //agregando Id de Chekbox y sharedPreferences
    CheckBox checkbox;
    SharedPreferences preferencia;

//Para utilizar google
    SignInButton btnGoogle;
    int RC_SIGN_IN = 0;
    GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        gotoRegister = findViewById(R.id.gotoRegister);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoogle = findViewById(R.id.sign_in_button);

        //referencias para checkbox y sharedpreference
        checkbox = findViewById(R.id.checkbox);
        preferencia = getPreferences(MODE_PRIVATE);
        // enviando los datos del shared preferences a los Text de usuario y contraseña
        inputEmail.setText(preferencia.getString("usuario",""));
        inputPassword.setText(preferencia.getString("clave",""));
        // Estado inicial del checkbox
        checkbox.setChecked(preferencia.getBoolean("guardar", false));

        //para ir a la ventana de registro de nuevos usuarios
        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registro.class);
                startActivity(intent);
            }
        });

        //para acceder a las funciones de la app
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarUsuario("https://apiapp.misalud.com.sv/citas/login?user=" + inputEmail.getText().toString() + "&pass=" + inputPassword.getText().toString());

            }
        });

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;

                }
            }
        });

        //invocando las opciones de google ya sea registro o iniciar sesion con una cuenta ya guardada.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }



    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       //verifica si hay una cuenta de google ya sincronizada con el dispositivo
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Intent intent = new Intent(Login.this, Googcount.class);
            startActivity(intent);

        } catch (ApiException e) {
            Log.w("Error ", "signInResult:failed code=" + e.getStatusCode());
        }
    }


// api de misalud
    private void validarUsuario(String URL){

        Log.i("url",""+URL);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("url",""+response);

                if(!response.isEmpty()){
                    Intent intent = new Intent(getApplicationContext(), Menu.class);
                    intent.putExtra("email", inputEmail.getText().toString());
                    intent.putExtra("pass", inputPassword.getText().toString());

                    //Validando el estado del checkbox
                    SharedPreferences.Editor editor = preferencia.edit();
                    if (checkbox.isChecked()) {
                        editor.putString( "usuario", inputEmail.getText().toString());
                        editor.putString( "clave", inputPassword.getText().toString());
                    } else{
                        editor.putString( "usuario", "");
                        editor.putString( "clave", "");
                    }
                    editor.putBoolean("guardar",checkbox.isChecked());
                    editor.commit();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Login.this,"Usuario o contraseña incorrecto",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "CREDENCIALES INCORRECTAS",Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros=new HashMap<String,String>();
                parametros.put("usuario",inputEmail.getText().toString());
                parametros.put("password",inputPassword.getText().toString());


                return super.getParams();
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
