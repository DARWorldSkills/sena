package com.casasolarctpi.myapplication.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.casasolarctpi.myapplication.R;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText txtUsuario, txtContraseña;
    TextView txtOlvidarContraseña;
    Button btnRegistrarse, btnIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        inicializar();
        escucharBotones();
    }

    private void escucharBotones() {
        btnIngresar.setOnClickListener(this);
        btnRegistrarse.setOnClickListener(this);
        txtOlvidarContraseña.setOnClickListener(this);
    }

    private void inicializar() {

        txtUsuario = findViewById(R.id.txtUsuario);
        txtContraseña = findViewById(R.id.txtContraseña);
        txtOlvidarContraseña = findViewById(R.id.txtRestablecerContraseña);
        btnIngresar = findViewById(R.id.btnIniciar);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnIniciar:

                break;

            case R.id.btnRegistrarse:
                Intent intent = new Intent(Login.this, Registro.class);
                startActivity(intent);
                break;


            case R.id.txtRestablecerContraseña:

                break;
        }
    }
}
