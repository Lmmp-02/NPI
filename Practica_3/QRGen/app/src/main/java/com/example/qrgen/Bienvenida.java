package com.example.qrgen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


/*
Pantalla inicial de carga :)
 */
public class Bienvenida extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);
        getSupportActionBar().hide();   //Esconde la barra de arriba
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                //Intent intent = new Intent(Bienvenida.this, IniciaSesion.class);
                Intent intent = new Intent(Bienvenida.this, IniciaSesion.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }
}