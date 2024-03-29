package grupo2.AsistenteEtsiit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.Arrays;
import java.util.List;

public class MuestraRutaActivity extends AppCompatActivity implements SensorEventListener {
    private List<List<String>> archivos;
    private ImageView imageView_cam;
    private ImageView imageView_bruj;
    private TextView textView_cam;
    private TextView textView_idx;
    Button boton_adelante;
    Button boton_atras;
    private int idx;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float lastZ = 0;
    private static final float SHAKE_THRESHOLD = 20.0f;
    private static final long TIME_THRESHOLD = 1000;
    private Handler handler = new Handler();
    private boolean canDetect = true;

    private Sensor rotationVectorSensor;
    private float[] rotationMatrix = new float[9];
    private float[] orientationValues = new float[3];
    private final List<String> direcciones = Arrays.asList("N", "NE", "E", "SE", "S", "SW", "W", "NW");
    int orientacion_act;

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
        imageView_bruj = findViewById(R.id.mr_imagebruj);
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
        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        if(accelerometer != null){
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(rotationVectorSensor != null){
            sensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    private void actualizaImagen(int indice){
        // Actualizamos el índice internamente
        idx = indice;

        // Abre un flujo de entrada para la imagen y la decripcio en assets
        try {
            InputStream inputStream_img = getAssets().open(archivos.get(indice).get(0));
            // Crea un Drawable desde el InputStream y establece la imagen en el ImageView
            imageView_cam.setImageDrawable(Drawable.createFromStream(inputStream_img, null));
            inputStream_img.close();
        }
        catch(IOException e) {
            try {
                InputStream inputStream_img = getAssets().open("imagenes/null.png");
                // Crea un Drawable desde el InputStream y establece la imagen en el ImageView
                imageView_cam.setImageDrawable(Drawable.createFromStream(inputStream_img, null));
                inputStream_img.close();
            }
            catch (IOException e2){
                e2.printStackTrace();
            }
        }

        try {
            // Lee el contenido del archivo línea por línea
            InputStream inputStream_txt = getAssets().open(archivos.get(indice).get(1));
            StringBuilder contenido = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream_txt));
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                contenido.append(linea).append("\n");
            }

            textView_cam.setText(contenido.toString());
            inputStream_txt.close();
        }
        catch (IOException e) {
            textView_cam.setText(R.string.mr_nodisponible);
            e.printStackTrace();
        }

        String str_indice = String.valueOf(indice+1) + "/" + String.valueOf(archivos.size());
        textView_idx.setText(str_indice);

        orientacion_act = -1;
    }

    private void anterior(){
        if(idx > 0){
            actualizaImagen(idx-1);

            boton_adelante.setEnabled(true);
            boton_adelante.setCompoundDrawablesWithIntrinsicBounds(ResourcesCompat.getDrawable(getResources(), R.drawable.baseline_arrow_forward_24, getTheme()), null, null, null);
            boton_adelante.setText("");

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
                boton_adelante.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                boton_adelante.setText("Salir");
            }
        }
        else if(idx == archivos.size()-1){
            //Vemos cual es la actividad a la que hay que volver
            SharedPreferences preferences = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
            //Si es la interfaz oral la que está activa, volvemos a ella
            if(preferences.getBoolean("interfazOralActiva", false)) {
                Intent intent = new Intent(MuestraRutaActivity.this, InterfazOralActivity.class);
                //Limpia la pila de actividades hasta el MainActivity y crea una nueva tarea si es necesario.
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            //Si no, volvemos al menu principal
            else{
                Intent intent = new Intent(MuestraRutaActivity.this, MainActivity.class);
                //Limpia la pila de actividades hasta el MainActivity y crea una nueva tarea si es necesario.
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Registra el SensorEventListener cuando la actividad se reanuda
        if(accelerometer != null){
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(rotationVectorSensor != null){
            sensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_UI);
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
        if (event.sensor == accelerometer) {
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

        if(event.sensor == rotationVectorSensor){
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
            SensorManager.getOrientation(rotationMatrix, orientationValues);

            // Obtener la orientación en grados
            float azimuth = (float) Math.toDegrees(orientationValues[0]);

            // Calcular la dirección basada en el ángulo de orientación
            int direction = getDirectionFromAzimuth(azimuth);

            if(orientacion_act != direction) {
                actualizaOrientacion(direction);
            }

            orientacion_act = direction;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No es necesario implementar esto para el acelerómetro
    }

    private int getDirectionFromAzimuth(float azimuth) {
        // Ajustar el ángulo a un rango [0, 360)
        azimuth = (azimuth + 360) % 360;

        // Calcular la dirección basada en el ángulo
        if (azimuth >= 337.5 || azimuth < 22.5) {
            return 0;   // N
        } else if (azimuth >= 22.5 && azimuth < 67.5) {
            return 1;   // NE
        } else if (azimuth >= 67.5 && azimuth < 112.5) {
            return 2;   // E
        } else if (azimuth >= 112.5 && azimuth < 157.5) {
            return 3;   // SE
        } else if (azimuth >= 157.5 && azimuth < 202.5) {
            return 4;   // S
        } else if (azimuth >= 202.5 && azimuth < 247.5) {
            return 5;   // SW
        } else if (azimuth >= 247.5 && azimuth < 292.5) {
            return 6;   // W
        } else {
            return 7;   // NW
        }
    }

    private void actualizaOrientacion(int direction){
        String imagen = "brujula/null.png";

        if(archivos.get(idx).get(2) != null) {
            int diferencia_circ = -1;
            int obj_direction = direcciones.indexOf(archivos.get(idx).get(2));

            if(obj_direction >= direction){
                diferencia_circ = obj_direction - direction;
            }
            else{
                diferencia_circ = direcciones.size() - direction + obj_direction;
            }

            imagen = "brujula/" + String.valueOf(diferencia_circ) + ".png";
        }

        try {
            InputStream inputStream_img = getAssets().open(imagen);
            imageView_bruj.setImageDrawable(Drawable.createFromStream(inputStream_img, null));
            inputStream_img.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}