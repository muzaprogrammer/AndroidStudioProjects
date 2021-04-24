package com.example.clinicamisalud;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class FragmentTwo extends Fragment {

    TextView txt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);

        txt = view.findViewById(R.id.txt);
        txt.setText("SIEMPRE TRATANDO DE ESTAR CERCA DE TI!!");


        return view;
    }
}
