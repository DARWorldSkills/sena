package com.casasolarctpi.prosolar2.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.casasolarctpi.prosolar2.R;
import com.casasolarctpi.prosolar2.controllers.MainActivity;
import com.casasolarctpi.prosolar2.models.CustomMarkerViewDataA;
import com.casasolarctpi.prosolar2.models.DatosTiempoReal;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
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
public class FragmentVoltajes extends Fragment {

    View view;
    LineChart tiempoRealVoltaje;
    List<DatosTiempoReal> datosTP = new ArrayList<>();
    List<Entry> entrada = new ArrayList<>();
    private int colorGrafica[] = new int[4];
    float valorMaximo1, valorMinimo1, valorMaximo2, valorMinimo2;
    List<DatosTiempoReal> datosTiempoRealList = new ArrayList<>();
    boolean obtenerPorPrimeraVez = false;
    private List<String> labelsChart = new ArrayList<>();
    public static List<Entry> [] entries = new List[4];
    private List<ILineDataSet> dataSets = new ArrayList<>();
    XAxis xAxis;
    public FragmentVoltajes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_fragment_voltajes, container, false);

        inicializar();
        ingresarValoresFirebase();
        inizialiteListEntries();

        return view;
    }

    private void inicializar() {
        tiempoRealVoltaje = view.findViewById(R.id.tiempoRealVoltaje);
        colorGrafica[0] = getResources().getColor(R.color.colorGraficaLinea1);
        colorGrafica[1] = getResources().getColor(R.color.colorGraficaLinea2);
        colorGrafica[2] = getResources().getColor(R.color.colorGraficaLinea3);
        colorGrafica[3] = getResources().getColor(R.color.colorGraficaLinea4);
    }

    private void inizialiteListEntries() {
        for (int i=0; i<entries.length;i++){
            entries[i] = new ArrayList<>();
        }
    }

    private void ingresarValoresFirebase() {

        DatabaseReference datosTipoReal = MainActivity.reference.child("datos").child("datosRT");

        //QUERY PARA LIMITAR LOS DATOS
        datosTipoReal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<DatosTiempoReal>> t = new GenericTypeIndicator<ArrayList<DatosTiempoReal>>(){};
                datosTiempoRealList = dataSnapshot.getValue(t);
                if (!obtenerPorPrimeraVez) {
                    inputValuesChart();
                    obtenerPorPrimeraVez=true;
                }else {
                    if (entries[0].size()>0) {
                        inputValuesRealTime();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void inputValuesChart() {

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

        float [] todosLosDatos = new float[4];
        for (int i=0; i<10;i++){
            labelsChart.add(datosTiempoRealList.get(i).getHora());
            try {

                todosLosDatos[0] = Float.parseFloat(datosTiempoRealList.get(i).getVoltaje1());
                todosLosDatos[1] = Float.parseFloat(datosTiempoRealList.get(i).getVoltaje2());
                todosLosDatos[2] = Float.parseFloat(datosTiempoRealList.get(i).getVoltaje3());
                todosLosDatos[3] = Float.parseFloat(datosTiempoRealList.get(i).getVoltaje4());


                for (int j=0; j<todosLosDatos.length;j++){
                    if (j<3) {
                        if (todosLosDatos[j] > valorMaximo1) {
                            valorMaximo1 = todosLosDatos[j];
                        }

                        if (valorMinimo1 == 0) {
                            valorMinimo1 = todosLosDatos[j];
                        }
                        if (todosLosDatos[j] < valorMinimo1) {
                            valorMinimo1 = todosLosDatos[j];
                        }
                    }else {

                        if (todosLosDatos[j] > valorMaximo2) {
                            valorMaximo2 = todosLosDatos[j];
                        }

                        if (valorMinimo2 == 0) {
                            valorMinimo2 = todosLosDatos[j];
                        }
                        if (todosLosDatos[j] < valorMinimo2) {
                            valorMinimo2 = todosLosDatos[j];
                        }

                    }

                }

            }catch (Exception ignore){

            }

            for (int j=0; j<todosLosDatos.length;j++){
                entries[j].add(new Entry(i,todosLosDatos[j]));
            }


            todosLosDatos = new float[4];
        }


        if (entries[0].size()>0) {


            LineDataSet tmpLineDataSet;
            for (int i=0;i<entries.length;i++){
                int numVoltaje = i +1;
                tmpLineDataSet = new LineDataSet(entries[i], "I"+numVoltaje);
                tmpLineDataSet.setColor(colorGrafica[i]);
                tmpLineDataSet.setValueTextColor(colorGrafica[i]);
                tmpLineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                tmpLineDataSet.setLineWidth(1.5f);
                tmpLineDataSet.setCircleColors(colorGrafica[i]);
                tmpLineDataSet.setDrawCircles(true);
                if (i<3){
                    tmpLineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                }else {
                    tmpLineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);

                }
                dataSets.add(tmpLineDataSet);

            }

            LineData data = new LineData(dataSets);
            data.setDrawValues(false);
            Description description = new Description();
            tiempoRealVoltaje.setData(data);
            description.setText(" ");
            xAxis = tiempoRealVoltaje.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsChart));
            xAxis.setLabelRotationAngle(-10f);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            YAxis yAxisLeft = tiempoRealVoltaje.getAxisLeft();
            YAxis yAxisRight = tiempoRealVoltaje.getAxisRight();

            if (valorMinimo1 > 10) {
                valorMinimo1= (float) (valorMinimo1*0.995);
            }

            if (valorMinimo2 > 10) {
                valorMinimo2= (float) (valorMinimo2*0.995);
            }

            yAxisLeft.setAxisMaximum((float) (valorMaximo1*1.005));
            yAxisRight.setAxisMaximum((float) (valorMaximo2*1.005));
            yAxisLeft.setAxisMinimum(valorMinimo1);
            yAxisRight.setAxisMinimum(valorMinimo2);
            valorMaximo1 = 0;
            valorMaximo2 = 0;
            valorMinimo1 = 0;
            valorMinimo2 = 0;

            tiempoRealVoltaje.setDescription(description);
            tiempoRealVoltaje.setDrawMarkers(true);
            CustomMarkerViewDataA customMarkerView = new CustomMarkerViewDataA(getContext(),R.layout.item_custom_marker,labelsChart, colorGrafica);
            customMarkerView.setSizeList(labelsChart.size());
            tiempoRealVoltaje.setMarker(customMarkerView);
            tiempoRealVoltaje.setTouchEnabled(true);
            tiempoRealVoltaje.setVisibility(View.VISIBLE);
            tiempoRealVoltaje.invalidate();

        }
    }

    private void cleanEntries(){
        for (int i=0; i<entries.length; i++){
            entries[i].clear();
        }
    }

    private void inputValuesRealTime() {

        cleanEntries();
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

        float [] todosLosDatos = new float[4];
        for (int i=0; i<datosTiempoRealList.size();i++){
            labelsChart.add(datosTiempoRealList.get(i).getHora());
            try {

                todosLosDatos[0] = Float.parseFloat(datosTiempoRealList.get(i).getVoltaje1());
                todosLosDatos[1] = Float.parseFloat(datosTiempoRealList.get(i).getVoltaje2());
                todosLosDatos[2] = Float.parseFloat(datosTiempoRealList.get(i).getVoltaje3());
                todosLosDatos[3] = Float.parseFloat(datosTiempoRealList.get(i).getVoltaje4());


                for (int j=0; j<todosLosDatos.length;j++){
                    if (j<3) {
                        if (todosLosDatos[j] > valorMaximo1) {
                            valorMaximo1 = todosLosDatos[j];
                        }

                        if (valorMinimo1 == 0) {
                            valorMinimo1 = todosLosDatos[j];
                        }
                        if (todosLosDatos[j] < valorMinimo1) {
                            valorMinimo1 = todosLosDatos[j];
                        }
                    }else {

                        if (todosLosDatos[j] > valorMaximo2) {
                            valorMaximo2 = todosLosDatos[j];
                        }

                        if (valorMinimo2 == 0) {
                            valorMinimo2 = todosLosDatos[j];
                        }
                        if (todosLosDatos[j] < valorMinimo2) {
                            valorMinimo2 = todosLosDatos[j];
                        }

                    }

                }

            }catch (Exception ignore){

            }

            for (int j=0; j<todosLosDatos.length;j++){
                entries[j].add(new Entry(i,todosLosDatos[j]));
            }


            todosLosDatos = new float[4];
        }

        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsChart));

        YAxis yAxisLeft = tiempoRealVoltaje.getAxisLeft();
        YAxis yAxisRight = tiempoRealVoltaje.getAxisRight();


        if (valorMinimo1 > 10) {
            valorMinimo1= (float) (valorMinimo1*0.995);
        }

        if (valorMinimo2 > 10) {
            valorMinimo2= (float) (valorMinimo2*0.995);
        }

        yAxisLeft.setAxisMaximum((float) (valorMaximo1*1.005));
        yAxisRight.setAxisMaximum((float) (valorMaximo2*1.005));
        yAxisLeft.setAxisMinimum(valorMinimo1);
        yAxisRight.setAxisMinimum(valorMinimo2);
        valorMaximo1 = 0;
        valorMaximo2 = 0;
        valorMinimo1 = 0;
        valorMinimo2 = 0;


        if (entries[0].size()>0){
            tiempoRealVoltaje.notifyDataSetChanged();
            tiempoRealVoltaje.invalidate();
            tiempoRealVoltaje.setVisibility(View.VISIBLE);
        }


    }


}
