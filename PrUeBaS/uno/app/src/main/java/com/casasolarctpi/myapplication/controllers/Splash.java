package com.casasolarctpi.myapplication.controllers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.casasolarctpi.myapplication.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    public static Context context;
    //public static DatabaseReference reference;
    //private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        context = this;
        inizialiteFirebaseApp();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, Login.class);
                startActivity(intent);
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 2000);
    }

    // Conexión entre la app y FirebaseApp ,activar persistencia de la base de datos de Firebase y referenciar la instacia de la base de datos
    private void inizialiteFirebaseApp(){



    }

}

