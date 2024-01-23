package grupo2.AsistenteEtsiit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.widget.Button;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;

public class LocalizacionFragment extends Fragment {

    public LocalizacionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_localizacion, container, false);

        Button boton_qr = view.findViewById(R.id.boton_qr);
        Button boton_mano = view.findViewById(R.id.boton_mano);

        boton_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        boton_mano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new SelectorRutaFragment(null));
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