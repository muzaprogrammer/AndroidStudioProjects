package com.example.a2preferenciasusuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    TextInputEditText txtusuario, txtclave;
    CheckBox chkguardar;
    SharedPreferences preferences;
    int intentos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtusuario = findViewById(R.id.txtusuario);
        txtclave = findViewById(R.id.txtclave);
        chkguardar = findViewById(R.id.chk_guardar);

        preferences = getPreferences(MODE_PRIVATE);
        txtusuario.setText(preferences.getString("usuario",""));
        txtclave.setText(preferences.getString("clave",""));
        chkguardar.setChecked(preferences.getBoolean("guardar",false));

    }

    public void login(View view) {
        String usuario = txtusuario.getText().toString().trim();
        String clave = txtclave.getText().toString().trim();

        if (! (usuario.equals("ADMIN") && clave.equals("123"))){
            Toast.makeText(this,"Credenciales no validas",Toast.LENGTH_SHORT).show();
            intentos++;
            if (intentos==4) finish();

        } else {
            Toast.makeText(this,"Bienvenido " + usuario,Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = preferences.edit();
            if (chkguardar.isChecked()) {
                editor.putString("usuario",usuario);
                editor.putString("clave",clave);
            } else {
                editor.putString("usuario","");
                editor.putString("clave","");
            }
            editor.putBoolean("guardar",chkguardar.isChecked());
            editor.commit();

        }
    }

    public void salir(View view) {
    }
}