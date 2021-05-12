package com.example.usodenuestrocontentprovider;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;
import android.widget.Toast;

public class MainActivity extends Activity {
    TextView tvContenido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContenido = (TextView) this.findViewById(R.id.tvContenido);

        Uri uri = Uri.parse("content://com.example.creaciondeuncontentprovider/paises");
        ContentResolver cr = this.getContentResolver();
        /////////////////insert////////////////////////
        ContentValues cv = new ContentValues();
        //cv.put("codpais", "");
        cv.put("codconti", 1);
        cv.put("nombre", "Panam�");
        cv.put("lat", 5);
        cv.put("lon", 6);
        cr.insert(uri, cv);
        ////////////////delete/////////////////////////
        //cr.delete(uri, "codpais=11", null);
        ///////////////update ////////////////////////
        //ContentValues cvupdate = new ContentValues();
        //cvupdate.put("nombre", "PANAMA");
        //cr.update(uri, cvupdate, "codpais=12", null);

        Cursor c = cr.query(uri, null, "codconti=1", null, null);
        Toast.makeText(this, c.getCount()+"", Toast.LENGTH_SHORT).show();
        while (c.moveToNext())
        {
            tvContenido.append(c.getString(1)+ "\n");
        }
        c.close();

    }


}
