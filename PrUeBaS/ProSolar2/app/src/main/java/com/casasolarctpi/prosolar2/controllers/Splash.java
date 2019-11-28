package com.casasolarctpi.prosolar2.controllers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.widget.ImageView;

import com.casasolarctpi.prosolar2.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    public static Context context;
    public static DatabaseReference reference;
    private FirebaseAuth mAuth;
    ImageView imgSplash;
    boolean bandera = true;
    int valor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        context = this;
        FirebaseApp.initializeApp(this);
        imgSplash = findViewById(R.id.imgSplash);
        //imgSplash.setVisibility(View.INVISIBLE);
        inizialiteFirebaseApp();
        bandera = true;
        valor = 0;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(bandera){
                    try {
                        Thread.sleep(2000);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            valor++;
                            if (valor == 2){
                                animacionSplash();
                                bandera = false;
                            }
                        }
                    });
                }
            }
        }).start();


    }

    private void animacionSplash() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            try {

                Animator animator = ViewAnimationUtils.createCircularReveal(imgSplash,0,imgSplash.getWidth(),0,imgSplash.getHeight()*1.5f);
                final Animator animator1 = ViewAnimationUtils.createCircularReveal(imgSplash,imgSplash.getMaxWidth()/2, imgSplash.getHeight()/2, imgSplash.getHeight()*1.5f,0);
                animator.setDuration(800);
                animator1.setDuration(800);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        animator1.start();

                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        imgSplash.setVisibility(View.VISIBLE);

                    }
                });

                animator1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        updateUI(currentUser);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                    }
                });

                animator.start();

            }catch (Exception e){

                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        imgSplash.setVisibility(View.VISIBLE);
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        updateUI(currentUser);
                    }
                };
                new Timer().schedule(timerTask,1000);

            }
        }

    }



    // Conexión entre la app y FirebaseApp ,activar persistencia de la base de datos de Firebase y referenciar la instacia de la base de datos
    private void inizialiteFirebaseApp(){
        FirebaseApp.initializeApp(this);
        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(false);}catch (Exception e){}
        reference= FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(Splash.this,MainActivity.class);
            startActivity(intent);
            finish();

        } else {
            Intent intent = new Intent(Splash.this,Login.class);
            startActivity(intent);
            finish();
        }
    }

}

