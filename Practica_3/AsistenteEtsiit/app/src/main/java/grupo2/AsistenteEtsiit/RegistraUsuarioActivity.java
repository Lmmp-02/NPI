package grupo2.AsistenteEtsiit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.List;

public class RegistraUsuarioActivity extends AppCompatActivity {
    TextView boton_login, boton_invitado, txtGrado, txtCurso;
    EditText usuario, correo, password, re_password;
    Spinner grado, curso;
    Button boton_registro;

    List<String> opcionesCurso;
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
        grado = findViewById(R.id.spinnerGrado);
        curso = findViewById(R.id.spinnerCurso);
        boton_login = findViewById(R.id.textoRegistro);
        boton_registro = findViewById(R.id.boton_entrar);
        txtGrado = findViewById(R.id.txtGrado);
        txtCurso = findViewById(R.id.txtCurso);

        // Define las opciones disponibles en el Spinner
        String[] opcionesGrado = {"Ingeniería Informática", "Ingeniería de Telecomunicaciones"};

        // Crea un ArrayAdapter usando las opciones y un layout predeterminado para los elementos del Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcionesGrado);

        // Especifica el layout a utilizar cuando aparece la lista de opciones
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Asigna el adaptador al Spinner
        grado.setAdapter(adapter);

        // Opciones iniciales para el segundo Spinner
        opcionesCurso = new ArrayList<>();
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcionesCurso);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        curso.setAdapter(adapter2);

        // Maneja el evento de selección del Spinner
        grado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedOption = opcionesGrado[position];
                txtGrado.setText(selectedOption);

                // Actualiza las opciones del segundo Spinner según la selección en el primer Spinner
                actualizarCursos(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Maneja el caso en el que no se selecciona nada (opcional)
            }
        });

        // Evento Spinner 2
        curso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedOption = opcionesCurso.get(position);
                txtCurso.setText(selectedOption);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Maneja el caso en el que no se selecciona nada (opcional)
            }
        });


        //Si se pulsa iniciar sesion, vamos a dicha actividad
        boton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(RegistraUsuario.this, IniciaSesion.class);
                //startActivity(intent);
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
                String grad = txtGrado.getText().toString();
                String grup = txtCurso.getText().toString();

                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(mail) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(pass2)){
                    Toast.makeText(RegistraUsuarioActivity.this, "Faltan campos obligatorios por rellenar", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(RegistraUsuarioActivity.this, "Introduzca una dirección de correo válida.", Toast.LENGTH_LONG).show();
                }
                else if(pass.length()<6){
                    password.setError("Contraseña demasiado corta.");
                    Toast.makeText(RegistraUsuarioActivity.this, "La contraseña debe tener más de 6 caracteres.", Toast.LENGTH_LONG).show();
                }
                else if(!pass.equals(pass2)){
                    password.setError("Las contraseñas no coinciden.");
                    Toast.makeText(RegistraUsuarioActivity.this, "Las contraseñas introducidas son diferentes", Toast.LENGTH_LONG).show();
                }
                else{
                    //Si los parametros son correctos, registramos el usuario en la base de datos de autenticación
                    auth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
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
                                                            Intent intent = new Intent(RegistraUsuarioActivity.this, MainActivity.class);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                        //Si no, mostramos el error por pantalla
                                                        else{
                                                            Toast.makeText(RegistraUsuarioActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                        );
                                    }
                                    //Si no, mostramos el error por pantalla
                                    else{
                                        Toast.makeText(RegistraUsuarioActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                    );
                }
            }
        });

    }

    // Método para actualizar las opciones del segundo Spinner
    private void actualizarCursos(int position) {
        // Lógica para determinar las opciones del segundo Spinner según la selección en el primer Spinner
        // Aquí puedes agregar tu lógica específica para cada posición en el primer Spinner

        List<String> nuevasOpciones = new ArrayList<>();
        if (position == 0) {
            nuevasOpciones.add("4CSI");
            nuevasOpciones.add("4TI");
            nuevasOpciones.add("4IS");
            nuevasOpciones.add("4IC");
            nuevasOpciones.add("4SI");
        } else{
            nuevasOpciones.add("4A");
            nuevasOpciones.add("4B");
        }

        opcionesCurso = nuevasOpciones;

        // Actualiza las opciones del segundo Spinner
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcionesCurso);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        curso.setAdapter(adapter2);
    }
}


