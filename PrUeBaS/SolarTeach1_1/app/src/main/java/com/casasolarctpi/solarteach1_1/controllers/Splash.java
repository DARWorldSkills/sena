package com.casasolarctpi.solarteach1_1.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.casasolarctpi.solarteach1_1.R;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();


        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, Login.class);
                startActivity(intent);
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 1500);
    }
}
