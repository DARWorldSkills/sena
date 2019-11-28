package com.casasolarctpi.prosolar2.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.casasolarctpi.prosolar2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RestablecerContrasena extends AppCompatActivity implements View.OnClickListener {

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablecer_contrasena);
        findViewById(R.id.btnCambiarContrasena).setOnClickListener(this);
        findViewById(R.id.btnReestablecerContrasena).setOnClickListener(this);
        inicializarFirebase();


    }

    private void inicializarFirebase() {
        user = FirebaseAuth.getInstance().getCurrentUser();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCambiarContrasena:
                cambioDeContraseña();
                break;

            case R.id.btnReestablecerContrasena:
                restablecerContraseña();
                break;
        }
    }

    private void cambioDeContraseña() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.item_cambio_contrasena);
        final EditText txtContrasenaActual = dialog.findViewById(R.id.txtContrasenaActual);
        final EditText txtNuevaContrasena = dialog.findViewById(R.id.txtNuevaContrasena);
        final EditText txtConfirmarContrasenaNueva = dialog.findViewById(R.id.txtConfirmarContrasenaNueva);
        final Button btnAceptar = dialog.findViewById(R.id.btnAceptar1);
        final Button btnCancelar = dialog.findViewById(R.id.btnCancelar1);
        dialog.findViewById(R.id.pBCambioContra).setVisibility(View.INVISIBLE);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });
    }


    private void restablecerContraseña() {
    }
}
