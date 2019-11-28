package com.casasolarctpi.solarteach.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.casasolarctpi.solarteach.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnInicio, btnAyuda, btnAudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        inicializar();
    }

    private void inicializar() {
        btnInicio = findViewById(R.id.btnInicio);
        btnAyuda = findViewById(R.id.btnAyuda);
        btnAudio = findViewById(R.id.btnAudio);
        btnAyuda.setOnClickListener(this);
        btnInicio.setOnClickListener(this);
        btnAudio.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnInicio:

                Intent intent = new Intent(MainActivity.this, preJuego.class);
                startActivity(intent);

                break;

            case R.id.btnAyuda:

                break;

            case R.id.btnAudio:

                break;
        }
    }
}
