package grupo2.AsistenteEtsiit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import android.widget.Spinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.webkit.WebView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

public class HorarioFragment extends Fragment {
    Button buscarHorario;
    ImageView imagenHorario;

    NumberPicker numberPickerGrado;
    NumberPicker numberPickerCurso;
    NumberPicker numberPickerCuatri;

    String[] grados = {"Ingeniería Informática", "Ingeniería de Telecomunicaciones"};
    String[] grados_mostr = {"Informática", "Telecomunicaciones"};
    String[] grados_map = {"inf", "tel"};
    String[] cursos_inf = {"1A", "1B", "2A", "2B", "3A", "3B", "4CSI", "4IC", "4IS", "4SI", "4TI"};
    String[] cursos_tel = {"4A", "4B"};
    String[] cuatrimestres = {"Primero", "Segundo"};

    public HorarioFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_horario, container, false);

        buscarHorario = view.findViewById(R.id.fh_buscar);
        imagenHorario = view.findViewById(R.id.fh_imagen);
        numberPickerGrado = view.findViewById(R.id.fh_np_grado);
        numberPickerCurso = view.findViewById(R.id.fh_np_curso);
        numberPickerCuatri = view.findViewById(R.id.fh_np_cuatri);

        numberPickerGrado.setDisplayedValues(null);
        numberPickerGrado.setMinValue(0);
        numberPickerGrado.setMaxValue(grados_mostr.length-1);
        numberPickerGrado.setDisplayedValues(grados_mostr);

        numberPickerCuatri.setDisplayedValues(null);
        numberPickerCuatri.setMinValue(0);
        numberPickerCuatri.setMaxValue(cuatrimestres.length-1);
        numberPickerCuatri.setDisplayedValues(cuatrimestres);

        actualizarCurso();

        numberPickerGrado.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                actualizarCurso();
            }
        });

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            String userId = currentUser.getUid();
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

            mDatabase.child("user").child(userId).child("grado").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    else {
                        boolean encontrado = false;

                        for (int i = 0; i < grados.length && !encontrado; ++i){
                            if (grados[i].equals(String.valueOf(task.getResult().getValue()))) {
                                numberPickerGrado.setValue(i);
                                actualizarCurso();
                                encontrado = true;
                            }
                        }
                    }
                }
            });

            mDatabase.child("user").child(userId).child("curso").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    else {
                        boolean encontrado = false;

                        for (int i = 0; i < numberPickerCurso.getDisplayedValues().length && !encontrado; ++i){
                            if (numberPickerCurso.getDisplayedValues()[i].equals(String.valueOf(task.getResult().getValue()))) {
                                numberPickerCurso.setValue(i);
                                encontrado = true;
                                actualizarImagen();
                            }
                        }
                    }
                }
            });
        }

        // Configuración del botón para buscar horario
        buscarHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarImagen();
            }
        });

        return view;
    }

    private void actualizarCurso() {
        numberPickerCurso.setDisplayedValues(null);
        numberPickerCurso.setMinValue(0);

        if(numberPickerGrado.getValue() == 0){
            numberPickerCurso.setMaxValue(cursos_inf.length-1);
            numberPickerCurso.setDisplayedValues(cursos_inf);
        }
        else{
            numberPickerCurso.setMaxValue(cursos_tel.length-1);
            numberPickerCurso.setDisplayedValues(cursos_tel);
        }
    }

    private void actualizarImagen(){
        // Referencia a Firebase Storage
        imagenHorario.setImageResource(R.drawable.cargando);

        String grado = grados_map[numberPickerGrado.getValue()];
        String curso = numberPickerCurso.getDisplayedValues()[numberPickerCurso.getValue()];
        String cuatri = String.valueOf(numberPickerCuatri.getValue()+1);

        String rutaImagen = "horarios/" + grado + curso + cuatri + ".png";
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child(rutaImagen);

        // Obtener metadatos del archivo
        imageRef.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata) {
                // Obtener la imagen
                final long ONE_MEGABYTE = 1024 * 1024;
                imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        imagenHorario.setImageBitmap(bitmap);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                imagenHorario.setImageResource(R.drawable.nodisp);
            }
        });
    }
}