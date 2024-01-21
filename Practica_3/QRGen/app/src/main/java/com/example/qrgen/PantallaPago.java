package com.example.qrgen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PantallaPago extends AppCompatActivity {

    // Crear un vector de strings con tamaño 5
    String[] campos = new String[4];
    String resultado;

    TextView txtPago;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_pago);

        // Recuperar el resultado del escaneo del Intent
        Intent intent = getIntent();
        if (intent.hasExtra("RESULTADO_QR")) resultado = intent.getStringExtra("RESULTADO_QR");

        String[] lineas = resultado.split("\\r?\\n");

        // Añadir strings al vector
        campos[0] = "Primero";
        campos[1] = "Segundo";
        campos[2] = "Acompanamiento";
        campos[3] = "Postre";

        txtPago = findViewById(R.id.txtPago);

        if(lineas.length > 6){ //Menú recoger
            agregarCamposDinamicos(8, lineas);
            txtPago.setText("Precio del menú, 6 euros\nDesliza dos dedos hacia arriba para pagar");
        }
        else{ //Menú diario
            txtPago.setText("Precio del menú, 3,5 euros\nDesliza dos dedos hacia arriba para pagar");
            agregarCamposDinamicos(4, lineas);
        }
    }

    private void agregarCamposDinamicos(int cantidadCampos, String[] info) {
        LinearLayout contenedorCampos = findViewById(R.id.contenedorCampos);

        // Añadir título principal
        TextView tituloPrincipal = new TextView(this);
        tituloPrincipal.setText(info[0]+", "+info[1]);
        tituloPrincipal.setTextSize(18); // Tamaño del texto
        tituloPrincipal.setGravity(Gravity.CENTER); // Alinear al centro

        contenedorCampos.addView(tituloPrincipal);

        for (int i = 0; i < cantidadCampos; i++) {
            TextView etiqueta = new TextView(this);
            etiqueta.setText(campos[i%4]);

            EditText campoTexto = new EditText(this);
            campoTexto.setInputType(InputType.TYPE_NULL);
            if(i > 3) campoTexto.setText(info[i+3]);
            else campoTexto.setText(info[i+2]);

            // Configurar diseño y añadir al contenedor
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.gravity = Gravity.CENTER_VERTICAL;
            params.topMargin = 30; // Margen superior entre campos

            contenedorCampos.addView(etiqueta, params);
            contenedorCampos.addView(campoTexto, params);

            // Añadir título secundario después de los primeros 4 campos
            if (cantidadCampos == 8 && i == 3) {
                TextView tituloSecundario = new TextView(this);
                tituloSecundario.setText(info[i+3]);
                tituloSecundario.setTextSize(18);
                tituloSecundario.setGravity(Gravity.CENTER);

                // Ajustar el margen superior del título secundario
                LinearLayout.LayoutParams paramsTituloSecundario = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                paramsTituloSecundario.gravity = Gravity.CENTER_VERTICAL;
                paramsTituloSecundario.topMargin = 60;

                contenedorCampos.addView(tituloSecundario, paramsTituloSecundario);
            }
        }
    }
}