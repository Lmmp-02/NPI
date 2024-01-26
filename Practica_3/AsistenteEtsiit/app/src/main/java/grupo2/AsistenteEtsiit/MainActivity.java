package grupo2.AsistenteEtsiit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import grupo2.AsistenteEtsiit.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final int CODIGO_INTERFAZ_ORAL = 10;
    ActivityMainBinding binding;
    private BottomNavigationView bottomNavigationView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        getSupportActionBar().hide();   //Esconde la barra de arriba (no le gusta a nadie)

        replaceFragment(new InicioFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.inicio){
                replaceFragment(new InicioFragment());
            }
            else if(item.getItemId() == R.id.comedor){
                replaceFragment(new PagoComidaFragment());
            }
            else if(item.getItemId() == R.id.localizacion){
                replaceFragment(new LocalizacionFragment());
            }
            else if(item.getItemId() == R.id.asistente){
                replaceFragment(new Fragment());
                Intent intent = new Intent(MainActivity.this, InterfazOralActivity.class);
                startActivityForResult(intent, CODIGO_INTERFAZ_ORAL);
            }
            else if(item.getItemId() == R.id.salir){
                Intent intent = new Intent(MainActivity.this, IniciaSesionActivity.class);
                startActivity(intent);
                finish();
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        // Borramos la pila de fragmentos actual
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        // Mostramos el fragmento deseado
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

     void addFragment(Fragment fragment){
         FragmentManager fragmentManager = getSupportFragmentManager();
         FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

         // Ocultar fragmentos anteriores en la pila de retroceso
         for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
             Fragment previousFragment = fragmentManager.findFragmentByTag(Integer.toString(i));
             if (previousFragment != null) {
                 fragmentTransaction.hide(previousFragment);
             }
         }

         // Reemplazar el fragmento actual
         fragmentTransaction.replace(R.id.frame_layout, fragment, Integer.toString(fragmentManager.getBackStackEntryCount()));

         //Guardamos el fragmento anterior en la pila
         fragmentTransaction.addToBackStack(null);
         fragmentTransaction.commit();
     }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Ponemos que el boton seleccionado sea el de inicio
        if (requestCode == CODIGO_INTERFAZ_ORAL) {
            bottomNavigationView.setSelectedItemId(R.id.inicio);
        }
        //Guardamos que el llamador actual es main
        SharedPreferences preferences = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("interfazOralActiva", false);
        editor.apply();
    }
}
