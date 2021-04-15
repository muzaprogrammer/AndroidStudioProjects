package com.example.a7sqllite;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MainActivity extends Activity {
	public static int[] codigoscontinentes ;
	public static String[] nombrescontinentes ;
	public static int[] codigospaises=null;
	public static String[] nombrepaises=null;;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	public void consultar(View v)
	{	// Invocación de la actividad de consulta
		Intent intent  = new Intent(this,Consultar_activity.class);
		this.startActivity(intent);
	}
	public void agregar(View v)
	{	// invocación de la actividad para agregar un pais
		Intent intent  = new Intent(this,Agregar_activity.class);
		this.startActivity(intent);
	}
}


	
