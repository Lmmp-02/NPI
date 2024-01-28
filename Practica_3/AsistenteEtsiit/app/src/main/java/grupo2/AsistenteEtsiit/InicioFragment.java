package grupo2.AsistenteEtsiit;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class InicioFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public InicioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        tabLayout = view.findViewById(R.id.fi_tablayout);
        viewPager = view.findViewById(R.id.fi_viewpager);

        tabLayout.setupWithViewPager(viewPager);

        FragmentManager fragmentManager = getChildFragmentManager();

        VPAdapter vpAdapter = new VPAdapter(fragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new WebFragment(), getResources().getString(R.string.fi_pagina_web));
        vpAdapter.addFragment(new HorarioFragment(), getResources().getString(R.string.fi_horario));

        viewPager.setAdapter(vpAdapter);

        return view;
    }
}