package com.example.a14firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.installations.InstallationTokenResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    EditText edtToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtToken= findViewById(R.id.edtToken);
    }

    public void getToken(View view) {
/*
        FirebaseInstallations.getInstance().getToken(false).addOnSuccessListener(new OnSuccessListener<InstallationTokenResult>() {
            @Override
            public void onSuccess(InstallationTokenResult installationTokenResult) {
                String token = installationTokenResult.getToken();
                Toast.makeText(getApplicationContext(),token,Toast.LENGTH_LONG).show();
                edtToken.setText(token);
            }
        });
*/
        String t = FirebaseMessaging.getInstance().getToken().getResult();
        edtToken.setText(t);
        Log.i("TOKEN",t);
    }

    public void subscribirse(View view) {
        FirebaseMessaging.getInstance().subscribeToTopic("NOTICIAS")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Te has subscrito al tema NOTICIAS";
                        if (!task.isSuccessful()) {
                            msg = "No te has podido subscribir al tema NOTICIAS";
                        }
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}