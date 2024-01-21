package com.example.qrgen;

import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.applozic.mobicomkit.api.account.register.RegistrationResponse;
import com.applozic.mobicomkit.api.conversation.Message;
import com.applozic.mobicomkit.broadcast.AlEventManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.kommunicate.KmConversationBuilder;
import io.kommunicate.KmConversationHelper;
import io.kommunicate.KmException;
import io.kommunicate.Kommunicate;
import io.kommunicate.callbacks.KMLoginHandler;
import io.kommunicate.callbacks.KmCallback;
import io.kommunicate.callbacks.KmPluginEventListener;
import io.kommunicate.users.KMUser;
import com.applozic.mobicomkit.uiwidgets.kommunicate.KmPrefSettings;

public class InterfazOral extends AppCompatActivity implements KmPluginEventListener {
    public static final Integer RecordAudioRequestCode = 1;
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

        //Comprobamos permisos. Si no los tiene, los pedimos
        if(ContextCompat.checkSelfPermission(InterfazOral.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},RecordAudioRequestCode);
            }
        }
        //Si tiene los permisos, pasamos a la creacion del chat
        else{
            creaInterfazOral();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RecordAudioRequestCode && grantResults.length > 0 ){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this,"Permiso concedido.", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this,"Microfono apagado. Solo disponible chat de texto.", Toast.LENGTH_LONG).show();
        }
        creaInterfazOral();
    }

    public void creaInterfazOral(){
        //Inicializamos Kommunicate
        Kommunicate.init(InterfazOral.this, "1046f12744c6017fb944066df8190daeb");

        //Obtenemos los datos del usuario actual
        FirebaseUser user = auth.getCurrentUser();

        //Habilitamos TTS y STT
        KmPrefSettings.getInstance(InterfazOral.this)
                .enableSpeechToText(true) // Habilita el microfono para escribir mensajes
                .enableTextToSpeech(true) // Al recibir mensajes, se leen en voz alta
                //.setSendMessageOnSpeechEnd(true) //Permite que se envie el mensaje automaticamente al terminar de hablar
                .setSpeechToTextLanguage("es-ES") // indicamos idioma de STT
                .setTextToSpeechLanguage("es-ES"); // indicamos idioma de TTS


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
                    Map<String, String> conv_metadata = new HashMap<>();
                    conv_metadata.put("Department" , "Mobility"); // This is an example of updating metadata.
                    conv_metadata.put("Designation" , "Software Engineer II"); // This is an example of updating metadata.
                    //Creamos la conversación
                    new KmConversationBuilder(InterfazOral.this)
                            // Pass false if you would like to create new conversation every time user starts
                            // a conversation. This is true by default which means only one conversation will
                            // open for the user every time the user starts a conversation.
                            .setSingleConversation(false)
                            .setConversationInfo(conv_metadata)
                            //Creamos y abrimos la conversacion
                            .launchConversation(new KmCallback() {
                                @Override
                                public void onSuccess(Object message) {
                                    //Obtenemos el id de la conversacion
                                    Integer conversationId = (Integer) message;

                                    //Registramos eventos
                                    AlEventManager.getInstance().registerPluginEventListener(InterfazOral.this);
                                    //Mostramos info de la conversacion
                                    // Iterar sobre el Map utilizando un bucle for-each
                                    /*
                                    for (Map.Entry<String, String> entry : Kommunicate.conv()) {
                                        String clave = entry.getKey();
                                        String valor = entry.getValue();
                                        System.out.println("Clave: " + clave + ", Valor: " + valor);
                                    }
                                    */
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

    //Métodos que implementan la interfaz de escucha

    //Comprobamos si el mensaje da una orden
    @Override
    public void onMessageReceived(Message message) {
        String msg = message.getMessage();

        // Definir la expresión regular con grupos de captura para $origen y $destino
        String regex = "(\\S+)\\s*-->\\s*(\\S+)";

        // Compilar la expresión regular
        Pattern pattern = Pattern.compile(regex);

        // Crear un objeto Matcher
        Matcher matcher = pattern.matcher(msg);

        // Verificar si se encuentra la coincidencia
        if (matcher.find()) {
            // Obtener los valores de $origen y $destino
            String origen = matcher.group(1);
            String destino = matcher.group(2);

            // Imprimir los valores
            System.out.println("Origen: " + origen);
            System.out.println("Destino: " + destino);

            Toast.makeText(this,"Origen: "+origen+"\tDestino: "+destino, Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"No encontrada accion especial", Toast.LENGTH_SHORT).show();
        }

    }
    //Metodos que implementan la interfaz de escucha, pero que no se implementan
    @Override
    public void onPluginLaunch() {

    }

    @Override
    public void onPluginDismiss() {

    }

    @Override
    public void onConversationResolved(Integer conversationId) {

    }

    @Override
    public void onConversationRestarted(Integer conversationId) {

    }

    @Override
    public void onRichMessageButtonClick(Integer conversationId, String actionType, Object action) {

    }

    @Override
    public void onStartNewConversation(Integer conversationId) {

    }

    @Override
    public void onSubmitRatingClick(Integer conversationId, Integer rating, String feedback) {

    }

    @Override
    public void onMessageSent(Message message) {

    }
    @Override
    public void onBackButtonClicked(boolean isConversationOpened) {

    }

    @Override
    public void onAttachmentClick(String attachmentType) {

    }

    @Override
    public void onFaqClick(String FaqUrl) {

    }

    @Override
    public void onLocationClick() {

    }

    @Override
    public void onNotificationClick(Message message) {

    }

    @Override
    public void onVoiceButtonClick(String action) {

    }

    @Override
    public void onRatingEmoticonsClick(Integer ratingValue) {

    }

    @Override
    public void onRateConversationClick() {

    }


}