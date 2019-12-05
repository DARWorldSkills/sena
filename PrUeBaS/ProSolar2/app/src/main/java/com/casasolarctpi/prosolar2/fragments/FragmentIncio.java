package com.casasolarctpi.prosolar2.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.casasolarctpi.prosolar2.R;
import com.casasolarctpi.prosolar2.models.Constants;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


public class FragmentIncio extends Fragment implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    Handler handler = new Handler(); // En esta zona creamos el objeto Handler
    private ProgressBar _progressBar;
    private final int TIEMPO = 5000;
    View view;
    //3600000 una hora
    MaterialSpinner spinnerInicio;
    RecyclerView recyclerView;
    List<String> corrientes = new ArrayList<>();
    List<String> voltajes = new ArrayList<>();

    public FragmentIncio() {

        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.fragment_fragment_incio, container, false);

        //ejecutarTarea();
        inicializar();
        obtenerDatos();

        return view;


    }


    private void obtenerDatos() {

    }

    private void inicializar() {
        spinnerInicio = view.findViewById(R.id.spinnerInicio);
        recyclerView = view.findViewById(R.id.RecyclerInicio);
        spinnerInicio.setItems(Constants.seleccionInicio);
    }

    private void ejecutarTarea() {
        corrientes = new ArrayList<>();
        voltajes = new ArrayList<>();
        corrientes.add("Corrientes");
        voltajes.add("Voltajes");
        handler.postDelayed(new Runnable() {
            public void run() {

                // función a ejecutar
                Toast.makeText(getContext(), "hola", Toast.LENGTH_SHORT).show();
                getDataForPanel("voltaje", "corriente");
                handler.postDelayed(this, TIEMPO);
            }

        }, TIEMPO);
    }



    @Override
    public void onClick(View view) {

    }

    private void getDataForPanel(String corriente1, String voltaje1) {
        switch (spinnerInicio.getSelectedIndex()){
            case 0:
                if (corrientes.size()>=7){
                    corrientes.remove(1);
                    voltajes.remove(1);
                }
                corrientes.add("");
                voltajes.add("");
                break;

            case 1:
                if (corrientes.size()>=7){
                    corrientes.remove(1);
                    voltajes.remove(1);
                }
                corrientes.add("");
                voltajes.add("");
                break;

            case 2:
                if (corrientes.size()>=7){
                    corrientes.remove(1);
                    voltajes.remove(1);
                }
                corrientes.add("");
                voltajes.add("");
                break;


            case 3:
                if (corrientes.size()>=7){
                    corrientes.remove(1);
                    voltajes.remove(1);
                }
                corrientes.add("");
                voltajes.add("");
                break;
        }
    }
}
