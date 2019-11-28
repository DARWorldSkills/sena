package com.casasolarctpi.responsiveejemplo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LineChart chart;
    List<Entry> entradas = new ArrayList<Entry>() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chart = findViewById ( R.id.chart ) ;


    }
}
