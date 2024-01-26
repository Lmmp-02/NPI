package grupo2.AsistenteEtsiit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class ServiciosExternos extends Fragment {

    public ServiciosExternos() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_servicios_externos, container, false);

        ImageButton boton_secretaria = view.findViewById(R.id.se_secretaria);
        ImageButton boton_becas = view.findViewById(R.id.se_becas);
        ImageButton boton_csirc = view.findViewById(R.id.se_csirc);
        ImageButton boton_osl = view.findViewById(R.id.se_osl);
        ImageButton boton_citic = view.findViewById(R.id.se_citic);
        ImageButton boton_ceprud = view.findViewById(R.id.se_ceprud);

        boton_secretaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciaMaps("Hospital Real, Avenida del Hospicio, S/N CP:18071 Granada (Granada)");
            }
        });

        boton_becas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciaMaps("Quinto Centenario Avd. de Madrid S/N CP:18071 Granada (Granada)");
            }
        });

        boton_csirc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciaMaps("Centro de Servicios de Informática y Redes de Comunicaciones UGR, Granada");
            }
        });

        boton_osl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciaMaps("OSL, C. Real de Cartuja, 38, Albaicín, 18012 Granada");
            }
        });

        boton_citic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciaMaps("CITIC, Calle Periodista Rafael Gómez Montero, 2, Chana, 18014 Granada");
            }
        });

        boton_ceprud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciaMaps("CEPRUD, C. Real de Cartuja, 38, Albaicín, 18012 Granada");
            }
        });

        return view;
    }

    void iniciaMaps(String direccion){
        // Crear la Uri con la dirección
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(direccion));

        // Crear la intención
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps"); // Establecer el paquete de Google Maps

        // Verificar si Google Maps está instalado
        if (mapIntent.resolveActivity(requireContext().getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            // Mostrar mensaje si Google Maps no está instalado
            Toast.makeText(requireContext(), "No pudo iniciarse Maps", Toast.LENGTH_SHORT).show();
        }
    }
}