/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import java.awt.Image;

public class Carrusel extends javax.swing.JPanel {

    private JLabel imagenLabel;
    private int tam_imagen_x, tam_imagen_y;
    private int indiceImagenActual = 0;
    private BufferedImage[] imagenes;
    private Carrusel_Fotos padre;
    
    public Carrusel(Carrusel_Fotos padre, int tam_x, int tam_y) {
        super(new BorderLayout(10,10));
        //this.setPreferredSize(new Dimension(tam_x, tam_y));
       
        tam_imagen_x = tam_x;
        tam_imagen_y = tam_y-50;
        imagenLabel = new JLabel();
        //imagenLabel.setPreferredSize(new Dimension(tam_imagen_x,tam_imagen_y));
        
        ImageIcon flecha_arriba=new ImageIcon("./recursos/iconos/flecha_arriba.png");
        ImageIcon flecha_abajo=new ImageIcon("./recursos/iconos/flecha_abajo.png");

        // Crear botones de navegación
        JButton anteriorButton = new JButton(flecha_arriba);
        anteriorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarImagenAnterior();
            }
        });

        JButton siguienteButton = new JButton(flecha_abajo);
        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarSiguienteImagen();
            }
        });

        // Crear un panel para los botones
        JPanel panelBotonesIZQ = new JPanel();
        panelBotonesIZQ.add(anteriorButton);
        
        JPanel panelBotonesDER = new JPanel();
        panelBotonesDER.add(siguienteButton);
        
        
        add(imagenLabel, BorderLayout.CENTER);
        add(panelBotonesIZQ, BorderLayout.NORTH);
        add(panelBotonesDER, BorderLayout.SOUTH);
    }
    
    private void mostrarImagenAnterior() {
        indiceImagenActual = (indiceImagenActual - 1 + imagenes.length) % imagenes.length;
        actualizarImagen();
    }

    private void mostrarSiguienteImagen() {
        indiceImagenActual = (indiceImagenActual + 1) % imagenes.length;
        actualizarImagen();
    }
    
    public void cargaImagenes(String[] rutasImagenes) {
        BufferedImage[] images = new BufferedImage[rutasImagenes.length];

        for (int i = 0; i < rutasImagenes.length; ++i) {
            try {
                File file = new File(rutasImagenes[i]);
                images[i] = ImageIO.read(file);
            } catch (IOException e) {
                System.err.println("Error al cargar la imagen: " + rutasImagenes[i]);
                e.printStackTrace();
            }
        }

        this.imagenes = images;
        
        actualizarImagen();
    }

    private void actualizarImagen() {
        BufferedImage imagenIcon = imagenes[indiceImagenActual];
        BufferedImage imagenEscalada = new BufferedImage(tam_imagen_x, tam_imagen_y, BufferedImage.TYPE_INT_ARGB);
        imagenEscalada.createGraphics().drawImage(imagenIcon, 0, 0, tam_imagen_x, tam_imagen_y, null);

        // Crear un nuevo ImageIcon con la imagen escalada
        ImageIcon ImagenIcon = new ImageIcon(imagenEscalada);
        
        imagenLabel.setIcon(ImagenIcon);
    }
    
    
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
                
            }
        });
    }
}