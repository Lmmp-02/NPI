package com.example.qrgen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    TextView titulo;
    Button boton_oral, boton_pago, boton_localizacion;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Si el usuario no esta identificado, va al login
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null){
            Intent intent = new Intent(MainActivity.this, IniciaSesion.class);
            startActivity(intent);
        }
        /*
        //Vamos a la ventana de inicio de sesión
        Intent iniciar_sesion = new Intent(MainActivity.this, EligeSesion.class);
        startActivity(iniciar_sesion);
         */
    }

    protected void onStart(){
        super.onStart();

        /*
        setContentView(R.layout.activity_main);
        titulo = findViewById(R.id.titulo);
        boton_oral = findViewById(R.id.boton_inicio_invitado);
        boton_pago = findViewById(R.id.boton_inicio_registrarse);
        boton_localizacion = findViewById(R.id.boton_inicio_iniciar_sesion);
        */
    }

    /*
    ///Codigo creacion QR
    private SensorManager sensorManager;
    private Sensor pressure;

    EditText edit_input;
    Button bt_generate;
    ImageView iv_qr;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_input = findViewById(R.id.edit_input);
        bt_generate = findViewById(R.id.bt_generate);
        iv_qr = findViewById(R.id.iv_qr);

        bt_generate.setOnClickListener(v->{
            generateQR();
        });
    }


    private void generateQR() {
        String text = edit_input.getText().toString().trim();
        MultiFormatWriter writer = new MultiFormatWriter();

        try {
            BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, 400, 400);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            iv_qr.setImageBitmap(bitmap);

        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }
    */

}
