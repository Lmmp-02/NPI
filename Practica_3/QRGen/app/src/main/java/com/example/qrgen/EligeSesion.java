package com.example.qrgen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EligeSesion extends AppCompatActivity {
    Button boton_registrarse, boton_iniciar_sesion, boton_invitado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elige_sesion);
        boton_registrarse = findViewById(R.id.boton_inicio_registrarse);
        boton_iniciar_sesion = findViewById(R.id.boton_inicio_iniciar_sesion);
        boton_invitado = findViewById(R.id.boton_inicio_invitado);

        boton_registrarse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                /*
                //Vamos a la pestaña de inicio de sesión
                Intent iniciar_sesion = new Intent(MainActivity.this, EligeSesion.class);
                startActivity(iniciar_sesion);
                */
            }


        });
    }

}