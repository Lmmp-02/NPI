package com.example.qrgen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class IniciaSesion extends AppCompatActivity {
    TextView boton_registro;
    Button boton;
    EditText correo, password;
    FirebaseAuth auth;
    String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();   //Ocultamos barra superior

        setContentView(R.layout.activity_inicia_sesion);
        auth = FirebaseAuth.getInstance();
        boton = findViewById(R.id.boton_entrar);
        correo = findViewById(R.id.editTextNombre);
        password = findViewById(R.id.editTextPassword);
        boton_registro = findViewById(R.id.textoRegistro);

        //Al pulsar el boton de registro, vamos a la ventana de registro
        boton_registro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IniciaSesion.this, RegistraUsuario.class);
                startActivity(intent);
                finish();
            }
        });

        //Al pulsar el boton de iniciar sesion
        boton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Obtenemos el texto introducido por el usuario
                String username = correo.getText().toString();
                String pass = password.getText().toString();

                //Comprobamos que los campos esten rellenados correctamente
                if(TextUtils.isEmpty(username)){
                    Toast.makeText(IniciaSesion.this, "Introduzca un nombre de usuario.", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(pass)){
                    Toast.makeText(IniciaSesion.this, "Introduzca la contraseña.", Toast.LENGTH_SHORT).show();
                }else if(!username.matches(emailRegex)){
                    correo.setError("Correo no válido.");
                    Toast.makeText(IniciaSesion.this, "Introduzca una dirección de correo válida.", Toast.LENGTH_LONG).show();
                }else if(pass.length()<6){
                    password.setError("Contraseña demasiado corta");
                    Toast.makeText(IniciaSesion.this, "La contraseña debe tener más de 6 caracteres.", Toast.LENGTH_LONG).show();
                }
                else{
                    //Realizamos el login
                    auth.signInWithEmailAndPassword(username, pass).addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //Si va bien, pasamos a la app
                                if(task.isSuccessful()){
                                    try{
                                        Intent intent = new Intent(IniciaSesion.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }catch (Exception e){
                                        Toast.makeText(IniciaSesion.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                                //Si no, mostramos el error por pantalla
                                else{
                                    Toast.makeText(IniciaSesion.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    );
                }
            }
        });
    }
}