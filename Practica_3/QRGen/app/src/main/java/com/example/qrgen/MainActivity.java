package com.example.qrgen;

import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor pressure;

    EditText edit_input;
    Button bt_generate;
    ImageView iv_qr;
    EditText presion;

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
}
