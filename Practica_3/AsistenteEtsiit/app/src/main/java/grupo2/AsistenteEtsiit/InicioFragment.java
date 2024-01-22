package grupo2.AsistenteEtsiit;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InicioFragment extends Fragment {
    private WebView webView;

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

        // Encuentra el WebView en tu diseño
        webView = view.findViewById(R.id.activity_webview);

        // Configura un cliente de WebView para manejar las redirecciones y cargas de páginas
        webView.setWebViewClient(new WebViewClient());

        // Habilita la configuración para JavaScript (si es necesario)
        webView.getSettings().setJavaScriptEnabled(true);

        // Carga la URL deseada
        webView.loadUrl("https://etsiit.ugr.es/");

        return view;
    }
}