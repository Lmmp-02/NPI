/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.*;

/**
 *
 * @author Usuario
 */
public class Ventana extends javax.swing.JFrame {
    
    private Menu_Inicio menu_inicio;
    private Menu_Localizacion menu_localizacion;
    private Seleccion_Clases sel_clases;
    private Carrusel_Fotos carrusel;
    private Confirmacion_Menu_Final confirmacion_fin;
    private int estado;
    
    // Comedor
    private Menu_Comedor menu_comedor;
    private Seleccion_Tipo_Menu tipo_menu;
    
    public Ventana() {
        initComponents();
        //Ponemos interfaz en pantalla completa
        //this.setExtendedState(Ventana.MAXIMIZED_BOTH);
        
        //Inicializamos los diferentes JPanel
        menu_inicio = new Menu_Inicio(this);
        menu_localizacion = new Menu_Localizacion(this);
        sel_clases = new Seleccion_Clases(this);
        carrusel = new Carrusel_Fotos(this);
        confirmacion_fin = new Confirmacion_Menu_Final(this);
        menu_comedor = new Menu_Comedor(this);
        tipo_menu = new Seleccion_Tipo_Menu(this);
        
        //Activamos el cursor personalizado
        this.set_cursor_personalizado();
        
        this.setVisible(true);
        //Añadimos al contenedor principal el primer panel e inicializamos el estado
        muestraPanel(menu_inicio);
        estado = 0;
    }
    
    //Método para mostrar cierto panel en el contenedor principal
    //Borra el panel anterior automaticamente
    private void muestraPanel(JPanel p){
        p.setSize(contenedor_principal.getSize());
        p.setLocation(0,0);
        contenedor_principal.removeAll();
        contenedor_principal.add(p);
        contenedor_principal.revalidate();
        contenedor_principal.repaint();
    }
    
    
    //Parte "Controlador" - Controla las transiciones entre paneles
    
    public void botonLocalizacionPulsado(){
        muestraPanel(menu_localizacion);
        estado = 1;
    }
    
    public void botonComedorPulsado(){
        //Por implementar - Ir a menu_comedor
        muestraPanel(menu_comedor);
        estado = 6;
    }
    
    /*public void botonMenuAydanamarPulsado(){
        muestraPanel(carrusel_comedor);
        estado = 7;
    }*/ // Implementar cuando esté el carrusel de fotos
    
    public void botonMenuParaLlevarPulsado(){
        muestraPanel(tipo_menu);
        estado = 8;
    }
    
    public void botonLocalizacionClasesPulsado(){
        muestraPanel(sel_clases);
        estado = 2;
    }
    
    public void botonLocalizacionEspaciosComunesPulsado(){
        //Por implementar - Ir a menu_seleccion_espacios_comunes (estado 5)
        muestraPanel(carrusel);
        estado = 3; 
    }
    
    
    //Método de transición cuando se haga swipe a la izquierda
    public void anterior(){
        System.out.println("Anterior --> Estado " + estado );
        switch(estado){
            case 1: //Menu localizacion
                muestraPanel(menu_inicio);
                estado = 0;
                break;
            case 2: //Selector clases
                muestraPanel(menu_localizacion);
                estado = 1;
                break;
            case 3: //Carrusel 
                muestraPanel(sel_clases);
                estado = 2;
                break;
            case 4: //Confirmacion final
                muestraPanel(carrusel);
                estado = 3;
                break;
            case 6: // Menú Comedor
                muestraPanel(menu_inicio); //Cambiarlo pero no sé cuál es su función
                estado = 6;
                break;
            case 8:
                muestraPanel(menu_comedor);
                estado = 6;
                break;
        }
        System.out.println(" es " + estado +"\n");
    }
    
    //Método de transición cuando se haga swipe a la derecha
    public void siguiente(){
        System.out.println("Siguiente --> Estado " + estado );
        switch(estado){
            case 2:
                muestraPanel(carrusel);
                estado = 3;
                break;
            case 3:
                muestraPanel(confirmacion_fin);
                estado = 4;
                break;
            case 4:
                muestraPanel(menu_inicio);
                estado = 0;
                break;
            case 6:
                muestraPanel(menu_inicio);
                estado = 0;
                break;
            case 8:
                muestraPanel(menu_comedor);
                estado = 6;
                break;
        }
        System.out.println(" es " + estado +"\n");
    }
   
    
    
    
    //Métodos para poner cursor personalizado
    private void set_cursor_personalizado(){
        // Cargar una imagen para el nuevo icono del cursor
        BufferedImage cursorImage = loadImage("./recursos/cursor_custom.png");
        if (cursorImage != null) {
            Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "CustomCursor");
            // Establece el nuevo cursor en el JFrame
            this.setCursor(customCursor);
        }
    }
    
    private static BufferedImage loadImage(String filePath) {
        try {
            return ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contenedor_principal = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 255, 255));

        contenedor_principal.setPreferredSize(new java.awt.Dimension(450, 755));

        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Background.png"))); // NOI18N
        jButton1.setText("jButton1");
        jButton1.setEnabled(false);
        jButton1.setPreferredSize(new java.awt.Dimension(450, 755));

        javax.swing.GroupLayout contenedor_principalLayout = new javax.swing.GroupLayout(contenedor_principal);
        contenedor_principal.setLayout(contenedor_principalLayout);
        contenedor_principalLayout.setHorizontalGroup(
            contenedor_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedor_principalLayout.createSequentialGroup()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        contenedor_principalLayout.setVerticalGroup(
            contenedor_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedor_principalLayout.createSequentialGroup()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(contenedor_principal, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contenedor_principal;
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables
}
