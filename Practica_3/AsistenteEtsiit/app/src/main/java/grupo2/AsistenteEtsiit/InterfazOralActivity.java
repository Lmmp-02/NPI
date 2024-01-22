package grupo2.AsistenteEtsiit;

import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.applozic.mobicomkit.api.account.register.RegistrationResponse;
import com.applozic.mobicomkit.api.conversation.Message;
import com.applozic.mobicomkit.api.conversation.MessageBuilder;
import com.applozic.mobicomkit.broadcast.AlEventManager;
//import com.example.qrgen.ui.main.SectionsPagerAdapter;
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

public class InterfazOralActivity extends AppCompatActivity implements KmPluginEventListener {
    public static final Integer RecordAudioRequestCode = 1;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference ref_usuario;
    private static final String CONVERSATION_ID_KEY = "conversationId";
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String CONVERSATION_CREATED_KEY = "conversationCreated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz_oral);
        getSupportActionBar().hide();   //Esconde la barra de arriba
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        //Comprobamos permisos. Si no los tiene, los pedimos
        if(ContextCompat.checkSelfPermission(InterfazOralActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
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
        Kommunicate.init(InterfazOralActivity.this, "1046f12744c6017fb944066df8190daeb");

        //Obtenemos los datos del usuario actual
        FirebaseUser user = auth.getCurrentUser();

        //Habilitamos TTS y STT
        KmPrefSettings.getInstance(InterfazOralActivity.this)
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
                    /*
                    Map<String, String> conv_metadata = new HashMap<>();
                    conv_metadata.put("Department" , "Mobility"); // This is an example of updating metadata.
                    conv_metadata.put("Designation" , "Software Engineer II"); // This is an example of updating metadata.
                    */
                    if (!isConversationCreated()) {
                        //Creamos la conversación
                        new KmConversationBuilder(InterfazOralActivity.this)
                                // Pass false if you would like to create new conversation every time user starts
                                // a conversation. This is true by default which means only one conversation will
                                // open for the user every time the user starts a conversation.
                                .setSingleConversation(false)
                                //.setConversationInfo(conv_metadata)
                                //Creamos y abrimos la conversacion
                                .launchConversation(new KmCallback() {
                                    @Override
                                    public void onSuccess(Object message) {
                                        //Obtenemos el id de la conversacion
                                        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putInt(CONVERSATION_ID_KEY, (Integer) message);
                                        editor.apply();
                                        //Registramos eventos
                                        AlEventManager.getInstance().registerPluginEventListener(InterfazOralActivity.this);
                                        setConversationCreated(true);
                                        //Mostramos info de la conversacion
                                        // Iterar sobre el Map utilizando un bucle for-each
                                        //Cierra la pantalla de carga cuando termina la conversación, volviendo al menú
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Object error) {
                                        Log.d("ConversationTest", "Error en creacion conversacion KM: " + error);
                                    }
                                });
                    }
                    //Si la conversacion esta creada, la volvemos a abrir
                    else{
                        int conversationId;
                        // Intenta obtener el identificador de conversación almacenado
                        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                        conversationId = preferences.getInt(CONVERSATION_ID_KEY, -1);
                        try {
                            KmConversationHelper.openConversation(InterfazOralActivity.this,
                                    true,
                                    conversationId,
                                    new KmCallback() {
                                        @Override
                                        public void onSuccess(Object message) {
                                            AlEventManager.getInstance().registerPluginEventListener(InterfazOralActivity.this);
                                            new MessageBuilder(context)
                                                    .setMessage("Hola de nuevo")
                                                    .setGroupId(conversationId)  //where 123456 is the conversationId.
                                                    .send();
                                            finish();
                                        }

                                        @Override
                                        public void onFailure(Object error) {

                                        }
                                    });
                        } catch (KmException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
                    // You can perform actions such as repeating the login call or throw an error message on failure
                    Log.d("Error en login KM", "Error : " + exception);
                }
            });




        }
        //Si el usuario no esta registrado, hacemos un chat como visitante
        /*
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
         */
    }
    private boolean isConversationCreated() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return preferences.getBoolean(CONVERSATION_CREATED_KEY, false);
    }
    private void setConversationCreated(boolean created) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(CONVERSATION_CREATED_KEY, created);
        editor.apply();
    }

    //--------------Métodos que implementan la interfaz de escucha----------------

    //Comprobamos si el mensaje da una orden
    @Override
    public void onMessageReceived(Message message) {
        String msg = message.getMessage();

        ////Primera opcion: Mensaje de redireccion a localizacion
        // Definir la expresión regular con grupos de captura para $origen y $destino
        String regex_localizacion = "(\\S+)\\s*-->\\s*(\\S+)";
        // Compilar la expresión regular
        Pattern pattern_localizacion = Pattern.compile(regex_localizacion);
        // Crear un objeto Matcher
        Matcher matcher_localizacion = pattern_localizacion.matcher(msg);

        // Si el bot pide localizacion
        if (matcher_localizacion.find()) {
            // Obtener los valores de $origen y $destino
            String origen = matcher_localizacion.group(1);
            String destino = matcher_localizacion.group(2);
            // Llamamos a la funcion de localizacion
            Toast.makeText(this,"Origen: "+origen+"\tDestino: "+destino, Toast.LENGTH_SHORT).show();
        }
        // Si el bot pide leer QR para comedor
        else if(msg.equals("Activando lector de QR...")) {
            // Llamamos a la funcion de escaneo de QR para comedores
            Intent intent = new Intent(InterfazOralActivity.this, LectorQRActivity.class);
            intent.putExtra("activity_base","InterfazOralActivity");
            startActivity(intent);
            Toast.makeText(this,"Ir a lectura de QR", Toast.LENGTH_SHORT).show();
        }
        // Si el bot pide leer QR para comedor
        else if(msg.equals("Activando NFC...")) {
            // Llamamos a la funcion de escaneo de NFC para comedores
            Intent intent = new Intent(InterfazOralActivity.this, LectorNFCActivity.class);
            intent.putExtra("activity_base","InterfazOralActivity");
            startActivity(intent);
            Toast.makeText(this,"Redirigiendo a NFC", Toast.LENGTH_SHORT).show();
        }
        // Si el bot pide salir del interfaz oral
        else if(msg.equals("Saliendo...")) {
            // Salimos del activity
            Toast.makeText(this,"Salida de interfaz oral", Toast.LENGTH_SHORT).show();
            Kommunicate.closeConversationScreen(InterfazOralActivity.this);
            finish();

        }
        else{
            //Toast.makeText(this,"No encontrada accion especial", Toast.LENGTH_SHORT).show();
        }

    }
    //Metodos que implementan la interfaz de escucha, pero que no usamos
    @Override
    public void onPluginLaunch() {
        //Toast.makeText(this,"Cosas especiales", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPluginDismiss() {
        //Toast.makeText(this,"Cosas especiales", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConversationResolved(Integer conversationId) {
        Toast.makeText(this,"Resolved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConversationRestarted(Integer conversationId) {
        Toast.makeText(this,"Restart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRichMessageButtonClick(Integer conversationId, String actionType, Object action) {
        // Toast.makeText(this,"Cosas especiales", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartNewConversation(Integer conversationId) {
        Toast.makeText(this,"Cosas especiales", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSubmitRatingClick(Integer conversationId, Integer rating, String feedback) {
        //Toast.makeText(this,"Cosas especiales", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMessageSent(Message message) {
        //Toast.makeText(this,"Cosas especiales", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackButtonClicked(boolean isConversationOpened) {
        Toast.makeText(this,"Cosas especiales", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttachmentClick(String attachmentType) {
        //Toast.makeText(this,"Cosas especiales", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFaqClick(String FaqUrl) {
        //Toast.makeText(this,"Cosas especiales", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationClick() {
        Toast.makeText(this,"Cosas especiales", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNotificationClick(Message message) {
        //Toast.makeText(this,"Cosas especiales", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onVoiceButtonClick(String action) {
        //Toast.makeText(this,"Cosas especiales", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRatingEmoticonsClick(Integer ratingValue) {
        //Toast.makeText(this,"Cosas especiales", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRateConversationClick() {
        //Toast.makeText(this,"Cosas especiales", Toast.LENGTH_SHORT).show();
    }


}