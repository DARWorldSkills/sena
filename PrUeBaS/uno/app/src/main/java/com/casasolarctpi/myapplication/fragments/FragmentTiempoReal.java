package com.casasolarctpi.myapplication.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.casasolarctpi.myapplication.R;
import com.casasolarctpi.myapplication.controllers.MainActivity;
import com.casasolarctpi.myapplication.models.CustomMarkerViewData1;
import com.casasolarctpi.myapplication.models.DatosTiempoReal;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTiempoReal extends Fragment {
    TextView txtNombre;
    public static LineChart lineaTiempoReal;
    private View view;
    private String tipoDeDato;
    private TextView txtGrafica;
    public static  int modoGraficar;
    private int colorGrafica, colorTextoGrafica;
    float valorMaximo, valorMinimo;
    private XAxis xAxis;
    List<DatosTiempoReal> datosTiempoRealList = new ArrayList<>();
    public static List<Entry> entry1 = new ArrayList<>();
    private List<String> labelsChart = new ArrayList<>();
    private boolean bandera = false;

    public FragmentTiempoReal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fragment_tiempo_real, container, false);
        //GENERACION DE METODOS, ES EL ONCREATE DEL FRAGMENTO
        inicializar();
        inicializarValores();
        ingresarValoresFirebase();


        return view;
    }

    //METODO QUE USAMOS PARA INICIALIZAR VALORES
    private void inicializar() {
        txtNombre = view.findViewById(R.id.txtTituloChartRT);
        lineaTiempoReal = view.findViewById(R.id.tiempoRealChart);
    }

    //METODO QUE SIRVE PARA DEFINIR LAS GRAFICAS DEPENDIENDO EL TIPO
    private void inicializarValores() {
        switch (modoGraficar){
            case 0:
                colorGrafica = getResources().getColor(R.color.colorGraficaLinea1);
                colorTextoGrafica = getResources().getColor(R.color.colorGraficaPunto1);
                tipoDeDato = getResources().getString(R.string.dato1);
                break;

            case 1:
                colorGrafica = getResources().getColor(R.color.colorGraficaLinea4);
                colorTextoGrafica = getResources().getColor(R.color.colorGraficaPunto4);
                tipoDeDato = getResources().getString(R.string.dato1);

                break;

            case 2:
                colorGrafica = getResources().getColor(R.color.colorGraficaLinea3);
                colorTextoGrafica = getResources().getColor(R.color.colorGraficaPunto3);
                tipoDeDato = getResources().getString(R.string.dato1);
                break;


            case 3:
                colorGrafica = getResources().getColor(R.color.colorGraficaLinea5);
                colorTextoGrafica = getResources().getColor(R.color.colorGraficaPunto5);
                tipoDeDato = getResources().getString(R.string.dato1);
                break;


        }
        lineaTiempoReal.setVisibility(View.INVISIBLE);
        bandera=false;

    }

    private void ingresarValoresFirebase() {

        DatabaseReference datosTipoReal = MainActivity.reference.child("Tiempo Real");

        //QUERY PARA LIMITAR LOS DATOS
        datosTipoReal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<DatosTiempoReal>> t = new GenericTypeIndicator<ArrayList<DatosTiempoReal>>(){};
                datosTiempoRealList = dataSnapshot.getValue(t);
                if (!bandera) {
                    inputValuesChart();
                    bandera=true;
                }else {
                    if (entry1.size()>0) {
                        inputValuesRealTime();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    //METODO PARA INGRESAR VALORES A LA GRAFICA
    private void inputValuesChart() {

        final Date[] date1 = {new Date(), new Date()};
        final SimpleDateFormat dateFormat =new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Collections.sort(datosTiempoRealList, new Comparator<DatosTiempoReal>() {
            @Override
            public int compare(DatosTiempoReal o1, DatosTiempoReal o2) {
                try {
                    date1[0] = dateFormat.parse( o1.getFechaActual1());
                    date1[1] = dateFormat.parse( o2.getFechaActual1());
                    if (date1[0].getTime() < date1[1].getTime()) {
                        return -1;
                    }
                    if (date1[0].getTime() > date1[1].getTime()) {
                        return 1;
                    }
                    return 0;

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });

        for (int i= 0; i <datosTiempoRealList.size(); i++){
            labelsChart.add(datosTiempoRealList.get(i).getHora());
            float dato = 0;


            try {
                switch (modoGraficar){
                    case 0:
                        dato =Float.parseFloat(datosTiempoRealList.get(i).getHumedad());
                        break;

                    case 1:
                        dato =Float.parseFloat(datosTiempoRealList.get(i).getIrradiancia());
                        break;


                    case 2:
                        dato =Float.parseFloat(datosTiempoRealList.get(i).getCorriente1());
                        dato =Float.parseFloat(datosTiempoRealList.get(i).getCorriente2());
                        dato =Float.parseFloat(datosTiempoRealList.get(i).getCorriente3());
                        dato =Float.parseFloat(datosTiempoRealList.get(i).getCorriente4());
                        break;


                    case 3:
                        dato =Float.parseFloat(datosTiempoRealList.get(i).getVoltaje1());
                        dato =Float.parseFloat(datosTiempoRealList.get(i).getVoltaje2());
                        dato =Float.parseFloat(datosTiempoRealList.get(i).getVoltaje3());
                        dato =Float.parseFloat(datosTiempoRealList.get(i).getVoltaje4());
                        break;

                }

                if (dato>valorMaximo){
                    valorMaximo = dato;
                }

                if (valorMinimo==0){
                    valorMinimo=dato;
                }
                if (dato<valorMinimo){
                    valorMinimo = dato;
                }

            }catch (Exception ignore){

            }
            entry1.add(new Entry(i,dato));
        }

            //COMPLEMENTOOO QUE FALTA

        }

    //METODO PARA INGRESAR VALORES A LA GRAFICA TIEMPO REAL
    private void inputValuesRealTime() {

        entry1.clear();
        labelsChart.clear();

        final Date[] date1 = {new Date(),new Date()};
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Collections.sort(datosTiempoRealList, new Comparator<DatosTiempoReal>() {
            @Override
            public int compare(DatosTiempoReal o1, DatosTiempoReal o2) {
                try {
                    date1[0] =dateFormat.parse(o1.getFechaActual1());
                    date1[1] =dateFormat.parse(o2.getFechaActual1());
                    if (date1[0].getTime() < date1[1].getTime()) {
                        return -1;
                    }
                    if (date1[0].getTime() > date1[1].getTime()) {
                        return 1;
                    }
                    return 0;

                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });


        for (int i=0; i<datosTiempoRealList.size();i++){
            labelsChart.add(datosTiempoRealList.get(i).getHora());
            float dato=0;
            try {
                switch (modoGraficar) {
                    case 0:
                        dato = Float.parseFloat(datosTiempoRealList.get(i).getHumedad());
                        break;

                    case 1:
                        dato = Float.parseFloat(datosTiempoRealList.get(i).getIrradiancia());
                        break;

                    case 2:
                        dato = Float.parseFloat(datosTiempoRealList.get(i).getCorriente1());
                        dato = Float.parseFloat(datosTiempoRealList.get(i).getCorriente2());
                        dato = Float.parseFloat(datosTiempoRealList.get(i).getCorriente3());
                        dato = Float.parseFloat(datosTiempoRealList.get(i).getCorriente4());
                        break;


                    case 3:
                        dato = Float.parseFloat(datosTiempoRealList.get(i).getVoltaje1());
                        dato = Float.parseFloat(datosTiempoRealList.get(i).getVoltaje2());
                        dato = Float.parseFloat(datosTiempoRealList.get(i).getVoltaje3());
                        dato = Float.parseFloat(datosTiempoRealList.get(i).getVoltaje4());
                        break;
                }

                if (dato>valorMaximo){
                    valorMaximo = dato;
                }

                if (valorMinimo==0){
                    valorMinimo=dato;
                }
                if (dato<valorMinimo){
                    valorMinimo = dato;
                }

            }catch (Exception ignore){

            }
            entry1.add(new Entry(i,dato));

        }

        if (entry1.size()>0){
            lineaTiempoReal.notifyDataSetChanged();
            lineaTiempoReal.invalidate();
            lineaTiempoReal.setVisibility(View.VISIBLE);
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsChart));

        YAxis yAxisLeft = lineaTiempoReal.getAxisLeft();
        YAxis yAxisRight = lineaTiempoReal.getAxisRight();

        if (valorMinimo>10){
            valorMinimo-=0.1f;
        }


        yAxisLeft.setAxisMaximum(valorMaximo+0.2f);
        yAxisRight.setAxisMaximum(valorMaximo+0.2f);
        yAxisLeft.setAxisMinimum(valorMinimo);
        yAxisRight.setAxisMinimum(valorMinimo);
        valorMaximo=0;
        valorMinimo=0;


    }

}
