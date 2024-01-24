package grupo2.AsistenteEtsiit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MuestraRutaActivity extends AppCompatActivity implements SensorEventListener {
    private List<List<String>> archivos;
    private ImageView imageView_cam;
    private TextView textView_cam;
    private TextView textView_idx;
    Button boton_adelante;
    Button boton_atras;
    private int idx;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private float lastZ = 0;
    private static final float SHAKE_THRESHOLD = 20.0f;
    private static final long TIME_THRESHOLD = 1000;
    private Handler handler = new Handler();
    private boolean canDetect = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra_ruta);
        getSupportActionBar().hide();   //Esconde la barra de arriba (no le gusta a nadie)

        String origen = getIntent().getStringExtra("origen");
        String destino = getIntent().getStringExtra("destino");

        Caminos caminos = new Caminos("locs.txt", "cams.txt", getApplicationContext());

        archivos = caminos.calculaRutaArch(origen, destino);

        if(archivos.size() == 0){
            Toast.makeText(this, "No se ha encontrado un camino entre las localizaciones especificadas", Toast.LENGTH_SHORT).show();
            finish();
        }

        imageView_cam = findViewById(R.id.mr_imagecam);
        textView_cam = findViewById(R.id.mr_textcam);
        textView_idx = findViewById(R.id.mr_textact);
        boton_atras = findViewById(R.id.mr_boton_atras);
        boton_adelante = findViewById(R.id.mr_boton_adelante);
        boton_atras.setEnabled(false);

        idx = 0;
        actualizaImagen(idx);

        boton_adelante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguiente();
            }
        });

        boton_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anterior();
            }
        });

        final GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float deltaX = e2.getX() - e1.getX();

                if (deltaX > 0) {
                    // Deslizamiento hacia la derecha
                    anterior();
                } else if (deltaX < 0) {
                    // Deslizamiento hacia la izquierda
                    siguiente();
                }

                return true;
            }
        });

        View rootView = getWindow().getDecorView().getRootView();
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        if(accelerometer != null){
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(magnetometer != null){
            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    private void actualizaImagen(int indice){
        try {
            // Actualizamos el índice internamente
            idx = indice;

            // Abre un flujo de entrada para la imagen y la decripcio en assets
            InputStream inputStream_img = getAssets().open(archivos.get(indice).get(0));
            InputStream inputStream_txt = getAssets().open(archivos.get(indice).get(1));

            // Crea un Drawable desde el InputStream y establece la imagen en el ImageView
            imageView_cam.setImageDrawable(Drawable.createFromStream(inputStream_img, null));

            // Lee el contenido del archivo línea por línea
            StringBuilder contenido = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream_txt));
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                contenido.append(linea).append("\n");
            }

            textView_cam.setText(contenido.toString());

            String str_indice = String.valueOf(indice+1) + "/" + String.valueOf(archivos.size());
            textView_idx.setText(str_indice);

            // Cierra el InputStream
            inputStream_img.close();
            inputStream_txt.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void anterior(){
        if(idx > 0){
            actualizaImagen(idx-1);

            boton_adelante.setEnabled(true);

            if(idx == 0){
                boton_atras.setEnabled(false);
            }
        }
    }

    private void siguiente(){
        if(idx < archivos.size()-1){
            actualizaImagen(idx+1);

            boton_atras.setEnabled(true);

            if(idx == archivos.size()-1){
                boton_adelante.setEnabled(false);
            }
        }
    }

    private void actualizaOrientacion(int grados){

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Registra el SensorEventListener cuando la actividad se reanuda
        if(accelerometer != null){
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(magnetometer != null){
            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Desregistra el SensorEventListener cuando la actividad se pausa
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Obtén los valores de aceleración en los ejes X, Y, Z
            float z = event.values[2];

            // Detecta el movimiento horizontal evitando detecciones consecutivas
            if (canDetect && Math.abs(z - lastZ) > SHAKE_THRESHOLD) {
                canDetect = false;

                siguiente();

                // Programa la habilitación de la detección después de un tiempo
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        canDetect = true;
                    }
                }, TIME_THRESHOLD);
            }

            // Actualiza el último valor de Y y el tiempo
            lastZ = z;
        }

        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            actualizaOrientacion(Math.round(event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No es necesario implementar esto para el acelerómetro
    }
}