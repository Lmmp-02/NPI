package com.example.qrgen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class IniciaSesion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicia_sesion);
        getSupportActionBar().hide();
    }
}