package com.example.qrgen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class InterfazOral extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz_oral);
        getSupportActionBar().hide();   //Esconde la barra de arriba
    }
}