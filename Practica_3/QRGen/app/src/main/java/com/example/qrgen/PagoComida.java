package com.example.qrgen;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.qrgen.ui.main.SectionsPagerAdapter;
import com.example.qrgen.databinding.ActivityPagoComidaBinding;

public class PagoComida extends AppCompatActivity {

    private ActivityPagoComidaBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPagoComidaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

    }
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
