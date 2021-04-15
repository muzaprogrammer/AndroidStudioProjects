package com.example.a7sqllite;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Build;

public class Agregar_activity extends Activity {
	// Definición de variables a nivel de clase
	PaisesSQLite base;
	int codcontinente = 0;
	Spinner spnContinente;
	EditText edtNombrePais,edtLatitud,edtLongitud;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agregar_activity);
		// Referenciando cada uno de los controles 
		spnContinente =  this.findViewById(R.id.spnContinenteAgregar);
		edtNombrePais =  this.findViewById(R.id.edtNombrePais);
		edtLatitud =   this.findViewById(R.id.edtLatitud);
		edtLongitud =   this.findViewById(R.id.edtLongitud);
		fijarDatosIniciales();
	}

	public void salir(View v) {
		finish();
	}

	public void fijarDatosIniciales() {
		// Se crea instancia de clase que da el soporte para acceder a la base de datos
		base = new PaisesSQLite(this, "DBPaises", null, 1);
		// Se recupera la lista de nombres de continentes
		base.consultarContinentes();
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,
				MainActivity.nombrescontinentes);
		// Se establece el adaptador al control Spinner para poder seleccionar un continente
		spnContinente.setAdapter(adaptador);
	}
	public void guardar(View v) {
		// Retomando datos digitados por el usuario
		int pos = spnContinente.getSelectedItemPosition();
		codcontinente = MainActivity.codigoscontinentes[pos];
		double latitud = Double.valueOf( edtLatitud.getText().toString()) ;
		double longitud = Double.valueOf( edtLongitud.getText().toString()) ;
		// Creando referencia hacia base de datos en modo escritura
		SQLiteDatabase db = base.getWritableDatabase(); 		
		// instando registro
		db.execSQL("INSERT INTO PAISES VALUES (null,'"
				+ edtNombrePais.getText().toString() + "'," 
				+ codcontinente + "," + latitud + "," + longitud + ")" ) ;
		db.close();
		Toast.makeText(this, "País guardado", Toast.LENGTH_SHORT).show();
		finish();
	}



}
