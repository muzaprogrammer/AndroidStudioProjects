package com.example.creaciondeuncontentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class PaisesProvider extends ContentProvider {
    public static final String AUTORIDAD="com.example.creaciondeuncontentprovider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTORIDAD + "/paises");
    public static final int TODOS_LOS_ELEMENTOS = 1;
    public static final int UN_ELEMENTO = 2;
    private static UriMatcher URI_MATCHER = null;
    public static final String TABLA = "paises";
    private SQLiteDatabase baseDeDatos;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(AUTORIDAD, "paises", TODOS_LOS_ELEMENTOS);
        URI_MATCHER.addURI(AUTORIDAD, "paises/#", UN_ELEMENTO);
    }

    @Override
    public boolean onCreate() {
        PaisesSQLite dbHelper = new
                PaisesSQLite(getContext(),"DBPaises",null,1);
        baseDeDatos = dbHelper.getWritableDatabase();

        return baseDeDatos != null && baseDeDatos.isOpen();
    }

    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri))
        {
            case TODOS_LOS_ELEMENTOS:
                return "vnd.android.cursor.dir/vnd.com.example.pais";
            case UN_ELEMENTO:
                return "vnd.android.cursor.item/vnd.com.example.pais";
            default:
                throw new IllegalArgumentException("URI incorrecta" + uri);
        }

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder)
    {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLA);
        String where = selection;
        switch (URI_MATCHER.match(uri))
        {
            case TODOS_LOS_ELEMENTOS:
                break;
            case UN_ELEMENTO:
                where = "codpais = " + uri.getLastPathSegment();
                break;
            default:
                throw new IllegalArgumentException("URI incorrecta: " + uri);
        }
        return queryBuilder.query(baseDeDatos, projection, where, selectionArgs,
                null, null, sortOrder);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long idFila = baseDeDatos.insert(TABLA,null, values);
        if (idFila > 0 )
            return ContentUris.withAppendedId(CONTENT_URI, idFila);
        else
            throw new IllegalArgumentException("ERROR al insertar registro en " + uri);

    }


    @Override
    public int delete(Uri uri, String seleccion, String[] argSeleccion) {
        String where = seleccion;
        switch (URI_MATCHER.match(uri))
        {
            case TODOS_LOS_ELEMENTOS:
                break;
            case UN_ELEMENTO:
                where = "codpais = " + uri.getLastPathSegment();
                break;
            default:
                throw new IllegalArgumentException("URI incorrecta" + uri);
        }
        return baseDeDatos.delete(TABLA, where, argSeleccion);
    }



    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        String where = selection;
        switch (URI_MATCHER.match(uri))
        {
            case TODOS_LOS_ELEMENTOS:
                break;
            case UN_ELEMENTO:
                where = "codpais = " + uri.getLastPathSegment();
                break;
            default:
                throw new IllegalArgumentException("URI incorrecta" + uri);
        }
        return baseDeDatos.update(TABLA, values, where,selectionArgs);
    }


}
