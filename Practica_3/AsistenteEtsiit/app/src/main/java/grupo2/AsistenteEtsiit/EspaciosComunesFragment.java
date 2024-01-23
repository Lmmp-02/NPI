package grupo2.AsistenteEtsiit;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class EspaciosComunesFragment extends Fragment {
    private String origen;

    public EspaciosComunesFragment(String un_origen) {
        origen = un_origen;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_espacios_comunes, container, false);

        ImageButton boton_totem = view.findViewById(R.id.ec_totem);
        ImageButton boton_cafeteria = view.findViewById(R.id.ec_cafeteria);
        ImageButton boton_comedor = view.findViewById(R.id.ec_comedor);
        ImageButton boton_biblioteca = view.findViewById(R.id.ec_biblioteca);
        ImageButton boton_secretaria = view.findViewById(R.id.ec_secretaria);
        ImageButton boton_salondeactos = view.findViewById(R.id.ec_salondeactos);
        ImageButton boton_salondegrados = view.findViewById(R.id.ec_salondegrados);

        boton_totem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                botonPulsado(boton_totem, "0_totem");
            }
        });

        boton_cafeteria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                botonPulsado(boton_cafeteria, "Cafeteria");
            }
        });

        boton_comedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                botonPulsado(boton_comedor, "Comedor");
            }
        });

        boton_biblioteca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                botonPulsado(boton_biblioteca, "Biblioteca");
            }
        });

        boton_secretaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                botonPulsado(boton_secretaria, "Secretaria");
            }
        });

        boton_salondeactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                botonPulsado(boton_salondeactos, "SalonDeActos");
            }
        });

        boton_salondegrados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                botonPulsado(boton_salondegrados, "SalonDeGrados");
            }
        });

        return view;
    }

    private void addFragment(Fragment fragment){
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            MainActivity actividad = (MainActivity) getActivity();
            actividad.addFragment(fragment);
        }
    }

    private void botonPulsado(ImageButton boton, String loc){
        boton.setBackgroundResource(R.color.white);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Si se está seleccionando el origen
                if(origen == null){
                    addFragment(new SelectorRutaFragment(loc));
                }
                else{
                    Intent intent = new Intent(requireContext(), MuestraRutaActivity.class);
                    intent.putExtra("origen", origen);
                    intent.putExtra("destino", loc);
                    startActivity(intent);
                }

                boton.setBackgroundResource(R.color.etsiit);
            }
        }, 400);
    }
}