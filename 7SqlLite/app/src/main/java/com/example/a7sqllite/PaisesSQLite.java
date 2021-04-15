package com.example.a7sqllite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class PaisesSQLite extends SQLiteOpenHelper {
	// Definición de DDL'S para crear las tablas de paises y continentes
	String sqlCreatePaises = "Create Table Paises (codpais INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ " nombre TEXT, codconti INTEGER, lat REAL, lon REAL)";
	String sqlCreateContinentes = "Create Table Continentes " +
	   "(codconti INTEGER, nombre TEXT)";
	
	public PaisesSQLite(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		//M�todo ejecutado autom�ticamente si la base de datos no existe
		db.execSQL(sqlCreateContinentes);
		db.execSQL(sqlCreatePaises);
		poblarDB(db);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// M�todo ejecutado si el numero de newVersion es SUPERIOR a oldVersion
		db.execSQL("DROP TABLE IF EXISTS Continentes");
		db.execSQL("DROP TABLE IF EXISTS Paises");
		this.onCreate(db);
	}
	
	public void poblarDB(SQLiteDatabase db)
	{
		// Procedimiento para inicializar con algunos registros la DB
		// Tabla de continentes
		db.execSQL("INSERT INTO CONTINENTES VALUES (1,'AMERICA')");
		db.execSQL("INSERT INTO CONTINENTES VALUES (2,'EUROPA')");
		db.execSQL("INSERT INTO CONTINENTES VALUES (3,'ASIA')");
		db.execSQL("INSERT INTO CONTINENTES VALUES (4,'AFRICA')");
		db.execSQL("INSERT INTO CONTINENTES VALUES (5,'OCEANIA')");
		// Tabla de Paises 
		// AM�RICA
		db.execSQL("INSERT INTO PAISES VALUES (null,'EL SALVADOR',1,13.701402, -89.224650)");
		db.execSQL("INSERT INTO PAISES VALUES (null,'MEXICO',1,19.570988, -99.169165)");
		db.execSQL("INSERT INTO PAISES VALUES (null,'COSTA RICA',1,9.931861, -84.095922)");
		db.execSQL("INSERT INTO PAISES VALUES (null,'COLOMBIA',1,6.239748, -75.587011)");
		db.execSQL("INSERT INTO PAISES VALUES (null,'ARGENTINA',1,-34.600452, -58.383173)");
		// EUROPA
		db.execSQL("INSERT INTO PAISES VALUES (null,'ESPA�A',2,40.410745, -3.764640)");
		db.execSQL("INSERT INTO PAISES VALUES (null,'ALEMANIA',2,52.515696, 13.367740)");
		db.execSQL("INSERT INTO PAISES VALUES (null,'FRANCIA',2,48.900271, 2.315495)");

	}
	
	public void consultarContinentes()
	{
		//Se obtiene referencia a base de datos para relizar consultas
		SQLiteDatabase db = getReadableDatabase();
		//Se realiza consulta y el resultado es almacenado en un objeto cursor
		Cursor cursor = db.rawQuery("select codconti,nombre from continentes",null);
		//Creamos la capacidad de cada arreglo segun la cantidad de registros del objeto cursor
		MainActivity.nombrescontinentes = new String[cursor.getCount()];
		MainActivity.codigoscontinentes = new int [cursor.getCount()];
		int i=0;
		if (cursor.moveToFirst())
		{	
			do
			{
				//Recorremos el cursor para llenar los arreglos que contienen
				//el codigo y el nombre de los continentes
				MainActivity.codigoscontinentes[i] = cursor.getInt(0);
				MainActivity.nombrescontinentes[i] = cursor.getString(1);		
				i++;
			} while (cursor.moveToNext());
		}
		db.close();
	}
	
	public void consultarPaises(int codconti)
	{
		// Procedimiento que ejecuta la consulta de los paises
		// Según el continente seleccionado
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from paises where codconti=" + codconti ,null);
		MainActivity.nombrepaises = new String[cursor.getCount()];
		MainActivity.codigospaises = new int[cursor.getCount()]	;
		
		int i=0;
		if (cursor.moveToFirst())
		{
			do
			{	// Se llenan los arreglos que contienen los codigos de paises 
				// y el nombre de los mismos
				MainActivity.codigospaises[i] = cursor.getInt(0);
				MainActivity.nombrepaises[i] = cursor.getString(1);
				i++;
			} while (cursor.moveToNext());
		}
		db.close();
	}

}
