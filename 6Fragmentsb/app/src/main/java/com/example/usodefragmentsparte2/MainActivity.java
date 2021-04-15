package com.example.usodefragmentsparte2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends FragmentActivity
        implements FragmentListado.PaisesListener {

    int posicion=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se busca referencia al fragment que tiene la lista
        FragmentListado frgListado = (FragmentListado)
                getSupportFragmentManager().findFragmentById(R.id.FrgListado);
        // A este fragment se le establece un listener (la clase actual)
        // con el objetivo de invocar el método que se activará
        // cuando se seleccione un item de dicha lista. (onPaisSeleccionado)
        frgListado.setPaisListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Myposition",posicion);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int p = savedInstanceState.getInt("Myposition",-1);
        this.onPaisSeleccionado(p);
    }

    @Override
    public void onPaisSeleccionado(int  pos) {
        if (pos <0) return;
        // Verificando si existe fragment de detalle
        this.posicion = pos;
        boolean existeDetalle =
                (getSupportFragmentManager().findFragmentById(R.id.FrgDetalle) != null);
        if (existeDetalle)
        {   // Si existen si usa el fragment de detalle a través del método mostrarDetalle
            ( (FragmentDetalle) getSupportFragmentManager()
                    .findFragmentById(R.id.FrgDetalle)).mostrarDetalle( pos);
        }
        else
        {	//Si no Existe el fragment de detalle (Pantallas pequeñas)  se invoca una activity
            Intent i = new Intent(this, DetalleActivity.class);
            i.putExtra(DetalleActivity.EXTRA_POS, pos);
            startActivity(i);
        }
    }
}

