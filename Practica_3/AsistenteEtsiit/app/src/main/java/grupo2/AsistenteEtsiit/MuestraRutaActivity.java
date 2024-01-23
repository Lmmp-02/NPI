package grupo2.AsistenteEtsiit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MuestraRutaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra_ruta);
        getSupportActionBar().hide();   //Esconde la barra de arriba (no le gusta a nadie)

        String origen = getIntent().getStringExtra("origen");
        String destino = getIntent().getStringExtra("destino");


        TextView textView1 = findViewById(R.id.mr_text1);
        TextView textView2 = findViewById(R.id.mr_text2);

        textView1.setText(origen);
        textView2.setText(destino);
    }
}