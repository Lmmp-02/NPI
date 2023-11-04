/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2_npi;

/**
 *
 * @author 34611
 */


import javax.swing.*;
import GUI.Menu_Principal;

import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;
import static com.leapmotion.leap.Gesture.Type.TYPE_CIRCLE;
import static com.leapmotion.leap.Gesture.Type.TYPE_KEY_TAP;
import static com.leapmotion.leap.Gesture.Type.TYPE_SCREEN_TAP;
import static com.leapmotion.leap.Gesture.Type.TYPE_SWIPE;

import java.io.IOException;

class SampleListener extends Listener {
    private int count;
    
    public SampleListener(){
        count = 0;
    }
    
    @Override
    public void onConnect(Controller controller) {
        System.out.println("Connected");
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
        controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
        controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
        controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
    }
    
    // Desconectamos Leap software o hardware
    public void onDisconnect(Controller controller) {
        System.out.println("Disconnected");
    }

   
    @Override
    public void onFrame(Controller controller) {
        count += 1;
        Frame frame = controller.frame();
        if(count % 60 == 0){
            int s = count / 60;
            System.out.println("Segundo "+s);
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
        for (int i = 0; i < gestures.count(); i++) {
            Gesture gesture = gestures.get(i);
            switch (gesture.type()) {
                case TYPE_CIRCLE:
                    CircleGesture circle = new CircleGesture(gesture);
                    // Calcula la dirección usando el ángulo entre la normal y el pointable
                    String clockwiseness;
                    if (circle.pointable().direction().angleTo(circle.normal()) <= Math.PI/4) {
                    clockwiseness = "clockwise"; // Si es menor que 90 grados
                    } else { clockwiseness = "counterclockwise"; }
                    // Calcula el ángulo desplazado desde el último Frame
                    double sweptAngle = 0;
                    if (circle.state() != State.STATE_START) {
                    CircleGesture previousUpdate =
                    new CircleGesture(controller.frame(1).gesture(circle.id()));
                    sweptAngle = (circle.progress() - previousUpdate.progress()) *2*Math.PI;
                    }
                    System.out.println("Circle id: " + circle.id() + ", " + circle.state()
                    + ", progress: " + circle.progress() + ", radius: " + circle.radius()
                    + ", angle: " + Math.toDegrees(sweptAngle) + ", " + clockwiseness);
                    break;
                case TYPE_SWIPE:
                    SwipeGesture swipe = new SwipeGesture(gesture);
                    System.out.println("Swipe id: " + swipe.id()
                    + ", " + swipe.state()
                    + ", position: " + swipe.position()
                    + ", direction: " + swipe.direction()
                    + ", speed: " + swipe.speed());
                    break;
                case TYPE_SCREEN_TAP:
                    ScreenTapGesture screenTap =
                    new ScreenTapGesture(gesture);
                    System.out.println("Screen Tap id: " + screenTap.id()
                    + ", " + screenTap.state()
                    + ", position: " + screenTap.position()
                    + ", direction: " + screenTap.direction());
                    break;
                case TYPE_KEY_TAP:
                    KeyTapGesture keyTap = new KeyTapGesture(gesture);
                    System.out.println("Key Tap id: " + keyTap.id()
                    + ", " + keyTap.state()
                    + ", position: " + keyTap.position()
                    + ", direction: " + keyTap.direction());
                    break;
            }
        }
    }
    
    // Se elimina el listener de un controlador
    public void onExit(Controller controller) {
        System.out.println("Exited");
    }
    
    
}

public class P2_NPI {

    public static void main(String[] args) {
        //Menu_Principal vista = new Menu_Principal();
        SampleListener listener = new SampleListener();
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