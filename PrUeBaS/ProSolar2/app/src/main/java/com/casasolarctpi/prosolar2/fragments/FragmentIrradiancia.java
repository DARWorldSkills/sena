package com.casasolarctpi.prosolar2.fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.casasolarctpi.prosolar2.R;
import com.casasolarctpi.prosolar2.controllers.MainActivity;
import com.casasolarctpi.prosolar2.models.CustomMarkerViewData1;
import com.casasolarctpi.prosolar2.models.DatosTiempoReal;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
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

import static com.casasolarctpi.prosolar2.R.color.colorPrimary;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentIrradiancia extends Fragment {

    View view;
    LineChart chart;
    List<DatosTiempoReal> datosTP = new ArrayList<>();
    List<Entry> entradas = new ArrayList<>();
    List<DatosTiempoReal> datosTiempoRealList = new ArrayList<>();
    private List<String> labelsChart = new ArrayList<>();
    float valorMaximo, valorMinimo;
    private XAxis xAxis;
    boolean obtenerPorPrimeraVez = false;
    private int colorGrafica;
    private String tipoDeDato;

    public FragmentIrradiancia() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_fragment_irradiancia, container, false);

        inicializar();
        ingresarValoresFirebase();
        return view;
    }

    private void inicializar() {
        chart = view.findViewById(R.id.tiempoRealIrra);
        colorGrafica = getResources().getColor(R.color.colorGraficaLinea3);
    }

    private void ingresarValoresFirebase() {

        DatabaseReference datosTipoReal = MainActivity.reference.child("datos").child("datosRT");

        //QUERY PARA LIMITAR LOS DATOS
        datosTipoReal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<DatosTiempoReal>> t = new GenericTypeIndicator<ArrayList<DatosTiempoReal>>() {
                };
                datosTiempoRealList = dataSnapshot.getValue(t);
                if (!obtenerPorPrimeraVez) {
                    inputValuesChart();
                    obtenerPorPrimeraVez = true;
                } else {
                    if (entradas.size() > 0) {
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
        final Date[] date1 = {new Date(), new Date()};
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Collections.sort(datosTiempoRealList, new Comparator<DatosTiempoReal>() {
            @Override
            public int compare(DatosTiempoReal o1, DatosTiempoReal o2) {
                try {
                    date1[0] = dateFormat.parse(o1.getFechaActual1());
                    date1[1] = dateFormat.parse(o2.getFechaActual1());
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

        for (int i = 0; i < datosTiempoRealList.size(); i++) {
            labelsChart.add(datosTiempoRealList.get(i).getHora());
            float dato = 0;
            try {
                dato = Float.parseFloat(datosTiempoRealList.get(i).getIrradiancia());
                if (dato > valorMaximo) {
                    valorMaximo = dato;
                }

                if (valorMinimo == 0) {
                    valorMinimo = dato;
                }
                if (dato < valorMinimo) {
                    valorMinimo = dato;
                }
            } catch (Exception ignored) {


            }

            entradas.add(new Entry(i, dato));
        }

        if (entradas.size() > 0) {
            LineDataSet lineDataSet = new LineDataSet(entradas, tipoDeDato);
            lineDataSet.setColor(colorGrafica);
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            lineDataSet.setDrawCircles(true);
            lineDataSet.setCircleColors(colorGrafica);
            lineDataSet.setCircleColor(colorGrafica);
            LineData data = new LineData(lineDataSet);
            data.setDrawValues(false);
            Description description = new Description();
            chart.setData(data);
            description.setText(getString(R.string.fecha_datos_tomados) + " " + datosTiempoRealList.get(0).getFechaActual());
            xAxis = chart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsChart));
            xAxis.setLabelRotationAngle(-10f);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            YAxis yAxisLeft = chart.getAxisLeft();
            YAxis yAxisRight = chart.getAxisRight();

            if (valorMinimo > 10) {
                valorMinimo -= 5f;
            }


            yAxisLeft.setAxisMaximum(valorMaximo + 5f);
            yAxisRight.setAxisMaximum(valorMaximo + 5f);
            yAxisLeft.setAxisMinimum(valorMinimo);
            yAxisRight.setAxisMinimum(valorMinimo);
            valorMaximo = 0;
            valorMinimo = 0;

            chart.setDescription(description);
            chart.setDrawMarkers(true);
            CustomMarkerViewData1 customMarkerView = new CustomMarkerViewData1(getContext(), R.layout.item_custom_marker, labelsChart);
            customMarkerView.setTipoDelDato(tipoDeDato);
            customMarkerView.setSizeList(labelsChart.size());
            customMarkerView.setColorDelDato(colorGrafica);
            chart.setMarker(customMarkerView);
            chart.setTouchEnabled(true);
            chart.setVisibility(View.VISIBLE);
            chart.invalidate();

        }

    }


    private void inputValuesRealTime() {

        entradas.clear();
        labelsChart.clear();

        final Date[] date1 = {new Date(), new Date()};
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Collections.sort(datosTiempoRealList, new Comparator<DatosTiempoReal>() {
            @Override
            public int compare(DatosTiempoReal o1, DatosTiempoReal o2) {
                try {
                    date1[0] = dateFormat.parse(o1.getFechaActual1());
                    date1[1] = dateFormat.parse(o2.getFechaActual1());
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


        for (int i = 0; i < datosTiempoRealList.size(); i++) {
            labelsChart.add(datosTiempoRealList.get(i).getHora());
            float dato = 0;
            try {
                dato = Float.parseFloat(datosTiempoRealList.get(i).getIrradiancia());
                if (dato > valorMaximo) {
                    valorMaximo = dato;
                }

                if (valorMinimo == 0) {
                    valorMinimo = dato;
                }
                if (dato < valorMinimo) {
                    valorMinimo = dato;
                }
            } catch (Exception ignored) {

            }

            entradas.add(new Entry(i, dato));

        }

        if (entradas.size() > 0) {
            chart.notifyDataSetChanged();
            chart.invalidate();
            chart.setVisibility(View.VISIBLE);
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsChart));

        YAxis yAxisLeft = chart.getAxisLeft();
        YAxis yAxisRight = chart.getAxisRight();

        if (valorMinimo > 10) {
            valorMinimo -= 5f;
        }


        yAxisLeft.setAxisMaximum(valorMaximo + 5f);
        yAxisRight.setAxisMaximum(valorMaximo + 5f);
        yAxisLeft.setAxisMinimum(valorMinimo);
        yAxisRight.setAxisMinimum(valorMinimo);
        valorMaximo = 0;
        valorMinimo = 0;
    }


}
