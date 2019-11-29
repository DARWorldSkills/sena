package com.casasolarctpi.prosolar2.controllers;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.casasolarctpi.prosolar2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    EditText txtUsuario, txtContraseña;
    TextView txtOlvidarContraseña;
    Button btnRegistrarse, btnIngresar;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        inicializar();
        inicializarFirebase();
        escucharBotones();
    }

    private void inicializar() {

        txtUsuario = findViewById(R.id.txtUsuario);
        txtContraseña = findViewById(R.id.txtContraseña);
        txtOlvidarContraseña = findViewById(R.id.txtRestablecerContraseña);
        btnIngresar = findViewById(R.id.btnIniciar);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
        findViewById(R.id.pbLogin).setVisibility(View.INVISIBLE);
    }

    private void inicializarFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    private void escucharBotones() {
        btnIngresar.setOnClickListener(this);
        btnRegistrarse.setOnClickListener(this);
        txtOlvidarContraseña.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnIniciar:
                String correo = txtUsuario.getText().toString();
                String contrasena  = txtContraseña.getText().toString();
                signIn(correo,contrasena);
                break;

            case R.id.btnRegistrarse:
                Intent intent = new Intent(Login.this, Registro.class);
                startActivity(intent);
                break;


            case R.id.txtRestablecerContraseña:
                restablecerContrasena();
                break;
        }
    }

    private void restablecerContrasena() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.item_reestablecer_contrasena);
        final EditText txtEmail = dialog.findViewById(R.id.txtEmail);
        final Button btnAceptar = dialog.findViewById(R.id.btnAceptar1);
        final Button btnCancelar = dialog.findViewById(R.id.btnCancelar1);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnAceptar.setEnabled(false);
                btnCancelar.setEnabled(false);
                dialog.setCancelable(false);

                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = txtEmail.getText().toString();

                auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Reestablecer C afuera", "Email sent.");
                            Toast.makeText(Login.this, R.string.se_le_ha_enviado_un_correo, Toast.LENGTH_SHORT).show();
                            dialog.cancel();

                        }else {
                            btnAceptar.setEnabled(false);
                            btnCancelar.setEnabled(false);
                            dialog.setCancelable(false);
                            Toast.makeText(Login.this, R.string.a_ocurrido_un_error_afuera, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.cancel();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.setCancelable(true);
        dialog.show();


    }

    private void signIn(String email, String contrasena) {

        Log.d("Inicio de Sesión", "Ingresar" + email);
        if (!validar()) {
            return;
        }

        MostrarProgressDialog();

        mAuth.signInWithEmailAndPassword(email, contrasena).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d("Inicio de sesión", "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                    findViewById(R.id.pbLogin).setVisibility(View.INVISIBLE);
                    Toast.makeText(Login.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                }else{
                    Log.w("Inicio de sesión", "signInWithEmail:success");
                    Toast.makeText(Login.this, "El usuario no esta registrado, o la contraseña no coincide", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                    hideProgressDialog();
                }
            }
        });
    }

    private void MostrarProgressDialog() {
        findViewById(R.id.pbLogin).setVisibility(View.VISIBLE);
        btnIngresar.setEnabled(false);
        btnRegistrarse.setEnabled(false);
        txtUsuario.setEnabled(false);
        txtContraseña.setEnabled(false);
    }

    private void hideProgressDialog() {
        findViewById(R.id.pbLogin).setVisibility(View.INVISIBLE);
        btnIngresar.setEnabled(true);
        btnRegistrarse.setEnabled(true);
        txtUsuario.setEnabled(true);
        txtContraseña.setEnabled(true);
    }

    private boolean validar(){
        boolean validar = true;

        String correo = txtUsuario.getText().toString();
        if (TextUtils.isEmpty(correo)){
            txtUsuario.setError("Este campo es obligatorio");
            validar = false;
        }else {
            txtUsuario.setError(null);
        }

        String contraseña = txtContraseña.getText().toString();
        if (TextUtils.isEmpty(contraseña)){
            txtContraseña.setError("Este campo es obligatorio");
            validar = false;
        }else {
            txtContraseña.setError(null);
        }

        return validar;
    }

    private void updateUI(FirebaseUser user) {
        if (user != null){
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else {

        }
    }




}
