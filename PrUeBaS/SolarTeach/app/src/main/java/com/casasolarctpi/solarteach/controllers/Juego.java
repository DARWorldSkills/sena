package com.casasolarctpi.solarteach.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.casasolarctpi.solarteach.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import static com.casasolarctpi.solarteach.R.drawable.panelneg;

public class Juego extends AppCompatActivity implements View.OnClickListener, MaterialSpinner.OnItemSelectedListener {

    MaterialSpinner spinnerPanel, spinnerVoltaje, spinnerTipoConexion, spinnerCantidad;
    ImageView imgPanel, imgPanelNeg, imgPanelNegro;
    Button btnAudio, btnPasar;
    RadioButton Rb1, Rb2;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_juego);
        getSupportActionBar().hide();

        inicializar();
        escucharBotones();
        ListarSpiner();

    }

    //METODO PARA QUE RECONOZCA EL EVENTO CLICK
    private void escucharBotones() {

        btnAudio.setOnClickListener(this);
        btnPasar.setOnClickListener(this);
        spinnerPanel.setOnItemSelectedListener(this);

    }
    //METODO PARA LISTAR LOS SPINNERS
    private void ListarSpiner() {

        spinnerPanel.setItems("Monocristalino", "Policristalino", "Amorfo");
        spinnerVoltaje.setItems("12V", "24V");
        spinnerTipoConexion.setItems("Serie", "Paralelo", "Mixta");
        spinnerCantidad.setItems("1", "2", "3", "4");


    }
    //METODO PARA INICIALIZAR LOS ATRIBUTOS
    private void inicializar() {
        spinnerPanel =  findViewById(R.id.spinnerTipoDePanel);
        spinnerVoltaje =  findViewById(R.id.spinnerVoltajePanel);
        spinnerTipoConexion = findViewById(R.id.spinnerConexionPanel);
        spinnerCantidad =  findViewById(R.id.spinnerCantidad);
        imgPanel = findViewById(R.id.imgPanel);
        imgPanelNeg = findViewById(R.id.imgPanelNeg);
        imgPanelNegro = findViewById(R.id.imgPanelNegro);
        Rb1 = findViewById(R.id.Rb1);
        Rb2 = findViewById(R.id.Rb2);
        btnAudio = findViewById(R.id.btnAudio);
        btnPasar = findViewById(R.id.btnPasar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAudio:
                break;

            case R.id.btnPasar:
                Intent intent = new Intent(Juego.this, Regulador.class);
                startActivity(intent);
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

        switch (view.getId()){
            case R.id.spinnerTipoDePanel:
                if (position == 0){
                    imgPanelNeg.setVisibility(View.VISIBLE);
                    imgPanel.setVisibility(View.INVISIBLE);
                    imgPanelNegro.setVisibility(View.INVISIBLE);
                }else if (position == 1){
                    imgPanelNeg.setVisibility(View.INVISIBLE);
                    imgPanel.setVisibility(View.VISIBLE);
                    imgPanelNegro.setVisibility(View.INVISIBLE);
                }else if (position == 2){
                    imgPanelNeg.setVisibility(View.INVISIBLE);
                    imgPanel.setVisibility(View.INVISIBLE);
                    imgPanelNegro.setVisibility(View.VISIBLE);
                }
                break;
        }

    }
}
