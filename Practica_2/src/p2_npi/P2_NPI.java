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
    
    public SampleListener(){
        this.MAX_NO_MOVIMIENTO_X = 30;
        this.MAX_NO_MOVIMIENTO_Y = 30;
        this.PROF_CLICK = -200;
        this.MOV_ENTRE_CLICKS = 50;
        this.ALTURA_CERO = 400;
        this.pulsando_click = false;
        this.count = 0;
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
        for (int i = 0; i < gestures.count(); i++) {
            Gesture gesture = gestures.get(i);
            switch (gesture.type()) {
                case TYPE_CIRCLE -> {
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
                }
                case TYPE_SWIPE -> {
                    SwipeGesture swipe = new SwipeGesture(gesture);
                    System.out.println("Swipe id: " + swipe.id()
                            + ", " + swipe.state()
                            + ", position: " + swipe.position()
                            + ", direction: " + swipe.direction()
                            + ", speed: " + swipe.speed());
                    
                }
                
                case TYPE_SCREEN_TAP -> {
                    ScreenTapGesture screenTap =
                            new ScreenTapGesture(gesture);
                    System.out.println("Screen Tap id: " + screenTap.id()
                            + ", " + screenTap.state()
                            + ", position: " + screenTap.position()
                            + ", direction: " + screenTap.direction());
                }
                
                case TYPE_KEY_TAP -> {
                    KeyTapGesture keyTap = new KeyTapGesture(gesture);
                    System.out.println("Key Tap id: " + keyTap.id()
                            + ", " + keyTap.state()
                            + ", position: " + keyTap.position()
                            + ", direction: " + keyTap.direction());
                }
            }
        }
        
        //Movimiento del cursor con la palma de la mano
        if (!frame.hands().isEmpty()) {
            Hand hand = frame.hands().get(0);
            Vector position = hand.palmPosition();
            int mov_x = (int) position.getX();
            int mov_y = (int) position.getY() - ALTURA_CERO; 
            int pos_z = (int) position.getZ(); 
            
            
            //Vemos si estamos haciendo un click
            if(pos_z < PROF_CLICK && !pulsando_click){
                pulsando_click = true;
                System.out.println("Click ini");
                // Simula un clic izquierdo del ratón
                try {
                    Robot robot = new Robot();
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //Vemos si estamos dejando de hacer click
            else{
                if(pos_z > PROF_CLICK + MOV_ENTRE_CLICKS && pulsando_click){
                    System.out.println("Click fin");
                    pulsando_click = false;
                }
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
    }
    
        
    // Se elimina el listener de un controlador
    public void onExit(Controller controller) {
        System.out.println("Exited");
    }
    
       
    
}

public class P2_NPI {

    public static void main(String[] args) {
        Menu_Principal vista = new Menu_Principal();
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