/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author Usuario
 */
public class Carrusel_Fotos extends javax.swing.JPanel {

    private Ventana padre;
    private int indiceImagenActual;
    private BufferedImage[] imagenes;
    private String[] textos;
    private int tam_imagen_x, tam_imagen_y;
    
    public Carrusel_Fotos(Ventana p) {
        initComponents();
        indiceImagenActual=0;
        tam_imagen_x = 675;
        tam_imagen_y = 780;  
    }
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        boton_anterior = new javax.swing.JButton();
        boton_siguiente = new javax.swing.JButton();
        imagenLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 255));
        setOpaque(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Castellar", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 153, 51));
        jLabel1.setText("Carrusel Imágenes");

        jTextField1.setEditable(false);
        jTextField1.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        boton_anterior.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0), 4));
        boton_anterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_anteriorActionPerformed(evt);
            }
        });

        boton_siguiente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0), 4));
        boton_siguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_siguienteActionPerformed(evt);
            }
        });

        imagenLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imagenLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addGap(17, 17, 17))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(416, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(boton_siguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(445, 445, 445))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(boton_anterior, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(428, 428, 428))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(351, 351, 351))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(boton_anterior, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(imagenLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 1104, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(boton_siguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void boton_siguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_siguienteActionPerformed
        mostrarSiguienteImagen();
    }//GEN-LAST:event_boton_siguienteActionPerformed

    private void boton_anteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_anteriorActionPerformed
        mostrarImagenAnterior();
    }//GEN-LAST:event_boton_anteriorActionPerformed


    public void mostrarImagenAnterior() {
        indiceImagenActual = (indiceImagenActual - 1 + imagenes.length) % imagenes.length;
        actualizarImagen();
    }

    public void mostrarSiguienteImagen() {
        indiceImagenActual = (indiceImagenActual + 1) % imagenes.length;
        actualizarImagen();
    }
    
    public void cargaImagenes(String[] rutasImagenes, String[] rutasDesc) {
    	indiceImagenActual=0;
    	
        if(rutasImagenes.length != rutasDesc.length) {
        	System.out.print("cargaImagenes: el tamaño de los vectores no coincide");
        }
    	
    	this.imagenes = new BufferedImage[rutasImagenes.length];
    	this.textos = new String[rutasDesc.length];

        for (int i = 0; i < rutasImagenes.length; ++i) {
            try {
                File file = new File(rutasImagenes[i]);
                imagenes[i] = ImageIO.read(file);
            } catch (IOException e) {
                System.err.println("Error al cargar la imagen : " + rutasImagenes[i]);
                e.printStackTrace();
            }
            
            try {
                textos[i] = Files.readString(Paths.get(rutasDesc[i]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
  
        actualizarImagen();
    }

    private void actualizarImagen() {
        BufferedImage imagenIcon = imagenes[indiceImagenActual];
        
        BufferedImage imagenEscalada = new BufferedImage(tam_imagen_x, tam_imagen_y, TYPE_INT_ARGB);
        imagenEscalada.createGraphics().drawImage(imagenIcon, 0, 0, tam_imagen_x, tam_imagen_y, null);

        // Crear un nuevo ImageIcon con la imagen escalada
        ImageIcon ImagenIcon = new ImageIcon(imagenEscalada);
        
        imagenLabel.setIcon(ImagenIcon);
        
        jTextField1.setText(textos[indiceImagenActual]);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton_anterior;
    private javax.swing.JButton boton_siguiente;
    private javax.swing.JLabel imagenLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
