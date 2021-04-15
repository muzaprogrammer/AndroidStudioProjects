package com.example.a2menus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tvHola;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvHola = findViewById(R.id.tvHola);
        tvHola.setOnCreateContextMenuListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Esta es otra forma de hacerlo
        //getMenuInflater().inflate(R.menu.menu1,menu);
        MenuInflater menuInflater =  getMenuInflater();
        menuInflater.inflate(R.menu.menu1,menu);

        menu.add(Menu.NONE,5,Menu.NONE,"Opcion 5").setIcon(android.R.drawable.btn_plus).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        SubMenu subMenu = menu.addSubMenu(Menu.NONE,5,Menu.NONE, "Sub Opc 5");
        subMenu.add(Menu.NONE,51,Menu.NONE,"Sub opc 51");
        subMenu.add(Menu.NONE,52,Menu.NONE,"Sub opc 52");

        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnu_opc1:
                Toast.makeText(this, "Uno", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mnu_opc2:
                Toast.makeText(this, "Dos", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mnu_opc3:
                Toast.makeText(this, "Tres", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mnu_opc4:
                Toast.makeText(this, "Cuatro", Toast.LENGTH_SHORT).show();
                break;
            case 51:
                Toast.makeText(this, "Cinco.1", Toast.LENGTH_SHORT).show();
                break;
            case 52:
                Toast.makeText(this, "Cinco.2", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "OTRA", Toast.LENGTH_SHORT).show();
                break;
        }

        //return super.onOptionsItemSelected(item);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu1,menu);
        //super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnu_opc1:
                Toast.makeText(this, "Uno", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mnu_opc2:
                Toast.makeText(this, "Dos", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mnu_opc3:
                Toast.makeText(this, "Tres", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mnu_opc4:
                Toast.makeText(this, "Cuatro", Toast.LENGTH_SHORT).show();
                break;
            case 51:
                Toast.makeText(this, "Cinco.1", Toast.LENGTH_SHORT).show();
                break;
            case 52:
                Toast.makeText(this, "Cinco.2", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "OTRA", Toast.LENGTH_SHORT).show();
                break;
        }
        //return super.onContextItemSelected(item);
        return true;
    }


}