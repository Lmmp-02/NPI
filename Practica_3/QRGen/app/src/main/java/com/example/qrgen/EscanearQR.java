package com.example.qrgen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EscanearQR extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escanear_qr);
        getSupportActionBar().hide();   //Esconde la barra de arriba
    }
}