package grupo2.AsistenteEtsiit;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

public class ClasesDespachosFragment extends Fragment {

    private String origen;
    private int tipo; // 0 si se escoge clase, 1 si es despacho

    private String[] plantas_clases;
    private String[] plantas_despachos;

    public ClasesDespachosFragment(String un_origen) {
        origen = un_origen;
        tipo = -1;

        plantas_clases = new String[]{"0", "1", "2", "3", "A", "B", "-1"};
        plantas_despachos = new String[]{"2", "3", "4", "5"};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clases_despachos, container, false);

        NumberPicker np_planta = view.findViewById(R.id.cd_np_planta);
        NumberPicker np_numero = view.findViewById(R.id.cd_np_numero);

        Button boton_seleccionar = view.findViewById(R.id.cd_selecionar);
        Button boton_clase = view.findViewById(R.id.cd_b_clase);
        Button boton_despacho = view.findViewById(R.id.cd_b_despacho);

        np_planta.setEnabled(false);
        np_numero.setEnabled(false);
        boton_seleccionar.setEnabled(false);

        boton_clase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                np_planta.setDisplayedValues(null);
                np_planta.setMinValue(0);
                np_planta.setMaxValue(6);
                np_planta.setDisplayedValues(plantas_clases);

                tipo = 0;

                boton_seleccionar.setEnabled(true);
                boton_clase.setEnabled(false);

                np_planta.setEnabled(true);
                np_numero.setEnabled(true);

                np_numero.setMinValue(1);
                np_numero.setMaxValue(8);

                boton_despacho.setEnabled(true);
            }
        });

        boton_despacho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                np_planta.setDisplayedValues(null);
                np_planta.setMinValue(0);
                np_planta.setMaxValue(3);
                np_planta.setDisplayedValues(plantas_despachos);

                tipo = 1;

                boton_seleccionar.setEnabled(true);
                boton_despacho.setEnabled(false);

                np_planta.setEnabled(true);
                np_numero.setEnabled(true);

                np_numero.setMinValue(1);
                np_numero.setMaxValue(40);

                boton_clase.setEnabled(true);
            }
        });

        np_planta.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // Si se está seleccionando una clase
                if(tipo == 0){
                    if(plantas_clases[newVal].equals("-1") || plantas_clases[newVal].equals("A") || plantas_clases[newVal].equals("B")){
                        np_numero.setMinValue(1);
                        np_numero.setMaxValue(2);
                    }
                    else{
                        np_numero.setMinValue(1);
                        np_numero.setMaxValue(8);
                    }
                }
                else{
                    if(plantas_despachos[newVal].equals("5")){
                        np_numero.setMinValue(1);
                        np_numero.setMaxValue(1);
                    }
                    else {
                        np_numero.setMinValue(1);
                        np_numero.setMaxValue(40);
                    }
                }
            }
        });

        boton_seleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loc;

                if(tipo == 0){
                    loc = plantas_clases[np_planta.getValue()] + "." + String.valueOf(np_numero.getValue());
                }
                else{
                    loc = "D" + plantas_despachos[np_planta.getValue()] + "." + String.valueOf(np_numero.getValue());
                }

                if(origen == null){
                    addFragment(new SelectorRutaFragment(loc));
                }
                else{
                    if(origen.equals(loc)){
                        Toast.makeText(getActivity(), "El origen debe ser distinto al destino", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Intent intent = new Intent(requireContext(), MuestraRutaActivity.class);
                        intent.putExtra("origen", origen);
                        intent.putExtra("destino", loc);
                        startActivity(intent);
                    }
                }
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
}