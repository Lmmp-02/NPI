package grupo2.AsistenteEtsiit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Estructura que almacena una localización junto con el coste de llegar a ella
class Localizacion implements Comparable<Localizacion>{
    String nombre;
    int coste;
    String orientacion;

    Localizacion(String nombre, int coste, String orientacion){
        this.nombre = nombre;
        this.coste = coste;
        this.orientacion = orientacion;
    }

    // Comparador de localizaciones en base a su coste
    @Override
    public int compareTo(Localizacion otra) {
        return Integer.compare(this.coste, otra.coste);
    }
}

// Modificación de Localización para guardar un puntero a su Localización padre
class NodoLocalizacion extends Localizacion{
    NodoLocalizacion padre;

    NodoLocalizacion(String nombre, int coste, String orientacion, NodoLocalizacion padre){
        super(nombre, coste, orientacion);
        this.padre = padre;
    }
}

// Clase para el manejo de caminos entre localizaciones
public class Caminos{
    // Cada localización se guarda como clave en un Map, su valor asociado es una lista de Localización que representa los posibles destinos
    private Map<String, List<Localizacion>> caminos = new HashMap<>();

    // Constructor, dados un archivo de localizaciones y otro de caminos, inicializa la estructura que los contiene
    public Caminos(String rutaLocs, String rutaCams, Context context){
        // Creamos un Set para guardar y hacer consultas eficientes sobre los nombres de las localizaciones
        Set<String> localizaciones = new HashSet<>();

        AssetManager assetManager = context.getAssets();

        // Abrimos el archivo de localizaciones y las vamos incluyendo en el conjunto
        try (InputStream inputStream = context.getAssets().open(rutaLocs);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            String linea;

            while ((linea = br.readLine()) != null) {
                localizaciones.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Caminos", "Error al abrir el archivo de localizaciones", e);
        }

        // Abrimos el archivo de caminos
        try (InputStream inputStream = context.getAssets().open(rutaCams);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            int coste = 0;
            String linea;
            String[] lineaDiv;
            List<Localizacion> destinos;

            // Mientras no lleguemos al final del archivo leemos una línea
            while ((linea = br.readLine()) != null) {
                // Dividimos la línea separando en cadenas por espacios (nos deben quedar 3 o 4 si hay orientacion)
                lineaDiv = linea.split(" ");

                // Si hemos obtenido tres o más elementos y los dos de los extremos se encuentran en el archivo de localizaciones
                if (lineaDiv.length >= 3 && localizaciones.contains(lineaDiv[0]) && localizaciones.contains(lineaDiv[2])) {
                    // Intentamos sacar la lista asociada a la primera localización
                    destinos = caminos.get(lineaDiv[0]);

                    // Si no existe todavía se crea la entrada en el Map
                    if (destinos == null) {
                        destinos = new ArrayList<>();
                        caminos.put(lineaDiv[0], destinos);
                    }

                    // Convertimos el segundo elemento de la línea a entero
                    try {
                        coste = Integer.parseInt(lineaDiv[1]);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        Log.e("Caminos", "No se pudo convertir el coste \"" + lineaDiv[1] + "\" a entero");
                    }

                    if(lineaDiv.length == 4){
                        // Incluimos la Localización en la lista asociada a la clave
                        destinos.add(new Localizacion(lineaDiv[2], coste, lineaDiv[3]));
                    }
                    else{
                        destinos.add(new Localizacion(lineaDiv[2], coste, null));
                    }
                } else {
                    Log.e("Caminos", "Una de las localizaciones del camino \"" + linea + "\" no se encuentra en el archivo de localizaciones");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Caminos", "Error al abrir el archivo de localizaciones", e);
        }
    }

    // Dado un origen y un destino devuelve el camino óptimo entre ambos
    public List<String> calculaRuta(String origen, String destino){
        if(!caminos.containsKey(origen) || !caminos.containsKey(destino)) {
            System.err.println("calculaRuta: el origen o destino indicados no existen");
        }

        PriorityQueue<NodoLocalizacion> abiertos = new PriorityQueue<>();
        Set<String> cerrados = new HashSet<>();

        List<String> ruta = new ArrayList<>();

        boolean encontrado = false;

        // Creamos el nodo origen y lo añadimos a la cola de abiertos
        NodoLocalizacion actual = new NodoLocalizacion(origen, 0, null, null);

        abiertos.add(actual);

        // Mientras no escontremos el destino o no queden nodos en abiertos
        while(!encontrado && abiertos.size() != 0){
            // Al poder tener los nodos la misma localización con distinto padre puede pasar que una localización se cierre y queden más nodos de ella en abiertos
            do{
                actual = abiertos.poll();
            }
            while(cerrados.contains(actual.nombre));

            // Si llegamos al nodo destino cortamos el bucle
            if(actual.nombre.equals(destino)){
                encontrado = true;
            }
            else{
                // Introducimos el nombre de la localización (no el nodo en sí) en cerrados
                cerrados.add(actual.nombre);

                // Si la localización asociada al nodo tiene caminos los introducimos en abiertos comprobando que no están en cerrados
                if(caminos.get(actual.nombre) != null) {
                    for(Localizacion l : caminos.get(actual.nombre)){
                        if(!cerrados.contains(l.nombre)){
                            abiertos.add(new NodoLocalizacion(l.nombre, actual.coste + l.coste + 1, null, actual));
                        }
                    }
                }
            }
        }

        // Si se ha encontrado el destino se recorren los nodos hacia atrás para formar la lista, si no se ha encontrado devuelve una lista vacía
        if(encontrado){
            ruta.add(actual.nombre);

            while(actual.padre != null){
                actual = actual.padre;

                ruta.add(0, actual.nombre);
            }
        }

        return ruta;
    }

    // Devuelve una lista de listas con el nombre de los archivos de imagen (índice 0) y decripción (índice 1) para el camino óptimo entre origen y destino
    public List<List<String>> calculaRutaArch(String origen, String destino){
        List<List<String>> archivos = new ArrayList<>();
        List<String> ruta = new ArrayList<>();
        List<String> par;

        // Calcula la ruta óptima entre las dos localizaciones
        ruta = calculaRuta(origen, destino);

        // Junta el nombre de cada localización con la siguiente separados por - y añade las extensiones
        for(int i=0; i<(ruta.size()-1); ++i){
            par = new ArrayList<>();

            par.add("imagenes/" + ruta.get(i) + "-" + ruta.get(i+1) + ".jpg");
            par.add("descripciones/" + ruta.get(i) + "-" + ruta.get(i+1) + ".txt");

            boolean encontrado = false;
            List<Localizacion> destinos = caminos.get(ruta.get(i));
            for(int j = 0; j < destinos.size() && !encontrado; ++j){
                if(destinos.get(j).nombre.equals(ruta.get(i+1))){
                    par.add(destinos.get(j).orientacion);
                    encontrado = true;
                }
            }

            archivos.add(par);
        }

        // Si origen o destino es clase o despacho se quita la entrada correspondiente
        String regex = "D?-?[0-9AB]+\\.[0-9]+";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher_orig = pattern.matcher(origen);
        Matcher matcher_dest = pattern.matcher(destino);

        if(matcher_orig.matches()){
            archivos.remove(0);
        }

        if(matcher_dest.matches()){
            archivos.remove(archivos.size()-1);
        }

        return archivos;
    }
}

