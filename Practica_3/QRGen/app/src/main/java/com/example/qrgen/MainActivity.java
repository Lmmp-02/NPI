package com.example.qrgen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    TextView titulo;
    ImageView imagen_logout;
    Button boton_oral, boton_pago, boton_localizacion;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();   //Esconde la barra de arriba (no le gusta a nadie)
        titulo = findViewById(R.id.titulo);
        boton_oral = findViewById(R.id.boton_inicio_invitado);
        boton_pago = findViewById(R.id.boton_inicio_registrarse);
        boton_localizacion = findViewById(R.id.boton_inicio_iniciar_sesion);
        imagen_logout = findViewById(R.id.imagen_logout);
        //Al pulsar el boton de pago de comida, vamos a la actividad de pago
        boton_pago.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PagoComida.class);
                startActivity(intent);
            }
        });

        //Al pulsar el boton de localizacion, vamos a escanear el QR del servicio
        boton_localizacion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EscanearQR.class);
                startActivity(intent);
            }
        });

        //Al pulsar el boton de interfaz oral, vamos a la interfaz oral
        boton_oral.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InterfazOral.class);
                startActivity(intent);
            }
        });

        //Al pulsar el boton de logout, vamos a iniciar sesion
        imagen_logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IniciaSesion.class);
                startActivity(intent);
                finish();   //Esto hace que no se pueda volver hacia atras con las flechas
            }
        });

    }



}
