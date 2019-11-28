package com.casasolarctpi.prosolar2.models;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.casasolarctpi.prosolar2.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.List;

public class CustomMarkerViewDataA extends MarkerView {
    private TextView txtCustomMarker1, txtCustomMarker2;
    private List<String> labelsChart;

    private int colores[];

    private MPPointF mOffset;
    private float sizeList;
    private float getX1;


    public float getSizeList() {
        return sizeList;
    }

    public void setSizeList(float sizeList) {
        this.sizeList = sizeList;
    }



    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */

    public CustomMarkerViewDataA(Context context, int layoutResource, List<String> labelsChart, int[] colores) {
        super(context, layoutResource);
        this.labelsChart = labelsChart;
        this.colores = colores;
        txtCustomMarker1 = findViewById(R.id.txtCustomMarker1);
        txtCustomMarker2 = findViewById(R.id.txtCustomMarker2);
    }



    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        super.refreshContent(e, highlight);
        getX1=e.getX();
        switch (highlight.getDataSetIndex() ) {
            case 0:
                txtCustomMarker1.setText(getResources().getString(R.string.hora) + ": " + labelsChart.get((int) e.getX()));
                txtCustomMarker2.setText("I1N"+ ": " + e.getY());
                txtCustomMarker2.setTextColor(colores[0]);
                break;
            case 1:
                txtCustomMarker1.setText(getResources().getString(R.string.hora) + ": " + labelsChart.get((int) e.getX()));
                txtCustomMarker2.setText("I2W" + ": " + e.getY());
                txtCustomMarker2.setTextColor(colores[1]);
                break;

            case 2:
                txtCustomMarker1.setText(getResources().getString(R.string.hora) + ": " + labelsChart.get((int) e.getX()));
                txtCustomMarker2.setText("I3S" + ": " + e.getY());
                txtCustomMarker2.setTextColor(colores[2]);
                break;
            case 3:
                txtCustomMarker1.setText(getResources().getString(R.string.hora) + ": " + labelsChart.get((int) e.getX()));
                txtCustomMarker2.setText("I4E" + ": " + e.getY());
                txtCustomMarker2.setTextColor(colores[3]);
                break;



        }

    }


    @Override
    public MPPointF getOffset() {
        mOffset = new MPPointF((float) -(getWidth() / 2.2), -getHeight());
        float resta = getSizeList()-getX1;
        float tmp1 = (resta*100)/getSizeList();
        Log.e("tmp",tmp1 + ";"+resta+";"+getSizeList()+";"+getX1);
        if (tmp1<12) {
            mOffset = new MPPointF((float) -(getWidth() / 1.2), -getHeight());
        }
        return mOffset;

    }


}
