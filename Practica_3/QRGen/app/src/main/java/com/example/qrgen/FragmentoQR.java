package com.example.qrgen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentoQR#newInstance} factory method to
 * create an instance of this fragment.
 */

// Tutoriales de apoyo
// https://www.youtube.com/watch?v=elZP3JFFdVk&ab_channel=C%C3%B3digosdeProgramaci%C3%B3n-MR
// https://medium.com/@dev.jeevanyohan/zxing-qr-code-scanner-android-implementing-in-activities-fragment-custom-colors-faa68bfc761d

public class FragmentoQR extends Fragment {

    Button btnEscanerQR;
    String resultado;

    public FragmentoQR() {
        // Required empty public constructor
    }

    public static FragmentoQR newInstance() {
        FragmentoQR fragment = new FragmentoQR();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qr, container, false);

        btnEscanerQR = view.findViewById(R.id.btnEscanerQR);

        btnEscanerQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarEscaneoQR();
            }
        });

        return view;
    }

    private void iniciarEscaneoQR(){
        IntentIntegrator integrador = IntentIntegrator.forSupportFragment(FragmentoQR.this);
        integrador.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrador.setPrompt("Lector - QR");
        integrador.setOrientationLocked(false);
        integrador.setBeepEnabled(true);
        integrador.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null){ //Se lee algo o se cancela la lectura
            if(result.getContents() == null) Toast.makeText(this.getActivity(), "Escaneo cancelado", Toast.LENGTH_LONG).show();
            else{
                resultado = result.getContents();
                Toast.makeText(this.getActivity(), resultado, Toast.LENGTH_LONG).show();
            }
        }
        else super.onActivityResult(requestCode, resultCode, data);
    }
}