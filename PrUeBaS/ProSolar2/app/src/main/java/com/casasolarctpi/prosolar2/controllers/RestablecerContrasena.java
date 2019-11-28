package com.casasolarctpi.prosolar2.controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.casasolarctpi.prosolar2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
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
                if (validar() && compararContrasenas()){
                    showProgressBar();
                    AuthCredential credential = EmailAuthProvider.getCredential(MainActivity.userData.getCorreo(), txtContrasenaActual.getText().toString());
                    assert user != null;
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                user.updatePassword(txtNuevaContrasena.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(RestablecerContrasena.this, R.string.cambio_de_contrasena_exito, Toast.LENGTH_SHORT).show();
                                            dialog.cancel();
                                        }else {
                                            hideProgressBar();
                                            Toast.makeText(RestablecerContrasena.this, R.string.hay_un_error_actualizar_contra, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else {
                                hideProgressBar();
                                Toast.makeText(RestablecerContrasena.this, R.string.las_contrasena_actual_no_coincide, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            private void hideProgressBar() {
                txtContrasenaActual.setEnabled(true);
                txtNuevaContrasena.setEnabled(true);
                txtConfirmarContrasenaNueva.setEnabled(true);
                btnAceptar.setEnabled(true);
                btnCancelar.setEnabled(true);
                dialog.findViewById(R.id.pBCambioContra).setVisibility(View.INVISIBLE);
            }

            private void showProgressBar() {
                txtContrasenaActual.setEnabled(false);
                txtNuevaContrasena.setEnabled(false);
                txtConfirmarContrasenaNueva.setEnabled(false);
                btnAceptar.setEnabled(false);
                btnCancelar.setEnabled(false);
                dialog.findViewById(R.id.pBCambioContra).setVisibility(View.VISIBLE);
            }

            private boolean compararContrasenas() {
                boolean valid = true;

                String password1 = txtNuevaContrasena.getText().toString();
                String password2 = txtConfirmarContrasenaNueva.getText().toString();

                if (password1.equals(password2)) {
                    valid = true;
                } else {
                    txtNuevaContrasena.setError("Las contraseñas no coinciden");
                    txtConfirmarContrasenaNueva.setError("Las contraseñas no coinciden");
                    Toast.makeText(RestablecerContrasena.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    valid = false;
                }

                return valid;
            }

            private boolean validar() {
                boolean valid = true;
                String password = txtContrasenaActual.getText().toString();
                if (TextUtils.isEmpty(password)) {
                    txtContrasenaActual.setError(getString(R.string.este_valor_requerido));
                    valid = false;
                } else {
                    txtContrasenaActual.setError(null);
                }

                String password1 = txtNuevaContrasena.getText().toString();
                if (TextUtils.isEmpty(password1)) {
                    txtNuevaContrasena.setError(getString(R.string.este_valor_requerido));
                    valid = false;
                } else {
                    txtNuevaContrasena.setError(null);
                }

                String password2 = txtConfirmarContrasenaNueva.getText().toString();
                if (TextUtils.isEmpty(password2)) {
                    txtConfirmarContrasenaNueva.setError(getString(R.string.este_valor_requerido));
                    valid = false;
                } else {
                    txtConfirmarContrasenaNueva.setError(null);
                }

                if (!valid){
                    Toast.makeText(RestablecerContrasena.this, R.string.campos_vacios, Toast.LENGTH_SHORT).show();
                }

                if (password1.length()<8){
                    txtNuevaContrasena.setError("Contraseña muy corta");
                    Toast.makeText(RestablecerContrasena.this,"Contraseña muy corta", Toast.LENGTH_SHORT).show();
                    valid=false;
                }


                return valid;
            }
        });
        dialog.show();
    }


    private void restablecerContraseña() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.item_reestablecer_contrasena_dentro);
        final Button btnAceptar  = dialog.findViewById(R.id.btnAceptar1);
        final Button btnCancelar = dialog.findViewById(R.id.btnCancelar1);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar();
                dialog.setCancelable(true);
                btnAceptar.setEnabled(true);
                btnCancelar.setEnabled(true);
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = user.getEmail();
                assert emailAddress != null;
                auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("email de resC", "Email sent.");
                            Toast.makeText(RestablecerContrasena.this, R.string.se_le_ha_enviado_un_correo, Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }else {
                            hideProgressBar();
                            Toast.makeText(RestablecerContrasena.this, R.string.a_ocurrido_un_error, Toast.LENGTH_SHORT).show(); }
                    }
                });

            }
            private void showProgressBar() {
                dialog.setCancelable(true);
                btnAceptar.setEnabled(true);
                btnCancelar.setEnabled(true);
                btnAceptar.setEnabled(false);
                btnCancelar.setEnabled(false);
                dialog.findViewById(R.id.pbReestablecerD).setVisibility(View.VISIBLE);
            }

            private void hideProgressBar() {
                dialog.setCancelable(true);
                btnAceptar.setEnabled(true);
                btnCancelar.setEnabled(true);
                btnAceptar.setEnabled(true);
                btnCancelar.setEnabled(true);
                dialog.findViewById(R.id.pbReestablecerD).setVisibility(View.INVISIBLE);
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
}
