package com.example.a3tamaniosdensidades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText edtUser, edtClave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtUser = findViewById(R.id.edtUser);
        edtClave = findViewById(R.id.edtClave);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String usuario = edtUser.getText().toString();
        String clave = edtClave.getText().toString();

        outState.putString("usuario",usuario);
        outState.putString("clave",clave);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        edtUser.setText(savedInstanceState.getString("usuario"));
        edtClave.setText(savedInstanceState.getString("clave"));
    }
}