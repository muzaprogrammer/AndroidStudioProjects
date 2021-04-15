package com.example.usodefragmentsparte2;

import android.content.res.Resources;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentListado extends Fragment {
    private ListView lstListado;
    private PaisesListener listener;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listado, container, false);
    }
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        lstListado = (ListView)getView().findViewById(R.id.LstListado);
        Resources res = this.getResources();
        String[] paises = res.getStringArray(R.array.paises);
        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this.getActivity(),
                        android.R.layout.simple_list_item_1,paises);
        lstListado.setAdapter(adaptador);
        lstListado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View view, int pos,long id) {
                if (listener!=null) {
                    listener.onPaisSeleccionado(pos);
                }
            }
        });
    }

    // Definimos una interface que usará la clase MainActivity
    public interface PaisesListener {
        void onPaisSeleccionado(int pos);
    }
    // Método que establece el objeto listener que estará
    // pendiente de la selección de un item de la lista
    public void setPaisListener(PaisesListener listener) {
        this.listener=listener;
    }
}