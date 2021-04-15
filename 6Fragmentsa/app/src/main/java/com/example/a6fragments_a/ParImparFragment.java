package com.example.a6fragments_a;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class ParImparFragment extends Fragment {
    private Button boton;
    private EditText edit;
    private TextView text;
    public ParImparFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_par_impar, container, false);

        //Nuevos parametros para el view del fragmento
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT);

        //Nueva Regla: EL fragmento estara debajo del boton
        params.addRule(RelativeLayout.BELOW, R.id.btnAgregarFragment);
        //Margenes: top:41dp
        params.setMargins(0,41,0,0);
        //Setear los parametros al view
        view.setLayoutParams(params);


        boton = view.findViewById(R.id.btnValidar);
        edit = view.findViewById(R.id.edtNumero);
        text = view.findViewById(R.id.tvResultado);

        boton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                int numero = Integer.valueOf(edit.getText().toString());
                String resultado  = numero % 2 == 0 ? "Par" : "Impar";
                text.setText(resultado);
            }
        });
        return view;
    }
}
