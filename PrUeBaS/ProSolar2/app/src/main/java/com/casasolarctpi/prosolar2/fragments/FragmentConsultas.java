package com.casasolarctpi.prosolar2.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.casasolarctpi.prosolar2.R;
import com.casasolarctpi.prosolar2.models.DatosGuardados;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.view.View.INVISIBLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentConsultas extends Fragment implements View.OnClickListener {

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
    int month, yearM, numDias;
    public static int modoGraficar = 0;
    TextView txtTituloGrafica1, txtTituloGrafica2, txtTituloGrafica3;
    String datoInfo1;
    String datoInfo2;

    int colorDato1, colorDato2, colorDatoTexto1, colorDatoTexto2;
    float yAxisMax1, yAxisMin1, yAxisMax2, yAxisMin2;
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
        //ingresarValoresChart();


        return view;
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
                colorDato2 = getResources().getColor(R.color.colorGraficaPunto4);

                colorDatoTexto1 = getResources().getColor(R.color.colorGraficaLinea3);
                colorDatoTexto2 = getResources().getColor(R.color.colorGraficaLinea4);

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

                txtTituloGrafica1.setText(R.string.titulo_irradiancia_voltaje);
                txtTituloGrafica2.setText(R.string.titulo_irradiancia_voltaje);
                txtTituloGrafica3.setText(R.string.titulo_irradiancia_voltaje);

                break;


            case 3:

                datoInfo1 = getResources().getString(R.string.dato1);
                datoInfo2 = getResources().getString(R.string.dato2);

                colorDato1 = getResources().getColor(R.color.colorGraficaPunto1);
                colorDato2 = getResources().getColor(R.color.colorGraficaPunto2);

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

    }

    private static View createTabView(final Context context, final String text) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_bg, null);
        TextView tv = view.findViewById(R.id.tabsText);
        tv.setText(text);
        return view;
    }

    @Override
    public void onClick(View view) {

    }
}
