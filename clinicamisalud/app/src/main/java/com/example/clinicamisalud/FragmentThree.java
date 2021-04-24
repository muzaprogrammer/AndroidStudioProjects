package com.example.clinicamisalud;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class FragmentThree extends Fragment {
    //  Button btnSiguiente;
    ImageView btnSiguiente;
    /** RelativeLayout idfragment3;

     TextView txt; */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        /** txt = view.findViewById(R.id.txt);
         txt.setText("CERCA DE TI..."); */


        btnSiguiente = view.findViewById(R.id.btnSiguiente);
        // btnSiguiente.setImageResource(R.drawable.descarga);
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Bmeters = new Intent( getActivity() , Login.class);
                startActivity(Bmeters);
            }
        });
        return view;

    }
}



