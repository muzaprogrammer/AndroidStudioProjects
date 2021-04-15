package com.example.a7sqllite;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class Consultar_activity extends Activity implements	OnItemLongClickListener {
	// Definición de variables a nivel de clase
	PaisesSQLite base;
	int codpaisseleccionado = 0;
	int codcontinente = 0;
	Spinner spnContinentes;
	ListView listaPaises;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consultar_activity);
		// Referencias a controles
		spnContinentes = (Spinner) this
				.findViewById(R.id.spnContinentesConsultar);
		listaPaises = (ListView) this.findViewById(R.id.lstPaises);	
		fijarDatosIniciales();
	}
	public void fijarDatosIniciales() {
		// Fijar accion a realizar cuando usuario mantenga presionado un item de la lista
		// Se procedera a preguntar si quiere borrar el registro seleccionado
		listaPaises.setOnItemLongClickListener(this);
		// Se crea instancia de clase que da el soporte para acceder a la base de datos
		base = new PaisesSQLite(this, "DBPaises", null, 1);
		// Se recupera la lista de nombres de continentes
		base.consultarContinentes();
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,MainActivity.nombrescontinentes);
		// Se establece el adaptador al control Spinner para poder seleccionar
		// un continente
		spnContinentes.setAdapter(adaptador);
	}

	public void mostrar_paises(View v) { 
		// Metodo utilizado por el boton consultar.
		// Obtenemos el numero de elemento seleccionado del control spinner
		int pos = spnContinentes.getSelectedItemPosition();
		codcontinente = MainActivity.codigoscontinentes[pos];
		// Invocamos al metodo que se encarga de establecer el adaptador
		// que contiene la lista de paises en la lista
		fijar_adaptador_paises();
	}

	public void fijar_adaptador_paises() {
		// Llenamos el arreglo de paises en base al continente seleccionado
		base.consultarPaises(codcontinente);
		ArrayAdapter<String> adapt_paises = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, MainActivity.nombrepaises);
		// Se establece el adaptador para mostrar los paises
		listaPaises.setAdapter(adapt_paises);
	}
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos,
			long arg3) {
		// Método ejecutado cuando el usuario mantenga presionado el pais que desee borrar
		codpaisseleccionado = MainActivity.codigospaises[pos];
		borrarPais();
		return true;
	}
	public void borrarPais() {
		// Método en el cual se le preguntará al usuario si esta seguro de borrar el pais
		// Selecionado mediante un cuadro de diálogo.
		Context context = this;
		String title = "Consulta";
		String message = "Desea borrar registro?";
		String strbt1 = "Si"; String strbt2 = "No";
		AlertDialog.Builder ad = new AlertDialog.Builder(context);
		ad.setTitle(title);	ad.setIcon(android.R.drawable.star_big_on);
		ad.setMessage(message);
		ad.setPositiveButton(strbt1, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int arg1) {
				// Borrado del pais segun el codigo de pais seleccionado
				SQLiteDatabase db = base.getWritableDatabase();
				db.execSQL("DELETE FROM PAISES WHERE codpais ="	+ codpaisseleccionado);
				// Para refrescar la lista de paises mostrados
				fijar_adaptador_paises();
				db.close();
				Toast.makeText(getBaseContext(), "Registro borrado",Toast.LENGTH_LONG).show();
			}
		});
		ad.setNegativeButton(strbt2, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int arg1) {
				Toast.makeText(getBaseContext(), "Eliminación cancelada",Toast.LENGTH_LONG).show();
			}
		}).setIcon(android.R.drawable.star_on);
		ad.show();
	}

}
