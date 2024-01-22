package grupo2.AsistenteEtsiit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/*
Pantalla inicial de carga :)
 */
public class BienvenidaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);
        getSupportActionBar().hide();   //Esconde la barra de arriba
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent intent = new Intent(BienvenidaActivity.this, IniciaSesionActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}
