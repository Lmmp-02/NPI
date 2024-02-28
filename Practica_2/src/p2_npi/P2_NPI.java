/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2_npi;

/**
 *
 * @author 34611
 */


//import javax.swing.*;
//import GUI.Menu_Botones_Generico;
import GUI.Ventana;
import GUI.Seleccion_Clases;


import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;
import static com.leapmotion.leap.Gesture.Type.TYPE_SWIPE;
import java.awt.Robot;
import java.awt.event.InputEvent;

import java.io.IOException;



class SampleListener extends Listener {
    private int count;
    private boolean pulsando_click;
    private final int MAX_NO_MOVIMIENTO_X;
    private final int MAX_NO_MOVIMIENTO_Y;
    private final int PROF_CLICK;
    private final int MOV_ENTRE_CLICKS;
    private final int ALTURA_CERO;
    private final double SENSIBILIDAD_SWIPE;
    private final Ventana ventana;
    
    private int frames_ultimo_gesto;
    private Integer estado_salida;
    private Integer contador_salida;
    
    private int frames_gesto_salida;
    private boolean palmadaDetectada = false;
    private double distanciaPrevia = 0;
    
    private Seleccion_Clases seleccionClasesPanel;
    
    public SampleListener(Ventana v){
        this.MAX_NO_MOVIMIENTO_X = 30;
        this.MAX_NO_MOVIMIENTO_Y = 30;
        this.PROF_CLICK = -200;
        this.MOV_ENTRE_CLICKS = 50;
        this.ALTURA_CERO = 200;
        this.pulsando_click = false;
        this.count = 0;
        this.frames_ultimo_gesto = 200;
        this.frames_gesto_salida = 0;
        this.estado_salida = 0;
        this.contador_salida = 0;
        this.SENSIBILIDAD_SWIPE = 0.7;
        this.ventana = v;
    }
    
    
    @Override
    public void onConnect(Controller controller) {
        System.out.println("Connected");
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
    }
    
    // Desconectamos Leap software o hardware
    public void onDisconnect(Controller controller) {
        System.out.println("Disconnected");
    }
    
