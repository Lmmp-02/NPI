package com.example.qrgen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.applozic.mobicomkit.api.account.register.RegistrationResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import io.kommunicate.KmConversationBuilder;
import io.kommunicate.KmConversationHelper;
import io.kommunicate.KmException;
import io.kommunicate.Kommunicate;
import io.kommunicate.callbacks.KMLoginHandler;
import io.kommunicate.callbacks.KmCallback;
import io.kommunicate.users.KMUser;

public class InterfazOral extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference ref_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz_oral);
        getSupportActionBar().hide();   //Esconde la barra de arriba
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        //Inicializamos Kommunicate
        Kommunicate.init(InterfazOral.this, "1046f12744c6017fb944066df8190daeb");

        //Obtenemos los datos del usuario actual
        FirebaseUser user = auth.getCurrentUser();

        //Si el usuario esta registrado
        if(user != null){
            //Obtenemos info del usuario
            String uid = auth.getUid();
            ref_usuario = database.getReference("user").child(uid);


            //Creamos usuario en Kommunicate
            KMUser kmuser = new KMUser();
            kmuser.setUserId(uid);  // You can set any unique user ID
            //user.setDisplayName(<DISPLAY_NAME>); // Pass the display name of the user

            Map<String, String> metadata = new HashMap<>();
            metadata.put("Department" , "Mobility"); // This is an example of updating metadata.
            metadata.put("Designation" , "Software Engineer II"); // This is an example of updating metadata.

            kmuser.setMetadata(metadata);

            //Inicio de sesión del usuario
            Kommunicate.login(this, kmuser, new KMLoginHandler() {
                @Override
                public void onSuccess(RegistrationResponse registrationResponse, Context context) {
                    //Creamos la conversación
                    new KmConversationBuilder(InterfazOral.this)
                            // Pass false if you would like to create new conversation every time user starts
                            // a conversation. This is true by default which means only one conversation will
                            // open for the user every time the user starts a conversation.
                            .setSingleConversation(false)
                            .createConversation(new KmCallback() {
                                @Override
                                public void onSuccess(Object message) {
                                    Integer conversationId = (Integer) message;
                                    //Abrimos la conversacion
                                    try {
                                        KmConversationHelper.openConversation(InterfazOral.this,
                                                true,
                                                conversationId, null);
                                    } catch (KmException e) {
                                        throw new RuntimeException(e);
                                    }
                                    //Cierra la pantalla de carga cuando termina la conversación, volviendo al menú
                                    finish();
                                }

                                @Override
                                public void onFailure(Object error) {
                                    Log.d("ConversationTest", "Error en creacion conversacion KM: " + error);
                                }
                            });

                }

                @Override
                public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
                    // You can perform actions such as repeating the login call or throw an error message on failure
                    Log.d("Error en login KM", "Error : " + exception);
                }
            });




        }
        //Si el usuario no esta registrado, hacemos un chat como visitante
        else {

            Kommunicate.loginAsVisitor(this, new KMLoginHandler() {
                @Override
                public void onSuccess(RegistrationResponse registrationResponse, Context context) {
                    // You can perform operations such as opening the conversation, creating a new conversation or update user details on success
                    // A completar
                }

                @Override
                public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
                    // You can perform actions such as repeating the login call or throw an error message on failure
                    // A completar
                }
            });
        }

    }
}