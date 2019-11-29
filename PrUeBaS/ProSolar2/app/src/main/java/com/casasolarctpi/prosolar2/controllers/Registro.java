package com.casasolarctpi.prosolar2.controllers;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.casasolarctpi.prosolar2.R;
import com.casasolarctpi.prosolar2.models.Constants;
import com.casasolarctpi.prosolar2.models.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Registro extends AppCompatActivity implements View.OnClickListener {
    EditText txtCorreo, txtContrasena1, txtContrasena2, txtPrimerNombre, txtSegundoNombre, txtPrimerApellido, txtSegundoApellido, txtInstitucion, txtPais, txtDepartamento, txtCiudad;
    Button btnRegistrarse;
    MaterialSpinner msTipoDeUso;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().hide();

        inicializar();
        inicializarFirebase();


    }

    private void inicializar() {

        txtCorreo = findViewById(R.id.txtCorreo);
        txtContrasena1 = findViewById(R.id.txtContrasena1);
        txtContrasena2 = findViewById(R.id.txtContrasena2);
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
        findViewById(R.id.btnRegistrar).setOnClickListener(this);
        findViewById(R.id.progressBarRegistro).setVisibility(View.INVISIBLE);
        agregarDatos();
    }

    private void agregarDatos() {


        msTipoDeUso.setItems(Constants.tipoDeUso);
    }


    private void inicializarFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

    }

    public void Registrar(View view) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRegistrar:
            registrarUsuario();
            break;
        }
    }

    private void registrarUsuario() {
        if (compararContraseña()){
            crearCuenta(txtCorreo.getText().toString(), txtContrasena1.getText().toString());
        }else {

        }

    }

    private void crearCuenta(String correo, String contraseña) {

        Log.d("Crear Cuenta", "crearcuenta:" + correo);
        mostrarProgressDialog();

        mAuth.createUserWithEmailAndPassword(correo, contraseña).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d("Crear Cuenta", "crearusuarioconcorreo:satisfactorio");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                    databaseReference.child("usuarios").push().setValue(datosCompletos());
                }else {
                    Log.w("Crear Usuario", "crearUsuarioCorreo:Falla", task.getException());
                    Toast.makeText(Registro.this, "Autentificacion fallida", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }

                hideProgressDialog();
            }
        });
    }

    private void mostrarProgressDialog() {

        findViewById(R.id.progressBarRegistro).setVisibility(View.VISIBLE);
        findViewById(R.id.btnRegistrar).setEnabled(false);
        txtCorreo.setEnabled(false);
        txtContrasena1.setEnabled(false);
        txtContrasena2.setEnabled(false);
        txtPrimerNombre.setEnabled(false);
        txtSegundoNombre.setEnabled(false);
        txtPrimerApellido.setEnabled(false);
        txtSegundoApellido.setEnabled(false);
        txtInstitucion.setEnabled(false);
        txtPais.setEnabled(false);
        txtDepartamento.setEnabled(false);
        txtCiudad.setEnabled(false);

    }

    private void hideProgressDialog() {

        findViewById(R.id.progressBarRegistro).setVisibility(View.INVISIBLE);
        findViewById(R.id.btnRegistrar).setEnabled( true);
        txtCorreo.setEnabled(true);
        txtContrasena1.setEnabled(true);
        txtContrasena2.setEnabled(true);
        txtPrimerNombre.setEnabled(true);
        txtSegundoNombre.setEnabled(true);
        txtPrimerApellido.setEnabled(true);
        txtSegundoApellido.setEnabled(true);
        txtInstitucion.setEnabled(true);
        txtPais.setEnabled(true);
        txtDepartamento.setEnabled(true);
        txtCiudad.setEnabled(true);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null){
            Toast.makeText(this, "Por favor ingresa con tu nueva cuenta", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Registro.this, Login.class);
            startActivity(intent);
            finish();
        }else {

        }

    }

    private boolean validar(){
        boolean validar = true;
        String correo = txtCorreo.getText().toString();
        if (TextUtils.isEmpty(correo)){
            txtCorreo.setError("Este campo es obligatorio");
            validar = false;
        }else {
            txtCorreo.setError(null);
        }

        String contrasena1 = txtContrasena1.getText().toString();
        if (TextUtils.isEmpty(contrasena1)){
            txtContrasena1.setError("Este campo es obligatorio");
            validar = false;
        }else {
            txtContrasena1.setError(null);
        }

        String contrasena2 = txtContrasena2.getText().toString();
        if (TextUtils.isEmpty(contrasena2)){
            txtContrasena2.setError("Este campo es obligatorio");
            validar = false;
        }else {
            txtContrasena2.setError(null);
        }

        String primerN = txtPrimerNombre.getText().toString();
        if (TextUtils.isEmpty(primerN)){
            txtPrimerNombre.setError("Este campo es obligatorio");
            validar = false;
        }else {
            txtPrimerNombre.setError(null);
        }

        String primerA = txtPrimerApellido.getText().toString();
        if (TextUtils.isEmpty(primerA)){
            txtPrimerApellido.setError("Este campo es obligatorio");
            validar = false;
        }else {
            txtPrimerApellido.setError(null);
        }


        String pais = txtPais.getText().toString();
        if (TextUtils.isEmpty(pais)){
            txtPais.setError("Este campo es obligatorio");
            validar = false;
        }else {
            txtPais.setError(null);
        }

        String departamento = txtDepartamento.getText().toString();
        if (TextUtils.isEmpty(departamento)){
            txtDepartamento.setError("Este campo es obligatorio");
            validar = false;
        }else {
            txtDepartamento.setError(null);
        }

        String ciudad = txtCiudad.getText().toString();
        if (TextUtils.isEmpty(ciudad)){
            txtCiudad.setError("Este campo es obligatorio");
            validar = false;
        }else {
            txtCiudad.setError(null);
        }

        if (msTipoDeUso.getSelectedIndex()<0){
            txtCiudad.setError("Este campo es obligatorio");
            validar=false;
        }else {
            msTipoDeUso.setError(null);
        }


        return validar;
    }


    private boolean compararContraseña() {
        boolean validar = false;

        String contrasena1 = txtContrasena1.getText().toString();
        String contrasena2 = txtContrasena2.getText().toString();

        if (validar()){

            if (contrasena1.length()<8){
                txtContrasena1.setError("La contraseña es muy corta.");
                Toast.makeText(this, "La contraseña es muy corta, por favor ingrese una contraseña mas larga ", Toast.LENGTH_SHORT).show();
                validar = false;
            }else{
                if (contrasena1.equals(contrasena2)){
                    validar = true;
                }else{
                    txtContrasena1.setError("Las contraseñas no coinciden");
                    txtContrasena2.setError("Las contraseñas no coinciden");
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    validar = false;
                }
            }

        }else {
            Toast.makeText(this, "por favor revise sus datos, algunos no coinciden", Toast.LENGTH_SHORT).show();
        }
        return validar;
    }

    private UserData datosCompletos() {

        UserData data = new UserData();
        data.setCiudad(txtCiudad.getText().toString());
        data.setDepartamento(txtDepartamento.getText().toString());
        data.setCorreo(txtCorreo.getText().toString());
        data.setInstitucion(txtInstitucion.getText().toString());
        data.setPais(txtPais.getText().toString());
        data.setPrimerNombre(txtPrimerNombre.getText().toString());
        data.setPrimerApellido(txtPrimerApellido.getText().toString());
        data.setSegundoNombre(txtSegundoNombre.getText().toString());
        data.setSegundoApellido(txtSegundoApellido.getText().toString());
        int index = msTipoDeUso.getSelectedIndex();
        data.setTipodeuso(msTipoDeUso.getItems().get(index).toString());
        data.setSegundoApellido(txtSegundoApellido.getText().toString());
        return data;
    }

}
