package com.casasolarctpi.solarteach.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.casasolarctpi.solarteach.R;

public class preJuego extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnSiguiente, btnAudio;
    RadioButton rb1, rb2, rb3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_juego);
        getSupportActionBar().hide();

        inicializar();
    }

    private void inicializar() {

        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnAudio = findViewById(R.id.btnAudio);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        btnSiguiente.setOnClickListener(this);
        btnAudio.setOnClickListener(this);
        rb1.setOnClickListener(this);
        rb2.setOnClickListener(this);
        rb3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSiguiente:

                Intent intent = new Intent(preJuego.this, Juego.class);
                startActivity(intent);

                break;

            case R.id.btnAudio:

                break;
        }
    }
}
