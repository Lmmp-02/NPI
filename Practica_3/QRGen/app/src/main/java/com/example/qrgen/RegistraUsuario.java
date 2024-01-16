package com.example.qrgen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class RegistraUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra_usuario);
        getSupportActionBar().hide();
    }
}