package com.example.usodeuncontentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.CallLog.Calls;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    TextView salida;
    String[] TIPO_LLAMADA= {"", "Entrante","Saliente","Perdida","Otra","Cancelada"};
    Button btnMostrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        salida = (TextView) this.findViewById(R.id.tvContenido);
        btnMostrar = (Button) this.findViewById(R.id.btnmostrar);
        btnMostrar.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        Uri llamadas = android.provider.CallLog.Calls.CONTENT_URI; //Uri.parse("content://call_log/calls")
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(llamadas, null, null, null, null);
        salida.append("Detalle de llamadas");
        int cont=0;
        while (c.moveToNext())
        {	cont++;
            salida.append("\n" + DateFormat.format("dd/MM/yyyy k:mm",
                    c.getLong(c.getColumnIndex(Calls.DATE))) + " ("
                    + c.getString(c.getColumnIndex(Calls.DURATION)) + ") "
                    +c.getString(c.getColumnIndex(Calls.NUMBER)) + ", "
                    +TIPO_LLAMADA[Integer.parseInt(c.getString(c.getColumnIndex(Calls.TYPE)))]);


        }
        Toast.makeText(this, "Cantidad de registros: "+ String.valueOf(cont), Toast.LENGTH_LONG).show();
        c.close();


    }



}
