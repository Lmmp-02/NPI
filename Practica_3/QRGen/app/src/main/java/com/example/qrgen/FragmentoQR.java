package com.example.qrgen;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

// Tutorial de apoyo
// https://www.youtube.com/watch?v=elZP3JFFdVk&ab_channel=C%C3%B3digosdeProgramaci%C3%B3n-MR

public class FragmentoQR extends Fragment {

    Button btnEscanerQR;
    EditText txtResultado;

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
        txtResultado = view.findViewById(R.id.txtResultado);

        btnEscanerQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrador = new IntentIntegrator(FragmentoQR.this.getActivity());
                integrador.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrador.setPrompt("Lector - CDP");
                integrador.setCameraId(0); //0 -> Cámara trasera
                integrador.setBeepEnabled(true);
                integrador.setBarcodeImageEnabled(true);
                integrador.initiateScan();
            }
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null){ //Se lee algo o se cancela la lectura
            if(result.getContents() == null) Toast.makeText(this.getActivity(), "Escaneo cancelado", Toast.LENGTH_LONG).show();
            else{
                Toast.makeText(this.getActivity(), result.getContents(), Toast.LENGTH_LONG).show();
                txtResultado.setText(result.getContents());
            }
        }
        else super.onActivityResult(requestCode, resultCode, data);
    }
}