    //Gesto customizado para salirse de la app
    private void gestoSalir(Frame frame){
        contador_salida++;
        /*
        System.out.println("Estado: "+Integer.toString(estado_salida) + "\tContador salida: "
                + Integer.toString(contador_salida) + "\tFingers count: "
                + Integer.toString(frame.fingers().count())
         );*/
        if(contador_salida > 50){
            switch(estado_salida){
                case 0:
                    if(frame.fingers().count() == 1){
                        estado_salida = 1;
                        contador_salida = 0;
                    }
                    else{
                        
                        estado_salida = 0;
                        contador_salida = 0;
                    }
                    
                    break;
                case 1:
                    if(frame.fingers().count() == 2){
                        estado_salida = 2;
                        contador_salida = 0;
                    }
                    else {
                        if(frame.fingers().count() != 1){
                   
                            estado_salida = 0;
                            contador_salida = 0;
                        }
                    }
                    break;
                case 2:
                    if(frame.fingers().count() == 3){
                        estado_salida = 3;
                        contador_salida = 0;
                    }
                    else{
                        if(frame.fingers().count() != 2){
                   
                            estado_salida = 0;
                            contador_salida = 0;
                        }
                    }
                    
                    break;
                case 3:
                    if(frame.fingers().count() == 4){
                        estado_salida = 4;
                        contador_salida = 0;
                    }
                    else{
                        if(frame.fingers().count() != 3){
                   
                            estado_salida = 0;
                            contador_salida = 0;
                        }
                    }
                    
                    break;
                case 4:
                    if(frame.fingers().count() == 5){
                        estado_salida = 5;
                        contador_salida = 0;
                    }
                    else{
                        if(frame.fingers().count() != 4){
                   
                            estado_salida = 0;
                            contador_salida = 0;
                        }
                    }
                    
                    break;
                    
                case 5:
                    if(frame.fingers().count() == 0){
                        estado_salida = 6;
                        contador_salida = 0;
                    }
                    else{
                        if(frame.fingers().count() != 5){
                   
                            estado_salida = 0;
                            contador_salida = 0;
                        }
                    }
                    
                    break;
                    
                case 6:
                    System.exit(0);
            }
        }
    }

   
    @Override
    public void onFrame(Controller controller) {
        
        frames_ultimo_gesto += 1;
        System.out.println(frames_ultimo_gesto);
        Frame frame = controller.frame();
        
        //Gesto customizado para salirse de la app
        gestoSalir(frame);
        //Feedback de lo que va detectando Leap cada 60 frames
        
        if(count % 60 == 0){
            int s = count / 60;
            
            System.out.println("Muestra "+s);
            System.out.println("Frame id: " + frame.id()
                      + ", timestamp: " + frame.timestamp()
                      + ", hands: " + frame.hands().count()
                      + ", fingers: " + frame.fingers().count()
                      + ", tools: " + frame.tools().count()
                      + ", gestures " + frame.gestures().count());
            
            // Vamos a examinar la primera mano
            if (!frame.hands().isEmpty()) {
            // Obtiene el objeto mano
            Hand hand = frame.hands().get(0);
            // Comprobamos la lista de dedos
            FingerList fingers = hand.fingers();
            if (!fingers.isEmpty()) {
                // Calcula la posición media de los dedos
                Vector avgPos = Vector.zero();
                for (Finger finger : fingers) {
                    avgPos = avgPos.plus(finger.tipPosition()); }
                    avgPos = avgPos.divide(fingers.count());
                    System.out.println("Hand has " + fingers.count()
                    + " fingers, average finger tip position: " + avgPos);
                    // Obtiene datos de la mano
                    System.out.println("Hand sphere radius: " + hand.sphereRadius()
                    + " mm, palm position: " + hand.palmPosition());
                    // Obtiene la normal y la dirección
                    Vector normal = hand.palmNormal();
                    Vector direction = hand.direction();
                    // Calcula los de la mano, devuelve radianes y se pasan a grados
                    
                    System.out.println(
                        "Hand pitch: " + Math.toDegrees(direction.pitch()) + ", " // Eje X
                        + "roll: " + Math.toDegrees(normal.roll()) + ", "         // Eje Y
                        + "yaw: " + Math.toDegrees(direction.yaw()));             // Eje Z
                    
                    }
            }        
        }
        
        // La lista contiene objetos de tipo Gesture, debemos convertir
            // ese objeto a la instancia de la subclase.
            // No se puede hacer Type Casting, usamos los constructores
            // específicos, ejemplo
            //CircleGesture circle = new CircleGesture(gesture);
        
        // Recorremos la lista de gestos
        GestureList gestures = frame.gestures();
        if( frames_ultimo_gesto > 100){
            for (int i = 0; i < 1; i++) {
                Gesture gesture = gestures.get(i);
                switch (gesture.type()) {
                  
                    case TYPE_SWIPE -> {

                        SwipeGesture swipe = new SwipeGesture(gesture);

                        System.out.println("Swipe id: " + swipe.id()
                            + ", " + swipe.state()
                            + ", position: " + swipe.position()
                            + ", direction: " + swipe.direction()
                            + ", speed: " + swipe.speed());

                        if(swipe.state() == Gesture.State.STATE_STOP){
                            handleSwipeGesture(swipe);
                        }
                        break;
                    }

                }
            }
        }
        //Movimiento del cursor con la palma de la mano
        if (!frame.hands().isEmpty() && frames_ultimo_gesto > 100) {
            Hand hand = frame.hands().get(0);
            Vector position = hand.palmPosition();
            int mov_x = (int) position.getX();
            int mov_y = (int) position.getY() - ALTURA_CERO; 
            int pos_z = (int) position.getZ(); 
        
            FingerList fingers = hand.fingers();

        // Calcular la distancia media de las puntas de los dedos a la palma
        double totalDistance = 0;
        for (Finger finger : fingers) {
            Vector fingerTip = finger.tipPosition();
            double distance = fingerTip.distanceTo(position);
            totalDistance += distance;
        }
        double averageDistance = totalDistance / fingers.count();

        // Establecer un umbral para la distancia
        double closedFistThreshold = 80; 

        // Verificar si la mano está en posición de puño cerrado
        if (averageDistance < closedFistThreshold && !pulsando_click) {
            pulsando_click = true;
            System.out.println("Clic con el puño cerrado");
            try {
                Robot robot = new Robot();
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (averageDistance >= closedFistThreshold && pulsando_click) {
            pulsando_click = false;
        }
            
            //Si alguna de las componentes es baja, supondremos que no queremos
            //que el cursor se mueva en dicha direccion
            if(Math.abs(mov_x) < MAX_NO_MOVIMIENTO_X){
                mov_x = 0;
            }
            else{
                if(mov_x > 0){
                    mov_x -= MAX_NO_MOVIMIENTO_X;
                }
                else{
                    mov_x += MAX_NO_MOVIMIENTO_X;
                }
            }
            if(Math.abs(mov_y) < MAX_NO_MOVIMIENTO_Y){
                mov_y = 0;
            }
            else{
                if(mov_y > 0){
                    mov_y -= MAX_NO_MOVIMIENTO_Y;
                }
                else{
                    mov_y += MAX_NO_MOVIMIENTO_Y;
                }
            }
            

            // Utiliza las coordenadas (x, y) para mover el cursor
            java.awt.Point point = java.awt.MouseInfo.getPointerInfo().getLocation();
            int x_raton = (int) point.getX();
            int y_raton = (int) point.getY();

            // Ajusta la velocidad de movimiento 
            double speed = 0.1;

            java.awt.Robot robot;
            try {
                robot = new java.awt.Robot();
                robot.mouseMove(x_raton + (int)(mov_x * speed), (int) (y_raton - (mov_y * speed)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        
        }
        
        // Gesto de salida: palmada
        if (!frame.hands().isEmpty() && frame.hands().count() == 2) {
            // Obtiene ambas manos
            Hand manoIzquierda = frame.hands().leftmost();
            Hand manoDerecha = frame.hands().rightmost();

            // Calcula la distancia entre las palmas de las manos
            double distanciaActual = manoIzquierda.palmPosition().distanceTo(manoDerecha.palmPosition());

            // Detecta una palmada (acercamiento y luego alejamiento rápido)
            if (!palmadaDetectada && distanciaActual < 50 && distanciaPrevia - distanciaActual > 30) {
                palmadaDetectada = true;
            } else if (palmadaDetectada && distanciaActual > 100) {
                System.exit(0); // Cierra la aplicación
            }

            distanciaPrevia = distanciaActual;
        }
    }
    
    private void handleSwipeGesture(SwipeGesture swipe) {
        Vector direction = swipe.direction();
        System.out.println("DireccionX"+direction.getX());
        System.out.println("DireccionY"+direction.getY());
        System.out.println("DireccionZ"+direction.getZ());
        // Swipe hacia arriba
        if (direction.getY() > SENSIBILIDAD_SWIPE && swipe.state() == State.STATE_STOP) {
            ventana.gestoSwipeUp();
            System.out.println("Swipe hacia arriba :)");
        }
        // Swipe hacia abajo
        else if (direction.getY() < -SENSIBILIDAD_SWIPE && swipe.state() == State.STATE_STOP) {
            ventana.gestoSwipeDown();
            System.out.println("Swipe hacia abajo :)");
        }
        // Swipe hacia izquierda
        else if (direction.getX() < -SENSIBILIDAD_SWIPE && swipe.state() == State.STATE_STOP) {
            ventana.gestoSwipeLeft();
            System.out.println("Swipe hacia izquierda :)");
        }
        // Swipe hacia izquierda
        else if (direction.getX() > SENSIBILIDAD_SWIPE && swipe.state() == State.STATE_STOP) {
            ventana.gestoSwipeRight();
            System.out.println("Swipe hacia derecha :)");
        }
        
        frames_ultimo_gesto=0;
    }
        
    // Se elimina el listener de un controlador
    public void onExit(Controller controller) {
        System.out.println("Exited");
    }
    
       
    
}


public class P2_NPI {

    public static void main(String[] args) {
        // LLamada a Menu_Principal y que muestre el panel
        
        Ventana ventana = new Ventana();
        ventana.setVisible(true);

        SampleListener listener = new SampleListener(ventana);
        Controller controller = new Controller();
        
        // Have the sample listener receive events from the controller
        controller.addListener(listener);
        
        // Keep this process running until Enter is pressed
        System.out.println("Press Enter to quit...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Elimina el listener al final
        controller.removeListener(listener);
    }
}