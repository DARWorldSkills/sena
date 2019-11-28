package com.casasolarctpi.myapplication.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.casasolarctpi.myapplication.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class Registro extends AppCompatActivity {
    EditText txtCorreo, txtContraseña1, txtContraseña2, txtPrimerNombre, txtSegundoNombre, txtPrimerApellido, txtSegundoApellido, txtInstitucion, txtPais, txtDepartamento, txtCiudad;
    Button btnRegistrarse;
    MaterialSpinner msTipoDeUso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().hide();

        inicializar();


    }

    private void inicializar() {

        txtCorreo = findViewById(R.id.txtCorreo);
        txtContraseña1 = findViewById(R.id.txtContrasena1);
        txtContraseña2 = findViewById(R.id.txtContrasena2);
        txtPrimerNombre = findViewById(R.id.txtPrimerNombre);
        txtSegundoNombre = findViewById(R.id.txtSegundoNombre);
        txtPrimerApellido = findViewById(R.id.txtPrimerApellido);
        txtSegundoApellido = findViewById(R.id.txtSegundoApellido);
        txtInstitucion = findViewById(R.id.txtInstitucion);
        txtPais = findViewById(R.id.txtPais);
        txtDepartamento = findViewById(R.id.txtDepartamento);
        txtCiudad = findViewById(R.id.txtCiudad);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
        msTipoDeUso = findViewById(R.id.msTipoDeUso);

    }

    public void Registrar(View view) {

    }
}
