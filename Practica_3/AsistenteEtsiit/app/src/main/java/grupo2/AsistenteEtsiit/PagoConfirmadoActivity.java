package grupo2.AsistenteEtsiit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class PagoConfirmadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_confirmado);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                //Vemos cual es la actividad a la que hay que volver
                SharedPreferences preferences = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
                //Si es la interfaz oral la que está activa, volvemos a ella
                if(preferences.getBoolean("interfazOralActiva", false)) {
                    Intent intent = new Intent(PagoConfirmadoActivity.this, InterfazOralActivity.class);
                    //Limpia la pila de actividades hasta el MainActivity y crea una nueva tarea si es necesario.
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                //Si no, volvemos al menu principal
                else{
                    Intent intent = new Intent(PagoConfirmadoActivity.this, MainActivity.class);
                    //Limpia la pila de actividades hasta el MainActivity y crea una nueva tarea si es necesario.
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                finish();
            }
        }, 4000);
    }
}