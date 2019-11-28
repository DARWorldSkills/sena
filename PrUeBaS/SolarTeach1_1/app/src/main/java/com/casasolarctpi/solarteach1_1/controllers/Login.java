package com.casasolarctpi.solarteach1_1.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.casasolarctpi.solarteach1_1.R;
import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class Login extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);
        spinner.setItems("Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow");

    }
}
