package grupo2.AsistenteEtsiit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class LectorQRActivity extends AppCompatActivity {
    String resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iniciarEscaneoQR();
    }

    private void iniciarEscaneoQR(){
        IntentIntegrator integrador = new IntentIntegrator(LectorQRActivity.this);
        integrador.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrador.setPrompt("Lector - QR");
        integrador.setOrientationLocked(false);
        integrador.setBeepEnabled(true);
        integrador.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null){ //Se lee algo o se cancela la lectura
            if(result.getContents() == null){
                Toast.makeText(this, "Escaneo cancelado", Toast.LENGTH_LONG).show();
            }
            else{
                resultado = result.getContents();
                Toast.makeText(this, resultado, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(this, PantallaPagoActivity.class);

                // Pasar el resultado del escaneo como extra al Intent
                intent.putExtra("RESULTADO_QR", resultado);

                startActivity(intent);
            }
            finish();
        }
        else super.onActivityResult(requestCode, resultCode, data);
    }
}