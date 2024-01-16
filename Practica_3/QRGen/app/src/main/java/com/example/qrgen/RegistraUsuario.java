package com.example.qrgen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class RegistraUsuario extends AppCompatActivity {
    TextView boton_login, boton_invitado;
    EditText usuario, correo, password, re_password, grado, grupo;
    Button boton_registro;
    FirebaseAuth auth;
    String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra_usuario);
        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();

        auth = FirebaseAuth.getInstance();
        usuario = findViewById(R.id.editTextNombre);
        correo = findViewById(R.id.editTextCorreo);
        password = findViewById(R.id.editTextPassword);
        re_password = findViewById(R.id.editTextPassword2);
        grado = findViewById(R.id.editTextGrado);
        grupo = findViewById(R.id.editTextCurso);
        boton_login = findViewById(R.id.textoRegistro);
        boton_invitado = findViewById(R.id.textoInvitado);
        boton_registro = findViewById(R.id.boton_entrar);


        //Si se pulsa iniciar sesion, vamos a dicha actividad
        boton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistraUsuario.this, IniciaSesion.class);
                startActivity(intent);
                finish();
            }
        });

        //Si se pulsa registrarse
        boton_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user =  usuario.getText().toString();
                String pass = password.getText().toString();
                String pass2 = re_password.getText().toString();
                String mail = correo.getText().toString();
                String grad = grado.getText().toString();
                String grup = grupo.getText().toString();

                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(mail) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(pass2)){
                    Toast.makeText(RegistraUsuario.this, "Faltan campos obligatorios por rellenar", Toast.LENGTH_SHORT).show();
                    if(TextUtils.isEmpty(user))
                        usuario.setError("Campo obligatorio");
                    if(TextUtils.isEmpty(mail))
                        correo.setError("Campo obligatorio");
                    if(TextUtils.isEmpty(pass))
                        password.setError("Campo obligatorio");
                    if(TextUtils.isEmpty(pass2))
                        re_password.setError("Campo obligatorio");
                }
                else if(!mail.matches(emailRegex)){
                    correo.setError("Correo no válido.");
                    Toast.makeText(RegistraUsuario.this, "Introduzca una dirección de correo válida.", Toast.LENGTH_LONG).show();
                }
                else if(pass.length()<6){
                    password.setError("Contraseña demasiado corta.");
                    Toast.makeText(RegistraUsuario.this, "La contraseña debe tener más de 6 caracteres.", Toast.LENGTH_LONG).show();
                }
                else if(!pass.equals(pass2)){
                    password.setError("Las contraseñas no coinciden.");
                    Toast.makeText(RegistraUsuario.this, "Las contraseñas introducidas son diferentes", Toast.LENGTH_LONG).show();
                }
                else{
                    //Si los parametros son correctos, registramos el usuario en la base de datos de autenticación
                    auth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                System.out.println("COMPLETADO REGISTRO\n");

                                //Si se registra correctamente, guardamos en la base de datos el objeto del usuario
                                if(task.isSuccessful()){
                                    String id = task.getResult().getUser().getUid();  //Obtenemos id del usuario
                                    DatabaseReference ref = database.getReference().child("user").child(id);
                                    //Creamos objeto Usuario
                                    Usuario u = new Usuario(id, user, mail, pass, grad, grup);
                                    //Subimos el objeto a la base de datos
                                    ref.setValue(u).addOnCompleteListener(
                                        new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task){
                                                if(task.isSuccessful()){
                                                    Intent intent = new Intent(RegistraUsuario.this, MainActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                                //Si no, mostramos el error por pantalla
                                                else{
                                                    Toast.makeText(RegistraUsuario.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    );
                                }
                                //Si no, mostramos el error por pantalla
                                else{
                                    Toast.makeText(RegistraUsuario.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    );
                }
            }
        });

    }

}

