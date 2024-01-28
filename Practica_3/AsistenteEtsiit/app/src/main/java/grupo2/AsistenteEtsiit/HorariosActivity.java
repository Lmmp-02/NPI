package grupo2.AsistenteEtsiit;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class HorariosActivity extends AppCompatActivity {

    Button buscarHorario;
    ImageView imagenHorario;
    private Spinner spinnerCurso, spinnerGrupo, spinnerCuatri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horarios);

        buscarHorario = findViewById(R.id.button_horario);
        imagenHorario = findViewById(R.id.image_horario);
        // Inicializar Spinners
        spinnerCurso = findViewById(R.id.spinnerCurso);
        spinnerGrupo = findViewById(R.id.spinnerGrupo);
        spinnerCuatri = findViewById(R.id.spinnerCuatri);

        // Configurar adaptador para spinnerCurso
        ArrayAdapter<CharSequence> adapterCurso = ArrayAdapter.createFromResource(this,
                R.array.cursos_array, android.R.layout.simple_spinner_item);
        adapterCurso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCurso.setAdapter(adapterCurso);

        // Configurar adaptador para spinnerCuatrimestre
        ArrayAdapter<CharSequence> adapterCuatrimestre = ArrayAdapter.createFromResource(this,
                R.array.cuatrimestres_array, android.R.layout.simple_spinner_item);
        adapterCuatrimestre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCuatri.setAdapter(adapterCuatrimestre);

        // Escuchador para spinnerCurso
        spinnerCurso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                actualizarSpinnerGrupo(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Configuración del botón para buscar horario
        buscarHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Referencia a Firebase Storage
                imagenHorario.setImageResource(R.drawable.cargando);
                String cursoSeleccionado = spinnerCurso.getSelectedItem().toString();
                String grupoSeleccionado = spinnerGrupo.getSelectedItem().toString();
                String cuatriSeleccionado = spinnerCuatri.getSelectedItem().toString();
                if(cuatriSeleccionado.charAt(0) == '1'){
                    cuatriSeleccionado = "1";
                }
                else {
                    cuatriSeleccionado = "2";
                }

                String rutaImagen = "horarios/" + cursoSeleccionado + grupoSeleccionado + cuatriSeleccionado + ".png";
                System.out.println(rutaImagen);
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();
                StorageReference imageRef = storageRef.child(rutaImagen);

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
        });
    }

    private void actualizarSpinnerGrupo(int cursoPos) {
        int arrayId;
        switch (cursoPos) {
            case 0: // Curso 1
                arrayId = R.array.grupos_curso_1;
                break;
            case 1: // Curso 2
                arrayId = R.array.grupos_curso_2;
                break;
            case 2: // Curso 3
                arrayId = R.array.grupos_curso_3;
                break;
            case 3: // Curso 4
                arrayId = R.array.grupos_curso_4;
                break;
            default:
                arrayId = R.array.grupos_curso_1; // Valor por defecto
                break;
        }

        ArrayAdapter<CharSequence> adapterGrupo = ArrayAdapter.createFromResource(this,
                arrayId, android.R.layout.simple_spinner_item);
        adapterGrupo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGrupo.setAdapter(adapterGrupo);
    }
}
