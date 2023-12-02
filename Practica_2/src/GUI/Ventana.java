/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.*;

import java.util.List;

import p2_npi.Caminos;

/**
 *
 * @author Usuario
 */
public class Ventana extends javax.swing.JFrame {
    
    FondoPantalla fondo = new FondoPantalla();
    
    private Menu_Inicio menu_inicio;
    private Menu_Localizacion menu_localizacion;
    private Seleccion_Clases sel_clases;
    private Carrusel_Fotos carrusel_ruta, sel_espacios_comunes;
    private Panel_Menu panel_menu;
    private Panel_Menu_Llevar panel_menu_llevar;
    private Confirmacion_Menu_Final confirmacion_fin;
    private Confirmacion_Pago confirmacion_pago;
    private LeerCodigoQR escanerQR;
    private Menu_Comedor menu_comedor;
    private Seleccion_Tipo_Menu sel_tipo_menu;
    private Localizacion_Profesorado localizacion_profes;
    private Menu_Docencia menu_docencia;
    
    private int estado;
    
    public Ventana() {
        this.setContentPane(fondo);
        initComponents();
        //Ponemos interfaz en pantalla completa
        //this.setExtendedState(Ventana.MAXIMIZED_BOTH);
        
        ajustarIconoBoton(boton_home, "../Images/home.jpg");
        ajustarIconoBoton(boton_ayuda, "../Images/ayuda.jpg");
        
        //Inicializamos los diferentes JPanel
        menu_inicio = new Menu_Inicio(this);
        menu_localizacion = new Menu_Localizacion(this);
        sel_clases = new Seleccion_Clases(this);
        carrusel_ruta = new Carrusel_Fotos(this);
        sel_espacios_comunes = new Carrusel_Fotos(this);
        panel_menu = new Panel_Menu(this);
        confirmacion_fin = new Confirmacion_Menu_Final(this);
        menu_comedor = new Menu_Comedor(this);
        sel_tipo_menu = new Seleccion_Tipo_Menu(this);
        localizacion_profes = new Localizacion_Profesorado(this);
        confirmacion_pago = new Confirmacion_Pago(this);
        menu_docencia = new Menu_Docencia(this);

        //Activamos el cursor personalizado
        this.set_cursor_personalizado();
        
        this.setVisible(true);
        //Añadimos al contenedor principal el primer panel e inicializamos el estado
        muestraPanel(menu_inicio);
        estado = 0;
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        
        dispose();
        setUndecorated(true);
        gd.setFullScreenWindow(this);
        setVisible(true);
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
    //inicio pago con qr
    public void pagoConQR(){
        // Set the Nimbus look and feel 
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
            java.util.logging.Logger.getLogger(LeerCodigoQR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LeerCodigoQR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LeerCodigoQR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LeerCodigoQR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        escanerQR = new LeerCodigoQR(this);
        
        escanerQR.setVisible(true);
    }
    
    public void codigoLeido(String codigo){
        System.out.println(codigo);
        confirmacion_pago.setCodigo(codigo);
        
        escanerQR.closeWebcam();
        escanerQR.dispose();
        
        muestraPanel(confirmacion_pago); 
        estado = 10;
    }
    
    // Método para ajustar el icono de un botón
    private void ajustarIconoBoton(JButton boton, String rutaIcono) {
        // Obtiene el menor entre el ancho y el alto del botón para mantener la forma cuadrada
        int size = Math.min(boton.getWidth(), boton.getHeight());

        // Si el botón tiene tamaño 0, puedes asignar un tamaño predeterminado
        if (size == 0) {
            size = 100; // Tamaño cuadrado predeterminado
        }

        ImageIcon icon = new ImageIcon(getClass().getResource(rutaIcono));
        boton.setIcon(resizeIcon(icon, size, size));
    }

    // Método para redimensionar el icono
    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
    
    //Parte "Controlador" - Controla las transiciones entre paneles
    
    public void botonLocalizacionPulsado(){
        muestraPanel(menu_localizacion);
        estado = 1;
    }
    
    public void botonComedorPulsado(){
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
    
    public void botonAlmuerzoPulsado(){
        panel_menu_llevar = new Panel_Menu_Llevar(this, 0);
        muestraPanel(panel_menu_llevar);
        estado = 9;
    }
    
    public void botonCenaPulsado(){
        panel_menu_llevar = new Panel_Menu_Llevar(this, 1);
        muestraPanel(panel_menu_llevar);
        estado = 9;
    }
    
    public void botonOvolactovegPulsado(){
        panel_menu_llevar = new Panel_Menu_Llevar(this, 2);
        muestraPanel(panel_menu_llevar);
        estado = 9;
    }
    
    public void botonVeganoPulsado(){
        panel_menu_llevar = new Panel_Menu_Llevar(this, 3);
        muestraPanel(panel_menu_llevar);
        estado = 9;
    }
    
    public void botonCeliacoPulsado(){
        panel_menu_llevar = new Panel_Menu_Llevar(this, 4);
        muestraPanel(panel_menu_llevar);
        estado = 9;
    }
    
    public void botonLocalizacionClasesPulsado(){
        muestraPanel(sel_clases);
        estado = 21;
        sel_clases.cambioComboxCaja(estado);
    }
    
    public void botonLocalizacionEspaciosComunesPulsado(){
        //Por implementar - Ir a menu_seleccion_espacios_comunes (estado 5)
        muestraPanel(sel_espacios_comunes);
        estado = 3; 
    }
    
    public void botonProfesoradoPulsado(){ 
        muestraPanel(localizacion_profes);
        estado = 11;
    }
    
    public void botonDocenciaPulsado(){
        muestraPanel(menu_docencia);
        estado = 12;
    }
    
    public void gestoSwipeRight(){
        siguiente();
    }
    
    public void gestoSwipeLeft(){
        anterior();
    }
    
    public void gestoSwipeUp(){
        switch(estado){
            case 21:
                sel_clases.cambioComboxRueda(21, -1);
                break;
            case 22:
                sel_clases.cambioComboxRueda(22, -1);
                break;
            case 23:
                sel_clases.cambioComboxRueda(23, -1);
                break;
            case 41: 
            case 42:
                carrusel_ruta.mostrarImagenAnterior();
                break;
            case 7: //Panel de menus
                panel_menu.mostrarDiaAnterior();
                break;
            
            case 9: //Panel de menus para llevar
                panel_menu_llevar.mostrarDiaAnterior();
                break;
        }
    }
    
    public void gestoSwipeDown(){
        switch(estado){
            case 21:
                sel_clases.cambioComboxRueda(21, 1);
                break;
            case 22:
                sel_clases.cambioComboxRueda(22, 1);
                break;
            case 23:
                sel_clases.cambioComboxRueda(23, 1);
                break;
            case 41: 
            case 42:
                carrusel_ruta.mostrarSiguienteImagen();
                break;
            case 7: //Panel de menus
                panel_menu.mostrarDiaSiguiente();
                break;
            
            case 9: //Panel de menus para llevar
                panel_menu_llevar.mostrarDiaSiguiente();
                break;
        }
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
            case 21: //Selector clases-despachos
                muestraPanel(menu_localizacion);
                estado = 1;
                break;
            case 22:
                estado = 21;
                sel_clases.cambioComboxCaja(estado);
                break;
            case 23:
                estado = 22;
                sel_clases.cambioComboxCaja(estado);
                break;
            case 3: //Seleccion espacios comunes
                muestraPanel(menu_localizacion);
                estado = 1;
                break;
            case 41: //Carrusel ruta desde clases-despachos
                muestraPanel(sel_clases);
                estado = 23;
                sel_clases.cambioComboxCaja(estado);
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
                muestraPanel(panel_menu_llevar);
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
            case 10: //Confirmacion de pago
                break;
            case 11: // Menú Profesorado
                muestraPanel(menu_inicio);
                estado = 0;
                break;
            case 12:
                muestraPanel(menu_inicio);
                estado = 0;
                break;
        }
        System.out.println(" es " + estado +"\n");
    }
    
    //Método de transición cuando se haga swipe a la derecha
    public void siguiente(){
        System.out.println("Siguiente --> Estado " + estado );
        switch(estado){
            case 21:
                estado = 22;
                sel_clases.cambioComboxCaja(estado);
                break;
            case 22:
                estado = 23;
                sel_clases.cambioComboxCaja(estado);
                break;
            case 23: //Selector clases-despachos
            	Caminos cams = new Caminos("./recursos/locs.txt", "./recursos/cams.txt");
            	List<List<String>> rutas = cams.calculaRutaArch("0_totem", sel_clases.destino());
            
            	// No tenemos en cuenta la última imagen ni descripción indicadas
            	int tam = rutas.size() - 1;
            	
            	String[] ims = new String[tam];
            	String[] des = new String[tam];
            	
            	for (int i = 0; i < tam; i++) {
                    ims[i] = "./recursos/imagenes/" + rutas.get(i).get(0);
                    des[i] = "./recursos/descripciones/" + rutas.get(i).get(1);
                }
        
            	carrusel_ruta.cargaImagenes(ims, des);
            	
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
                break;
            case 7: // Muestra menus para comer aqui (de varios dias)
                //Hacer panel_menu_llevar.mostrar(menú indicado)
                muestraPanel(confirmacion_fin);
                estado = 53;
                break;
            case 8: // Seleccion de menu para llevar
                break;
            case 9: // Mostrar menu para llevar + escaner qr
                break;
            case 10: // Seleccion de menu para llevar
                //Hacer panel_menu_llevar.mostrar(menú indicado)
                muestraPanel(menu_inicio);
                estado = 0;
                break;
        }
        System.out.println(" es " + estado +"\n");
    }
   
    //Método de transición al estado inicial
    public void reinicio(){
        muestraPanel(menu_inicio);
        estado = 0;           
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
        contenedor_principal = new javax.swing.JPanel();
        botones_inicio = new javax.swing.JPanel();
        boton_anterior = new javax.swing.JButton();
        boton_siguiente = new javax.swing.JButton();
        boton_home = new javax.swing.JButton();
        boton_ayuda = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 255, 255));

        jPanel1.setOpaque(false);

        contenedor_principal.setBackground(new java.awt.Color(51, 51, 255));
        contenedor_principal.setOpaque(false);
        contenedor_principal.setPreferredSize(new java.awt.Dimension(440, 755));

        javax.swing.GroupLayout contenedor_principalLayout = new javax.swing.GroupLayout(contenedor_principal);
        contenedor_principal.setLayout(contenedor_principalLayout);
        contenedor_principalLayout.setHorizontalGroup(
            contenedor_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        contenedor_principalLayout.setVerticalGroup(
            contenedor_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 650, Short.MAX_VALUE)
        );

        botones_inicio.setBackground(new java.awt.Color(255, 51, 51));
        botones_inicio.setOpaque(false);

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

        javax.swing.GroupLayout botones_inicioLayout = new javax.swing.GroupLayout(botones_inicio);
        botones_inicio.setLayout(botones_inicioLayout);
        botones_inicioLayout.setHorizontalGroup(
            botones_inicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, botones_inicioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(boton_anterior, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 259, Short.MAX_VALUE)
                .addComponent(boton_siguiente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        botones_inicioLayout.setVerticalGroup(
            botones_inicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(botones_inicioLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(botones_inicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boton_anterior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(boton_siguiente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        boton_home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/home.jpg"))); // NOI18N
        boton_home.setAlignmentY(0.9F);
        boton_home.setBorderPainted(false);
        boton_home.setContentAreaFilled(false);
        boton_home.setDefaultCapable(false);
        boton_home.setMargin(new java.awt.Insets(0, 0, 0, 0));
        boton_home.setPreferredSize(new java.awt.Dimension(123, 111));
        boton_home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_homeActionPerformed(evt);
            }
        });

        boton_ayuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/ayuda.jpg"))); // NOI18N
        boton_ayuda.setBorderPainted(false);
        boton_ayuda.setContentAreaFilled(false);
        boton_ayuda.setMargin(new java.awt.Insets(0, 0, 0, 0));
        boton_ayuda.setOpaque(true);
        boton_ayuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_ayudaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(botones_inicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(boton_home, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(boton_ayuda, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(contenedor_principal, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botones_inicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contenedor_principal, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boton_home, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(boton_ayuda, javax.swing.GroupLayout.PREFERRED_SIZE, 55, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void boton_siguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_siguienteActionPerformed
        siguiente();
    }//GEN-LAST:event_boton_siguienteActionPerformed

    private void boton_anteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_anteriorActionPerformed
        anterior();
    }//GEN-LAST:event_boton_anteriorActionPerformed

    private void boton_homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_homeActionPerformed
        reinicio();
    }//GEN-LAST:event_boton_homeActionPerformed

    private void boton_ayudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_ayudaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boton_ayudaActionPerformed

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
    private javax.swing.JButton boton_ayuda;
    private javax.swing.JButton boton_home;
    private javax.swing.JButton boton_siguiente;
    private javax.swing.JPanel botones_inicio;
    private javax.swing.JPanel contenedor_principal;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
    
    class FondoPantalla extends JPanel{
        private Image imagen;
        
        @Override
        public void paint(Graphics g){
            imagen = new ImageIcon(getClass().getResource("../Images/Inicio.png")).getImage();
            
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
    }

}
