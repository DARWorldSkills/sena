package com.casasolarctpi.solarteach.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.casasolarctpi.solarteach.R;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText ediUsuario, ediContraseña;
    Button btnIngresar;
    TextView txtRegistrar, txtOlvidarContraseña;
    int validar = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        
        inicializar();


    }

    private void inicializar() {
        ediUsuario = findViewById(R.id.editUsuario);
        ediContraseña = findViewById(R.id.editContraseña);
        btnIngresar = findViewById(R.id.btnIngresar);
        txtRegistrar = findViewById(R.id.txtRegistrar);
        txtOlvidarContraseña = findViewById(R.id.txtOlvidarContraseña);
        btnIngresar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnIngresar:
                validar();

                break;
            case R.id.txtRegistrar:


                break;

            case R.id.txtOlvidarContraseña:

                break;
        }
    }

    private void validar() {

        String edit = ediUsuario.getText().toString().trim();
        String editC = ediContraseña.getText().toString().trim();
        if (edit.isEmpty()){
            ediUsuario.setError("Falta este campo");
        }else if (editC.isEmpty()){
            ediContraseña.setError("Falta este campo");
        }else {

            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void registro(View view) {
        Intent intent = new Intent(Login.this, Registro.class);
        startActivity(intent);
    }
}
