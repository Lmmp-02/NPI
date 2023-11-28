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
    private Carrusel_Fotos carrusel_ruta, sel_espacios_comunes;
    private Panel_Menu panel_menu, panel_menu_llevar;
    private Confirmacion_Menu_Final confirmacion_fin;
    private int estado;
    private Seleccion_Clases pago_comida;
    
    // Comedor
    private Menu_Comedor menu_comedor;
    private Seleccion_Tipo_Menu sel_tipo_menu;
    
    public Ventana() {
        initComponents();
        //Ponemos interfaz en pantalla completa
        //this.setExtendedState(Ventana.MAXIMIZED_BOTH);
        
        //Inicializamos los diferentes JPanel
        menu_inicio = new Menu_Inicio(this);
        menu_localizacion = new Menu_Localizacion(this);
        sel_clases = new Seleccion_Clases(this);
        carrusel_ruta = new Carrusel_Fotos(this);
        sel_espacios_comunes = new Carrusel_Fotos(this);
        panel_menu = new Panel_Menu(this);
        panel_menu_llevar = new Panel_Menu(this);
        confirmacion_fin = new Confirmacion_Menu_Final(this);
        menu_comedor = new Menu_Comedor(this);
        sel_tipo_menu = new Seleccion_Tipo_Menu(this);
        pago_comida = new Seleccion_Clases(this);
        
        
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
    
    public void botonMenuAydanamarPulsado(){
        muestraPanel(panel_menu);
        estado = 7;
    } 
    
    public void botonMenuParaLlevarPulsado(){
        muestraPanel(sel_tipo_menu);
        estado = 8;
    }
    
    public void botonLocalizacionClasesPulsado(){
        muestraPanel(sel_clases);
        estado = 2;
    }
    
    public void botonLocalizacionEspaciosComunesPulsado(){
        //Por implementar - Ir a menu_seleccion_espacios_comunes (estado 5)
        muestraPanel(sel_espacios_comunes);
        estado = 3; 
    }
    
    
    //Método de transición cuando se haga swipe a la izquierda
    public void anterior(){
        System.out.println("Anterior --> Estado " + estado );
        switch(estado){
            case 0: //Menu inicio
                break;
            case 1: //Menu localizacion
                muestraPanel(menu_inicio);
                estado = 0;
                break;
            case 2: //Selector clases-despachos
                muestraPanel(menu_localizacion);
                estado = 1;
                break;
            case 3: //Seleccion espacios comunes
                muestraPanel(menu_localizacion);
                estado = 1;
                break;
            case 41: //Carrusel ruta desde clases-despachos
                muestraPanel(sel_clases);
                estado = 2;
                break;
            case 42: //Carrusel ruta desde espacios comunes
                muestraPanel(sel_espacios_comunes);
                estado = 3;
                break;
            case 51: //Confirmacion fin desde clases-despachos
                muestraPanel(carrusel_ruta);
                estado = 41;
                break;
            case 52: //Confirmacion fin desde espacios comunes
                muestraPanel(carrusel_ruta);
                estado = 42;
                break;
            case 53: //Confirmacion fin desde menu comedor 
                muestraPanel(panel_menu);
                estado = 7;
                break;
            case 54: //Confirmacion fin desde pedido para llevar
                muestraPanel(pago_comida);
                estado = 9;
                break;
            case 6: //Menú Comedor
                muestraPanel(menu_inicio); //Cambiarlo pero no sé cuál es su función
                estado = 0;
                break;
            case 7: //Panel menu diario 
                muestraPanel(menu_comedor);
                estado = 6;
                break;
            case 8: //Seleccion menu para llevar
                muestraPanel(menu_comedor);
                estado = 6;
                break;
            case 9: //Muestra menu para llevar
                muestraPanel(sel_tipo_menu);
                estado = 8;
                break;
            case 10: //Pago con QR
                muestraPanel(sel_tipo_menu);
                estado = 8;
                break;
        }
        System.out.println(" es " + estado +"\n");
    }
    
    //Método de transición cuando se haga swipe a la derecha
    public void siguiente(){
        System.out.println("Siguiente --> Estado " + estado );
        switch(estado){
            case 2: //Selector clases-despachos
                muestraPanel(carrusel_ruta);
                estado = 41;
                break;
            case 3: //Seleccion espacios comunes
                muestraPanel(carrusel_ruta);
                estado = 42;
                break;
            case 41: //Carrusel ruta desde clases-despachos
                muestraPanel(confirmacion_fin);
                estado = 51;
                break;
            case 42:  //Carrusel ruta desde espacios comunes
                muestraPanel(confirmacion_fin);
                estado = 52;
                break;
            case 51: //Confirmacion fin desde clases-despachos
                muestraPanel(menu_inicio);
                estado = 0;
                break;
            case 52: //Confirmacion fin desde espacios comunes
                muestraPanel(menu_inicio);
                estado = 0;
                break;
            case 53: //Confirmacion fin desde menu comedor 
                muestraPanel(menu_inicio);
                estado = 0;
                break;
            case 54: //Confirmacion fin desde pedido para llevar
                muestraPanel(menu_inicio);
                estado = 0;
                break;
            case 6: // Menú Comedor
                muestraPanel(menu_inicio);
                estado = 0;
                break;
            case 7: // Muestra menus para comer aqui (de varios dias)
                //Hacer panel_menu_llevar.mostrar(menú indicado)
                muestraPanel(confirmacion_fin);
                estado = 53;
                break;
            case 8: // Seleccion de menu para llevar
                //Hacer panel_menu_llevar.mostrar(menú indicado)
                muestraPanel(panel_menu);
                estado = 9;
                break;
            case 9: // Seleccion de menu para llevar
                //Hacer panel_menu_llevar.mostrar(menú indicado)
                muestraPanel(pago_comida);
                estado = 10;
                break;
            case 10: // Seleccion de menu para llevar
                //Hacer panel_menu_llevar.mostrar(menú indicado)
                muestraPanel(confirmacion_fin);
                estado = 54;
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

        jPanel1 = new javax.swing.JPanel();
        botones_fin = new javax.swing.JPanel();
        boton_anterior = new javax.swing.JButton();
        boton_siguiente = new javax.swing.JButton();
        contenedor_principal = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 255, 255));

        botones_fin.setBackground(new java.awt.Color(255, 51, 51));

        boton_anterior.setText("Atrás");
        boton_anterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_anteriorActionPerformed(evt);
            }
        });

        boton_siguiente.setText("Siguiente");
        boton_siguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_siguienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout botones_finLayout = new javax.swing.GroupLayout(botones_fin);
        botones_fin.setLayout(botones_finLayout);
        botones_finLayout.setHorizontalGroup(
            botones_finLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, botones_finLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(boton_anterior)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 905, Short.MAX_VALUE)
                .addComponent(boton_siguiente)
                .addContainerGap())
        );
        botones_finLayout.setVerticalGroup(
            botones_finLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(botones_finLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(botones_finLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boton_anterior)
                    .addComponent(boton_siguiente))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        contenedor_principal.setBackground(new java.awt.Color(51, 51, 255));
        contenedor_principal.setPreferredSize(new java.awt.Dimension(440, 755));

        javax.swing.GroupLayout contenedor_principalLayout = new javax.swing.GroupLayout(contenedor_principal);
        contenedor_principal.setLayout(contenedor_principalLayout);
        contenedor_principalLayout.setHorizontalGroup(
            contenedor_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        contenedor_principalLayout.setVerticalGroup(
            contenedor_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1867, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(contenedor_principal, javax.swing.GroupLayout.DEFAULT_SIZE, 1068, Short.MAX_VALUE)
                    .addComponent(botones_fin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botones_fin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contenedor_principal, javax.swing.GroupLayout.DEFAULT_SIZE, 1867, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void boton_anteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_anteriorActionPerformed
        anterior();
    }//GEN-LAST:event_boton_anteriorActionPerformed

    private void boton_siguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_siguienteActionPerformed
        siguiente();
    }//GEN-LAST:event_boton_siguienteActionPerformed

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
    private javax.swing.JButton boton_anterior;
    private javax.swing.JButton boton_siguiente;
    private javax.swing.JPanel botones_fin;
    private javax.swing.JPanel contenedor_principal;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
