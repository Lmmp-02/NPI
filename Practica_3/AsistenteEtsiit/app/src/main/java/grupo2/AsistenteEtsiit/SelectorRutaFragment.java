package grupo2.AsistenteEtsiit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.NumberPicker;

import com.google.android.material.tabs.TabLayout;

public class SelectorRutaFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private String origen;   // 0 para origen, 1 para destino

    public SelectorRutaFragment(String un_origen) {
        origen = un_origen;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selector_ruta, container, false);

        TextView textView = view.findViewById(R.id.sr_textView);

        if(origen != null){
            textView.setText(R.string.SelectorRuta_cabecera_de);
        }

        tabLayout = view.findViewById(R.id.sr_tablayout);
        viewPager = view.findViewById(R.id.sr_viewpager);

        tabLayout.setupWithViewPager(viewPager);

        FragmentManager fragmentManager = getChildFragmentManager();

        VPAdapter vpAdapter = new VPAdapter(fragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new EspaciosComunesFragment(origen), getResources().getString(R.string.SelectorRuta_comunes));
        vpAdapter.addFragment(new ClasesDespachosFragment(origen), getResources().getString(R.string.SelectorRuta_clasesdesp));

        viewPager.setAdapter(vpAdapter);

        return view;
    }
}