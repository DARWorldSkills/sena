package com.casasolarctpi.prosolar2.fragments;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.casasolarctpi.prosolar2.R;
import com.casasolarctpi.prosolar2.controllers.MainActivity;
import com.casasolarctpi.prosolar2.models.Constants;
import com.casasolarctpi.prosolar2.models.CustomMarkerViewData2;
import com.casasolarctpi.prosolar2.models.CustomMarkerViewDataMonth;
import com.casasolarctpi.prosolar2.models.DatosGuardados;
import com.casasolarctpi.prosolar2.models.DatosPromedio;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentConsultas extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    Button btnConsulta1, btnConsulta2, btnConsulta3;
    BarChart barChart1, barChart2;
    LineChart lineChart1;
    TextView txtDate1, txtDate2;
    MaterialSpinner mSMes;
    NumberPicker nPAnio;
    Date dateToQuery;
    String fechaATexto;
    Dialog dialog;
    ProgressBar pBConsultas;
    int mode;
    private List<ILineDataSet> dataSets = new ArrayList<>();
    private XAxis xAxis;
    public static List<String> labelsChart = new ArrayList<>();
    final List<DatosGuardados>[] datosCompletosSemana = new List[7];
    List<DatosGuardados>[] datosCompletosMes = new List[31];
    List<BarEntry> entriesBarWeek = new ArrayList<>();
    List<BarEntry> entriesBarWeek1 = new ArrayList<>();
    List<BarEntry> entriesBarWeek2 = new ArrayList<>();
    List<BarEntry> entriesBarWeek3 = new ArrayList<>();
    List<BarEntry> entriesBarWeek4 = new ArrayList<>();
    int month, yearM, numDias;
    public static int modoGraficar = 0;
    TextView txtTituloGrafica1, txtTituloGrafica2, txtTituloGrafica3;
    String datoInfo1;
    String datoInfo2;
    String datoInfo3;
    String datoInfo4;
    String datoInfo5;


    int colorDato1, colorDato2, colorDato3,colorDato4, colorDato5, colorDatoTexto1, colorDatoTexto2;
    float yAxisMax1, yAxisMin1, yAxisMax2, yAxisMin2, yAxisMax3, yAxisMin3, yAxisMax4, yAxisMin4, yAxisMax5, yAxisMin5;
    float yAxisMaxS1, yAxisMinS1, yAxisMaxS2, yAxisMinS2;
    View view;
    TabHost tabHost;

    public FragmentConsultas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_fragment_consultas, container, false);


        TabHost tabHost = view.findViewById(R.id.tabHost);
        setCustomTabHost(tabHost);
        inicializar();
        ingresarValoresSpinner();
        ingresarValoresChart();


        return view;
    }

    private void ingresarValoresSpinner() {

        mSMes.setItems(Constants.MESES);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        nPAnio.setMinValue(Constants.MIN_YEAR);
        nPAnio.setMaxValue(Constants.MAX_YEAR);
        nPAnio.setValue(year);

    }

    private void setCustomTabHost(TabHost tabHost) {
        TabWidget tabWidget = tabHost.getTabWidget();
    }

    private void ingresarValoresChart() {

        switch (modoGraficar){
            case 0:

                datoInfo1 = getResources().getString(R.string.dato3);
                datoInfo2 = getResources().getString(R.string.dato1);

                colorDato1 = getResources().getColor(R.color.colorGraficaPunto3);
                colorDato2 = getResources().getColor(R.color.colorGraficaPunto1);

                colorDatoTexto1 = getResources().getColor(R.color.colorGraficaLinea3);
                colorDatoTexto2 = getResources().getColor(R.color.colorGraficaLinea1);

                txtTituloGrafica1.setText(R.string.titulo_irradiancia_humedad);
                txtTituloGrafica2.setText(R.string.titulo_irradiancia_humedad);
                txtTituloGrafica3.setText(R.string.titulo_irradiancia_humedad);

                break;

            case 1:

                datoInfo1 = getResources().getString(R.string.dato3);
                datoInfo2 = getResources().getString(R.string.dato4);

                colorDato1 = getResources().getColor(R.color.colorGraficaPunto3);
                colorDato2 = getResources().getColor(R.color.colorGraficaI1);
                colorDato3 = getResources().getColor(R.color.colorGraficaI2);
                colorDato4 = getResources().getColor(R.color.colorGraficaI3);
                colorDato5 = getResources().getColor(R.color.colorGraficaI4);

                colorDatoTexto1 = getResources().getColor(R.color.colorGraficaLinea3);
                colorDatoTexto2 = getResources().getColor(R.color.colorGraficaLinea5);

                txtTituloGrafica1.setText(R.string.titulo_irradiancia_corriente);
                txtTituloGrafica2.setText(R.string.titulo_irradiancia_corriente);
                txtTituloGrafica3.setText(R.string.titulo_irradiancia_corriente);


                break;

            case 2:
                datoInfo1 = getResources().getString(R.string.dato3);
                datoInfo2 = getResources().getString(R.string.dato5);

                colorDato1 = getResources().getColor(R.color.colorGraficaPunto3);
                colorDato2 = getResources().getColor(R.color.colorGraficaPunto5);

                colorDatoTexto1 = getResources().getColor(R.color.colorGraficaLinea3);
                colorDatoTexto2 = getResources().getColor(R.color.colorGraficaLinea5);

                colorDato1 = getResources().getColor(R.color.colorGraficaPunto3);
                colorDato2 = getResources().getColor(R.color.colorGraficaI1);
                colorDato3 = getResources().getColor(R.color.colorGraficaI2);
                colorDato4 = getResources().getColor(R.color.colorGraficaI3);
                colorDato5 = getResources().getColor(R.color.colorGraficaI4);


                txtTituloGrafica1.setText(R.string.titulo_irradiancia_voltaje);
                txtTituloGrafica2.setText(R.string.titulo_irradiancia_voltaje);
                txtTituloGrafica3.setText(R.string.titulo_irradiancia_voltaje);



                break;


            case 3:

                datoInfo1 = getResources().getString(R.string.dato1);
                datoInfo2 = getResources().getString(R.string.dato2);

                colorDato1 = getResources().getColor(R.color.colorGraficaLinea5);
                colorDato2 = getResources().getColor(R.color.colorCoral);

                colorDatoTexto1 = getResources().getColor(R.color.colorGraficaLinea1);
                colorDatoTexto2 = getResources().getColor(R.color.colorGraficaLinea2);

                txtTituloGrafica1.setText(R.string.titulo_humedad_temperatura);
                txtTituloGrafica2.setText(R.string.titulo_humedad_temperatura);
                txtTituloGrafica3.setText(R.string.titulo_humedad_temperatura);


                break;
        }
    }

    private void inicializar() {

        TabHost tabHost = view.findViewById(R.id.tabHost);
        tabHost.setup();

        //Tab 1
        TabHost.TabSpec spec = tabHost.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator(createTabView(getContext(),getResources().getString(R.string.dia)));
        tabHost.addTab(spec);

        //Tab 2
        spec = tabHost.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator(createTabView(getContext(),getResources().getString(R.string.semana)));
        tabHost.addTab(spec);

        //Tab 3
        spec = tabHost.newTabSpec("Tab Three");
        spec.setContent(R.id.tab3);
        spec.setIndicator(createTabView(getContext(),getResources().getString(R.string.mes)));
        tabHost.addTab(spec);

        btnConsulta1 = view.findViewById(R.id.btnConsulta1);
        btnConsulta2 = view.findViewById(R.id.btnConsulta2);
        btnConsulta3 = view.findViewById(R.id.btnConsulta3);

        btnConsulta1.setOnClickListener(this);
        btnConsulta2.setOnClickListener(this);
        btnConsulta3.setOnClickListener(this);

        lineChart1 = view.findViewById(R.id.lineChart1);
        barChart1 = view.findViewById(R.id.barChart1);
        barChart2 = view.findViewById(R.id.barChart2);

        lineChart1.setVisibility(INVISIBLE);
        barChart1.setVisibility(INVISIBLE);
        barChart2.setVisibility(INVISIBLE);

        txtDate1 = view.findViewById(R.id.txtConsulta1);
        txtDate2 = view.findViewById(R.id.txtConsulta2);

        mSMes = view.findViewById(R.id.spinnerMes);
        nPAnio = view.findViewById(R.id.nPAnio);

        txtTituloGrafica1 = view.findViewById(R.id.txtTituloChart);
        txtTituloGrafica2 = view.findViewById(R.id.txtTituloChart1);
        txtTituloGrafica3 = view.findViewById(R.id.txtTituloChart2);


        txtTituloGrafica1.setVisibility(INVISIBLE);
        txtTituloGrafica2.setVisibility(INVISIBLE);
        txtTituloGrafica3.setVisibility(INVISIBLE);

        pBConsultas = view.findViewById(R.id.pBConsultas);

    }

    private static View createTabView(final Context context, final String text) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_bg, null);
        TextView tv = view.findViewById(R.id.tabsText);
        tv.setText(text);
        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnConsulta1:
                mode=1;
                showDatePickerDialog();
                break;

            case R.id.btnConsulta2:
                mode=2;
                showDatePickerWeekDialog();
                break;


            case R.id.btnConsulta3:
                txtTituloGrafica3.setVisibility(INVISIBLE);
                mode=3;
                getDataMonth();
                btnConsulta3.setEnabled(false);
                break;
        }

    }

    private void getDataMonth() {

        pBConsultas.setVisibility(VISIBLE);
        barChart2.setVisibility(INVISIBLE);
        yearM = nPAnio.getValue();
        month = mSMes.getSelectedIndex();
        switch(month){
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                numDias=31;
                break;
            case 3:
            case 5:
            case 8:
            case 10:
                numDias=30;
                break;
            case 1:
                if ( ((yearM%100 == 0) && (yearM%400 == 0)) ||
                        ((yearM%100 != 0) && (yearM%  4 == 0))   )
                    numDias=29;
                else
                    numDias=28;
            default:
        }

        int realMonth= month+1;
        datosCompletosMes = new List[numDias];

        getDataToFirebaseForMonth(yearM, realMonth);


    }

    private void getDataToFirebaseForMonth(int year, int month) {

        DatabaseReference dbrMonth = MainActivity.reference.child("datos").child("datosAcum").child("y"+year).child("m"+month);
        dbrMonth.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@androidx.annotation.NonNull DataSnapshot dataSnapshot) {
                int tmpIndex;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    try {
                        tmpIndex = Integer.parseInt(Objects.requireNonNull(postSnapshot.getKey()).substring(1));
                        GenericTypeIndicator<ArrayList<DatosGuardados>> t = new GenericTypeIndicator<ArrayList<DatosGuardados>>() {};
                        datosCompletosMes[tmpIndex-1] = postSnapshot.getValue(t);
                    }catch (Exception e){
                    }

                }
                try {
                    showChartMonth();

                }catch (Exception e){
                    try {
                        Toast.makeText(getContext(), R.string.no_hay_datos_disponibles, Toast.LENGTH_SHORT).show();
                    }catch (Exception ignored){

                    }
                    btnConsulta3.setEnabled(true);
                    pBConsultas.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onCancelled(@androidx.annotation.NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "No hay conexión a internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showChartMonth() {

        List<BarEntry> entry1 = new ArrayList<>();
        List<BarEntry> entry2 = new ArrayList<>();
        List<BarEntry> entry3 = new ArrayList<>();
        List<BarEntry> entry4 = new ArrayList<>();
        List<BarEntry> entry5 = new ArrayList<>();
        barChart2.clearAnimation();
        barChart2.clear();
        List<String> labelC = new ArrayList<>();
        XAxis xAxis1;
        labelC.add(" ");
        float tmpValue = 0;
        float tmpValue2 = 0;
        float tmpValue3 = 0;
        float tmpValue4 = 0;
        float tmpValue5 = 0;
        for (int i=0; i<datosCompletosMes.length;i++) {
            DatosPromedio datosPromedio = promedioDia(datosCompletosMes[i]);
            switch (modoGraficar) {
                case 0:
                    tmpValue = datosPromedio.getIrradianciaPromedio();
                    tmpValue2 = datosPromedio.getHumedadPromedio();
                    break;
                case 1:
                    tmpValue = datosPromedio.getIrradianciaPromedio();
                    tmpValue2 = datosPromedio.getCorriente1Promedio();
                    tmpValue3 = datosPromedio.getCorriente2Promedio();
                    tmpValue4 = datosPromedio.getCorriente3Promedio();
                    tmpValue5 = datosPromedio.getCorriente4Promedio();

                    break;
                case 2:
                    tmpValue = datosPromedio.getIrradianciaPromedio();
                    tmpValue2 = datosPromedio.getVoltaje1Promedio();
                    tmpValue3 = datosPromedio.getVoltaje2Promedio();
                    tmpValue4 = datosPromedio.getVoltaje3Promedio();
                    tmpValue5 = datosPromedio.getVoltaje4Promedio();
                    break;
                case 3:
                    tmpValue = datosPromedio.getHumedadPromedio();
                    tmpValue2 = datosPromedio.getTemperaturaPromedio();
                    break;

            }


            if (tmpValue > yAxisMax1) {
                yAxisMax1 = tmpValue;
            }
            if (yAxisMin1 == 0) {
                yAxisMin1 = tmpValue;
            }
            if (tmpValue < yAxisMin1) {
                yAxisMin1 = tmpValue;
            }

            if (tmpValue2 > yAxisMax2) {
                yAxisMax2 = tmpValue2;
            }
            if (yAxisMin2 == 0) {
                yAxisMin2 = tmpValue2;
            }
            if (tmpValue2 < yAxisMin2) {
                yAxisMin2 = tmpValue2;
            }

            if (tmpValue3 > yAxisMax3) {
                yAxisMax3 = tmpValue3;
            }
            if (yAxisMin3 == 0) {
                yAxisMin3 = tmpValue3;
            }
            if (tmpValue3 < yAxisMin3) {
                yAxisMin3 = tmpValue3;
            }

            if (tmpValue4 > yAxisMax4) {
                yAxisMax4 = tmpValue4;
            }
            if (yAxisMin4 == 0) {
                yAxisMin4 = tmpValue4;
            }
            if (tmpValue4 < yAxisMin4) {
                yAxisMin4 = tmpValue4;
            }

            if (tmpValue5 > yAxisMax5) {
                yAxisMax5 = tmpValue5;
            }
            if (yAxisMin5 == 0) {
                yAxisMin5 = tmpValue5;
            }
            if (tmpValue5 < yAxisMin5) {
                yAxisMin5 = tmpValue5;
            }


            entry1.add(new BarEntry(i + 1, tmpValue));
            entry2.add(new BarEntry(i + 1, tmpValue2));
            entry3.add(new BarEntry(i + 1, tmpValue3));
            entry4.add(new BarEntry(i + 1, tmpValue4));
            entry5.add(new BarEntry(i + 1, tmpValue5));

            labelC.add(Integer.toString(i + 1));

            if (i<=datosCompletosMes.length){
                barChart2.notifyDataSetChanged();
                barChart2.invalidate();
            }
        }
        for (int i=0; i< numDias+1; i++){
            labelC.add(i,Integer.toString(i+1));

        }


        if (entry1.size()!=0) {

            txtTituloGrafica3.setVisibility(VISIBLE);

            BarDataSet barDataSet = new BarDataSet(entry1,datoInfo1);
            BarDataSet barDataSet1 = new BarDataSet(entry2,datoInfo2);
            BarDataSet barDataSet3 = new BarDataSet(entry3,datoInfo3);
            BarDataSet barDataSet4 = new BarDataSet(entry4,datoInfo4);
            BarDataSet barDataSet5 = new BarDataSet(entry5,datoInfo5);

            barDataSet.setColor(colorDato1);
            barDataSet1.setColor(colorDato2);
            barDataSet3.setColor(colorDato3);
            barDataSet4.setColor(colorDato4);
            barDataSet5.setColor(colorDato5);

            barDataSet.setDrawValues(false);
            barDataSet1.setDrawValues(false);
            barDataSet3.setDrawValues(false);
            barDataSet4.setDrawValues(false);
            barDataSet5.setDrawValues(false);

            barDataSet.setValueTextColor(colorDatoTexto1);
            barDataSet1.setValueTextColor(colorDatoTexto2);
            barDataSet3.setValueTextColor(colorDato3);
            barDataSet4.setValueTextColor(colorDato4);
            barDataSet5.setValueTextColor(colorDato5);


            barDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
            barDataSet1.setAxisDependency(YAxis.AxisDependency.RIGHT);
            barDataSet3.setAxisDependency(YAxis.AxisDependency.RIGHT);
            barDataSet4.setAxisDependency(YAxis.AxisDependency.RIGHT);
            barDataSet5.setAxisDependency(YAxis.AxisDependency.RIGHT);

            List<IBarDataSet> dataBarSets = new ArrayList<>();
            dataBarSets.add(barDataSet);
            dataBarSets.add(barDataSet1);
            dataBarSets.add(barDataSet3);
            dataBarSets.add(barDataSet4);
            dataBarSets.add(barDataSet5);
            BarData data = new BarData(barDataSet,barDataSet1, barDataSet3, barDataSet4, barDataSet5);

            barChart2.setData(data);
            Description description = new Description();
            description.setText(" ");
            xAxis1 = barChart2.getXAxis();
            xAxis1.setCenterAxisLabels(true);
            xAxis1.setLabelRotationAngle(-10f);
            xAxis1.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis1.setValueFormatter(new IndexAxisValueFormatter(labelC));
            xAxis1.setAxisMaximum(datosCompletosMes.length);
            xAxis1.setAxisMinimum(0);
            xAxis1.setGranularity(1f);
            xAxis1.setCenterAxisLabels(true);

            YAxis yAxisLeft = barChart2.getAxisLeft();
            YAxis yAxisRight = barChart2.getAxisRight();

            float tmpYAxisMax1= (float) (yAxisMax1*1.014);
            float tmpYAxisMax2= (float) (yAxisMax2*1.014);
            yAxisLeft.setAxisMaximum(tmpYAxisMax1);
            yAxisLeft.setAxisMinimum(0);
            yAxisRight.setAxisMaximum(tmpYAxisMax2);
            yAxisRight.setAxisMinimum(0);

            barChart2.getBarData().setBarWidth(0.46f);
            barChart2.getXAxis().setAxisMinValue(0);
            barChart2.groupBars(0, 0.08f, 0f);

            barChart2.setDescription(description);
            barChart2.setTouchEnabled(true);
            barChart2.setVisibility(VISIBLE);
            barChart2.setMarker(new CustomMarkerViewDataMonth(getContext(),R.layout.item_custom_marker,labelC,datoInfo1,datoInfo2,colorDato1,colorDato2));
            barChart2.highlightValue(null);

            barChart2.invalidate();


        }else {
            Toast.makeText(getContext(), R.string.no_hay_datos, Toast.LENGTH_SHORT).show();
        }
        btnConsulta3.setEnabled(true);
        pBConsultas.setVisibility(View.INVISIBLE);

    }

    private void showDatePickerWeekDialog() {

        Toast.makeText(getContext(), R.string.mensaje_week, Toast.LENGTH_SHORT).show();
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.item_select_week);
        final DatePicker datePicker = dialog.findViewById(R.id.calendarioWeek);
        Button btnAceptar = dialog.findViewById(R.id.btnAceptar);
        final Button btnCancelar = dialog.findViewById(R.id.btnCancelar);


        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                btnConsulta2.setEnabled(false);
                pBConsultas.setVisibility(VISIBLE);
                int mes = datePicker.getMonth()+1;
                txtTituloGrafica2.setVisibility(INVISIBLE);
                String fecha1 = datePicker.getDayOfMonth()+"-"+mes+"-"+datePicker.getYear();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                Date date = new Date();
                try {
                    date = dateFormat.parse(fecha1);
                } catch (ParseException e) {
                    e.printStackTrace();

                }

                Calendar calendar = new GregorianCalendar(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());
                Calendar calendar1 = new GregorianCalendar(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());

                calendar.setTime(date);


                calendar.setFirstDayOfWeek(Calendar.SUNDAY);
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                calendar.set(Calendar.AM_PM, Calendar.AM);
                calendar.set(Calendar.HOUR, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);


                calendar1.setTime(date);
                calendar1.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                calendar1.set(Calendar.AM_PM, Calendar.PM);
                calendar1.set(Calendar.HOUR, 11);
                calendar1.set(Calendar.MINUTE, 59);
                calendar1.set(Calendar.SECOND, 59);

                Date primerDia = calendar.getTime();
                Date ultimoDia = calendar1.getTime();

                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

                txtDate2.setText(getString(R.string.fecha)+": "+format.format(primerDia)+" "+getResources().getString(R.string.a)+" "+format.format(ultimoDia));

                searchDate(primerDia);
                dialog.cancel();

            }
        });



        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                lockDeviceRotation(false);
            }
        });
        lockDeviceRotation(true);
        dialog.show();


    }

    private void searchDate(Date primerDia) {

        Calendar tmpCalendar = new GregorianCalendar();
        tmpCalendar.setTime(primerDia);
        Date tmpDate = tmpCalendar.getTime();
        int realMonth;
        int dia;
        int anio;

        for (int i=0; i<7;i++){
            realMonth=tmpDate.getMonth()+1;
            dia = tmpCalendar.get(Calendar.DAY_OF_MONTH);
            anio = tmpCalendar.get(Calendar.YEAR);
            getDataDayOFFireBaseWeek(anio,realMonth,dia,i);
            tmpCalendar.add(Calendar.DAY_OF_MONTH,1);
            tmpDate=tmpCalendar.getTime();

        }
        
    }

    private void getDataDayOFFireBaseWeek(int year, int month, int dayOfMonth,final int contador) {

        DatabaseReference datosDia = MainActivity.reference.child("datos").child("datosAcum").child("y"+year).child("m"+month).child("d"+dayOfMonth);
        datosDia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<DatosGuardados>> t = new GenericTypeIndicator<ArrayList<DatosGuardados>>() {};
                datosCompletosSemana[contador] = dataSnapshot.getValue(t);
                if (contador==6){
                    showChartWeek();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void showChartWeek() {

        entriesBarWeek = new ArrayList<>();
        entriesBarWeek1 = new ArrayList<>();
        barChart1.clearAnimation();
        barChart1.clear();

        float tmpValue = 0;
        float tmpValue2 = 0;
        float tmpValue3 = 0;
        float tmpValue4 = 0;
        float tmpValue5 = 0;

        for (int i=0; i<7;i++){
            DatosPromedio datosPromedio = promedioDia(datosCompletosSemana[i]);
            switch (modoGraficar){
                case 0:
                    tmpValue = datosPromedio.getIrradianciaPromedio();
                    tmpValue2 = datosPromedio.getHumedadPromedio();
                    break;
                case 1:
                    tmpValue = datosPromedio.getIrradianciaPromedio();
                    tmpValue2 = datosPromedio.getCorriente1Promedio();
                    tmpValue3 = datosPromedio.getCorriente2Promedio();
                    tmpValue4 = datosPromedio.getCorriente3Promedio();
                    tmpValue5 = datosPromedio.getCorriente4Promedio();
                    break;
                case 2:
                    tmpValue = datosPromedio.getIrradianciaPromedio();
                    tmpValue2 = datosPromedio.getVoltaje1Promedio();
                    tmpValue3 = datosPromedio.getVoltaje2Promedio();
                    tmpValue4 = datosPromedio.getVoltaje3Promedio();
                    tmpValue5 = datosPromedio.getVoltaje4Promedio();

                    break;
                case 3:
                    tmpValue = datosPromedio.getHumedadPromedio();
                    tmpValue2 = datosPromedio.getTemperaturaPromedio();
                    break;

            }
            if (tmpValue > yAxisMaxS1) {
                yAxisMaxS1 = tmpValue;
            }

            if (yAxisMinS1 == 0) {
                yAxisMinS1 = tmpValue;
            }
            if (tmpValue < yAxisMinS1) {
                yAxisMinS1 = tmpValue;
            }

            if (tmpValue2 > yAxisMaxS2) {
                yAxisMaxS2 = tmpValue2;
            }

            if (yAxisMinS2 == 0) {
                yAxisMinS2 = tmpValue2;
            }
            if (tmpValue2 < yAxisMinS2) {
                yAxisMinS2 = tmpValue2;
            }

            if (tmpValue3 > yAxisMaxS2) {
                yAxisMaxS2 = tmpValue3;
            }

            if (yAxisMinS2 == 0) {
                yAxisMinS2 = tmpValue3;
            }
            if (tmpValue3 < yAxisMinS2) {
                yAxisMinS2 = tmpValue3;
            }

            if (tmpValue4 > yAxisMaxS2) {
                yAxisMaxS2 = tmpValue4;
            }

            if (yAxisMinS2 == 0) {
                yAxisMinS2 = tmpValue4;
            }
            if (tmpValue4 < yAxisMinS2) {
                yAxisMinS2 = tmpValue4;
            }
            if (tmpValue5 > yAxisMaxS2) {
                yAxisMaxS2 = tmpValue5;
            }

            if (yAxisMinS2 == 0) {
                yAxisMinS2 = tmpValue5;
            }
            if (tmpValue5 < yAxisMinS2) {
                yAxisMinS2 = tmpValue5;
            }

            entriesBarWeek.add(new BarEntry(i,tmpValue));
            entriesBarWeek1.add(new BarEntry(i,tmpValue2));
            entriesBarWeek2.add(new BarEntry(i,tmpValue3));
            entriesBarWeek3.add(new BarEntry(i,tmpValue4));
            entriesBarWeek4.add(new BarEntry(i,tmpValue5));

        }


        if(entriesBarWeek1.size()>0){
            txtTituloGrafica2.setVisibility(VISIBLE);
        }


        BarDataSet barDataSet = new BarDataSet(entriesBarWeek,datoInfo1);
        BarDataSet barDataSet1 = new BarDataSet(entriesBarWeek1,datoInfo2);
        BarDataSet barDataSet2 = new BarDataSet(entriesBarWeek2,datoInfo3);
        BarDataSet barDataSet3 = new BarDataSet(entriesBarWeek3,datoInfo4);
        BarDataSet barDataSet4 = new BarDataSet(entriesBarWeek4,datoInfo5);
        barDataSet.setColor(colorDato1);
        barDataSet1.setColor(colorDato2);
        barDataSet2.setColor(colorDato3);
        barDataSet3.setColor(colorDato4);
        barDataSet4.setColor(colorDato5);
        barDataSet.setBarShadowColor(colorDatoTexto1);
        barDataSet1.setBarShadowColor(colorDatoTexto2);
        barDataSet2.setBarShadowColor(colorDato3);
        barDataSet3.setBarShadowColor(colorDato4);
        barDataSet4.setBarShadowColor(colorDato5);

        final DecimalFormat decimalFormat = new DecimalFormat("####.##");
        barDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return decimalFormat.format(value);
            }
        });

        barDataSet1.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return decimalFormat.format(value);
            }
        });

        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        barDataSet1.setAxisDependency(YAxis.AxisDependency.RIGHT);

        List<IBarDataSet> dataBarSets = new ArrayList<>();
        dataBarSets.add(barDataSet);
        dataBarSets.add(barDataSet1);
        dataBarSets.add(barDataSet2);
        dataBarSets.add(barDataSet3);
        dataBarSets.add(barDataSet4);
        BarData data = new BarData(barDataSet,barDataSet1, barDataSet2, barDataSet3, barDataSet4);
        Description description = new Description();
        description.setText(" ");
        data.setBarWidth(0.48f); // set custom bar width
        barChart1.setData(data);
        barChart1.groupBars(0, 0.04f, 0f);
        xAxis = barChart1.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(Constants.DIAS_DE_LA_SEMANA));
        xAxis.setCenterAxisLabels(true);
        xAxis.setLabelRotationAngle(-5f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMaximum(7);



        YAxis yAxisLeft = barChart1.getAxisLeft();
        YAxis yAxisRight = barChart1.getAxisRight();

        float tmpYAxisMax1= (float) (yAxisMaxS1*1.014);
        float tmpYAxisMax2= (float) (yAxisMaxS2*1.014);
        yAxisLeft.setAxisMaximum(tmpYAxisMax1);
        yAxisLeft.setAxisMinimum(0);
        yAxisRight.setAxisMaximum(tmpYAxisMax2);
        yAxisRight.setAxisMinimum(0);

        barChart1.setVisibility(VISIBLE);
        barChart1.setDescription(description);
        pBConsultas.setVisibility(INVISIBLE);
        btnConsulta2.setEnabled(true);
        barChart1.invalidate(); // refresh

    }

    private DatosPromedio promedioDia(List<DatosGuardados> datosFiltrado) {

        DatosPromedio acumulador= new DatosPromedio();
        int  acmH = 0;
        int contador = 0;

        List<Float> irradianciaPorHoras = new ArrayList<>(1);
        float tmpAcumIrr =0 ;
        try {
            for (int i =0 ; i<datosFiltrado.size(); i++){
                DatosGuardados el1 = datosFiltrado.get(i);
                try {
                    tmpAcumIrr+=Float.parseFloat(el1.getIrradiancia());
                    acumulador.setHumedadPromedio(acumulador.getHumedadPromedio() + Float.parseFloat(el1.getHumedad()));
                    acumulador.setCorriente1Promedio(acumulador.getCorriente1Promedio() + Float.parseFloat(el1.getCorriente1()));
                    acumulador.setCorriente2Promedio(acumulador.getCorriente2Promedio() + Float.parseFloat(el1.getCorriente2()));
                    acumulador.setCorriente3Promedio(acumulador.getCorriente3Promedio() + Float.parseFloat(el1.getCorriente3()));
                    acumulador.setCorriente4Promedio(acumulador.getCorriente4Promedio() + Float.parseFloat(el1.getCorriente4()));
                    acumulador.setVoltaje1Promedio(acumulador.getVoltaje1Promedio() + Float.parseFloat(el1.getVoltaje1()));
                    acumulador.setVoltaje2Promedio(acumulador.getVoltaje2Promedio() + Float.parseFloat(el1.getVoltaje2()));
                    acumulador.setVoltaje3Promedio(acumulador.getVoltaje3Promedio() + Float.parseFloat(el1.getVoltaje3()));
                    acumulador.setVoltaje4Promedio(acumulador.getVoltaje4Promedio() + Float.parseFloat(el1.getVoltaje4()));
                    acumulador.setTemperaturaPromedio(acumulador.getTemperaturaPromedio() + Float.parseFloat(el1.getTemperatura()));
                    contador++;
                }catch (Exception ignore1) {

                }
                try {
                    Date horaDato;
                    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.US);
                    horaDato = timeFormat.parse(el1.getHora());
                    if (acmH == 0){
                        acmH = horaDato.getHours();
                    }
                    if (horaDato.getHours() == acmH){
                        tmpAcumIrr = tmpAcumIrr / contador;
                        irradianciaPorHoras.add(tmpAcumIrr);
                        tmpAcumIrr = 0;
                        acmH++;
                        contador = 0;
                    } else {
                        if (horaDato.getHours() - 1 > acmH || acmH ==0){
                            acmH = horaDato.getHours() + 1;
                        }
                    }

                } catch (Exception e) {

                }

            }

            for ( float element : irradianciaPorHoras ) {
                acumulador.setIrradianciaPromedio(element + acumulador.getIrradianciaPromedio());
            }
            acumulador.setHumedadPromedio(acumulador.getHumedadPromedio() / datosFiltrado.size());
            acumulador.setCorriente1Promedio(acumulador.getCorriente1Promedio() / datosFiltrado.size());
            acumulador.setCorriente2Promedio(acumulador.getCorriente2Promedio() / datosFiltrado.size());
            acumulador.setCorriente3Promedio(acumulador.getCorriente3Promedio() / datosFiltrado.size());
            acumulador.setCorriente4Promedio(acumulador.getCorriente4Promedio() / datosFiltrado.size());
            acumulador.setVoltaje1Promedio(acumulador.getVoltaje1Promedio() / datosFiltrado.size());
            acumulador.setVoltaje2Promedio(acumulador.getVoltaje2Promedio() / datosFiltrado.size());
            acumulador.setVoltaje3Promedio(acumulador.getVoltaje3Promedio() / datosFiltrado.size());
            acumulador.setVoltaje4Promedio(acumulador.getVoltaje4Promedio() / datosFiltrado.size());
            acumulador.setTemperaturaPromedio(acumulador.getTemperaturaPromedio() / datosFiltrado.size());

        }catch (Exception ignore){

        }


        return acumulador;
    }


    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                lockDeviceRotation(false);
            }
        });
        lockDeviceRotation(true);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        switch (mode) {
            case 1:
                btnConsulta1.setEnabled(false);
                txtTituloGrafica1.setVisibility(INVISIBLE);
                pBConsultas.setVisibility(VISIBLE);
                int realMonth = month + 1;
                fechaATexto = dayOfMonth + "-" + realMonth + "-" + year;
                Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);
                dateToQuery = calendar.getTime();
                txtDate1.setText(getString(R.string.fecha)+": "+fechaATexto);
                //dateDay = new GregorianCalendar(year,month,dayOfMonth).getTime();
                getDataDayOFFireBase(year, realMonth, dayOfMonth);
                lockDeviceRotation(false);
                break;
        }

    }

    private void lockDeviceRotation(boolean value) {
        if (value) {
            int currentOrientation = getResources().getConfiguration().orientation;
            if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            } else {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
            }
        } else {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
            } else {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
            }
        }
    }

    private void getDataDayOFFireBase(int year, int realMonth, int dayOfMonth) {

        final List<DatosGuardados>[] datosCompletos = new List[]{new ArrayList<>()};
        DatabaseReference datosDia = MainActivity.reference.child("datos").child("datosAcum").child("y" + year).child("m" + realMonth).child("d" + dayOfMonth);

        datosDia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<DatosGuardados>> t = new GenericTypeIndicator<ArrayList<DatosGuardados>>() {};
                try {

                    showChartDay(dataSnapshot.getValue(t));

                } catch (Exception ignore) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    private void showChartDay(List<DatosGuardados> datosGuardadosList) {
        List<Entry> entries = new ArrayList<>();
        List<Entry> entries1 = new ArrayList<>();
        List<Entry> entries2 = new ArrayList<>();
        List<Entry> entries3 = new ArrayList<>();
        List<Entry> entries4 = new ArrayList<>();
        lineChart1.clearAnimation();
        lineChart1.clear();
        dataSets.clear();
        DatosGuardados datosCompletos;
        labelsChart = new ArrayList<>();

        float dato1;
        if (datosGuardadosList != null) {
            for (int i = 0; i < datosGuardadosList.size(); i++) {
                datosCompletos = datosGuardadosList.get(i);
                labelsChart.add(datosCompletos.getHora());
                try {
                    dato1 = Float.parseFloat(datosCompletos.getIrradiancia());

                } catch (Exception ignore) {
                    dato1 = 0;
                }

                try {
                    switch (modoGraficar) {
                        case 0:
                            entries.add(new Entry(i, dato1));
                            entries1.add(new Entry(i, Float.parseFloat(datosCompletos.getHumedad())));
                            break;
                        case 1:
                            entries.add(new Entry(i, dato1));
                            entries1.add(new Entry(i, Float.parseFloat(datosCompletos.getCorriente1())));
                            entries2.add(new Entry(i, Float.parseFloat(datosCompletos.getCorriente2())));
                            entries3.add(new Entry(i, Float.parseFloat(datosCompletos.getCorriente3())));
                            entries4.add(new Entry(i, Float.parseFloat(datosCompletos.getCorriente4())));

                            break;
                        case 2:
                            entries.add(new Entry(i, dato1));
                            entries1.add(new Entry(i, Float.parseFloat(datosCompletos.getVoltaje1())));
                            entries2.add(new Entry(i, Float.parseFloat(datosCompletos.getVoltaje2())));
                            entries3.add(new Entry(i, Float.parseFloat(datosCompletos.getVoltaje3())));
                            entries4.add(new Entry(i, Float.parseFloat(datosCompletos.getVoltaje4())));
                            break;

                        case 3:
                            entries1.add(new Entry(i, Float.parseFloat(datosCompletos.getTemperatura())));
                            entries2.add(new Entry(i, Float.parseFloat(datosCompletos.getHumedad())));
                            break;
                    }


                } catch (Exception ignore) {

                }

            }
        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.no_hay_datos), Toast.LENGTH_SHORT).show();
            pBConsultas.setVisibility(INVISIBLE);
        }


        if (entries.size() != 0) {
            txtTituloGrafica1.setVisibility(VISIBLE);
            LineDataSet lineDataSet = new LineDataSet(entries, datoInfo1);
            LineDataSet lineDataSet1 = new LineDataSet(entries1, datoInfo2);
            LineDataSet lineDataSet2 = new LineDataSet(entries2, datoInfo3);
            LineDataSet lineDataSet3 = new LineDataSet(entries3, datoInfo4);
            LineDataSet lineDataSet4 = new LineDataSet(entries4, datoInfo5);

            lineDataSet.setColor(colorDato1);
            lineDataSet1.setColor(colorDato2);
            lineDataSet2.setColor(colorDato3);
            lineDataSet3.setColor(colorDato4);
            lineDataSet4.setColor(colorDato5);

            lineDataSet.setValueTextColor(colorDatoTexto1);
            lineDataSet1.setValueTextColor(colorDatoTexto2);
            lineDataSet2.setValueTextColor(colorDato1);
            lineDataSet3.setValueTextColor(colorDato3);
            lineDataSet4.setValueTextColor(colorDato3);

            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            lineDataSet1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            lineDataSet2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            lineDataSet3.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            lineDataSet4.setMode(LineDataSet.Mode.CUBIC_BEZIER);

            lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
            lineDataSet1.setAxisDependency(YAxis.AxisDependency.RIGHT);
            lineDataSet2.setAxisDependency(YAxis.AxisDependency.RIGHT);
            lineDataSet3.setAxisDependency(YAxis.AxisDependency.RIGHT);
            lineDataSet4.setAxisDependency(YAxis.AxisDependency.RIGHT);

            lineDataSet.setDrawCircles(false);
            lineDataSet1.setDrawCircles(false);
            lineDataSet2.setDrawCircles(false);
            lineDataSet3.setDrawCircles(false);
            lineDataSet4.setDrawCircles(false);
            lineDataSet.setFormSize(10f);
            lineDataSet1.setFormSize(10f);
            lineDataSet2.setFormSize(10f);
            lineDataSet3.setFormSize(10f);
            lineDataSet4.setFormSize(10f);

            dataSets.add(lineDataSet);
            dataSets.add(lineDataSet1);
            dataSets.add(lineDataSet2);
            dataSets.add(lineDataSet3);
            dataSets.add(lineDataSet4);
            LineData data = new LineData(dataSets);
            data.setDrawValues(false);
            lineChart1.setData(data);
            Description description = new Description();
            description.setText("");
            xAxis = lineChart1.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsChart));
            xAxis.setLabelRotationAngle(-10f);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            lineChart1.setDescription(description);
            lineChart1.setDrawMarkers(true);
            CustomMarkerViewData2 customMarkerView = new CustomMarkerViewData2(getContext(), R.layout.item_custom_marker, labelsChart, datoInfo1, datoInfo2, colorDato1, colorDato2);
            customMarkerView.setSizeList(labelsChart.size());
            lineChart1.setMarker(customMarkerView);
            lineChart1.setTouchEnabled(true);
            lineChart1.setVisibility(VISIBLE);
            btnConsulta1.setEnabled(true);
            lineChart1.invalidate();


        } else {
            Toast.makeText(getContext(), -+R.string.no_hay_datos, Toast.LENGTH_SHORT).show();
        }
        btnConsulta1.setEnabled(true);
        pBConsultas.setVisibility(View.INVISIBLE);
    }
}
