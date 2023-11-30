/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import java.awt.Font;

/**
 *
 * @author Usuario
 */
public class Menu_Inicio extends javax.swing.JPanel {

    private Ventana padre;
    public Menu_Inicio(Ventana padre) {
        initComponents();
        this.padre = padre;
        
        // Ajustamos el texto al tamaño de ventana
        int ancho_menu = padre.getWidth();
        System.out.println(ancho_menu);
        Font fuente_botones = boton_comedor.getFont().deriveFont((float) ancho_menu/15);
        Font fuente_titulo = jLabel1.getFont().deriveFont((float) ancho_menu/13);
        boton_comedor.setFont(fuente_botones);
        boton_docencia.setFont(fuente_botones);
        boton_localizacion.setFont(fuente_botones);
        boton_profesorado.setFont(fuente_botones);
        boton_tramites.setFont(fuente_botones);
        jLabel1.setFont(fuente_titulo);
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        boton_docencia = new javax.swing.JButton();
        boton_profesorado = new javax.swing.JButton();
        boton_comedor = new javax.swing.JButton();
        boton_tramites = new javax.swing.JButton();
        boton_localizacion = new javax.swing.JButton();

        setOpaque(false);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Castellar", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 153, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Menú de Inicio");

        boton_docencia.setBackground(new java.awt.Color(255, 255, 255));
        boton_docencia.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        boton_docencia.setText("Docencia");
        boton_docencia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 51), 4));
        boton_docencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_docenciaActionPerformed(evt);
            }
        });

        boton_profesorado.setBackground(new java.awt.Color(255, 255, 255));
        boton_profesorado.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        boton_profesorado.setText("Profesorado");
        boton_profesorado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 51), 4));
        boton_profesorado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_profesoradoActionPerformed(evt);
            }
        });

        boton_comedor.setBackground(new java.awt.Color(255, 255, 255));
        boton_comedor.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        boton_comedor.setText("Comedor");
        boton_comedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 51), 4));
        boton_comedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_comedorActionPerformed(evt);
            }
        });

        boton_tramites.setBackground(new java.awt.Color(255, 255, 255));
        boton_tramites.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        boton_tramites.setText("Trámites");
        boton_tramites.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 51), 4));
        boton_tramites.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_tramitesActionPerformed(evt);
            }
        });

        boton_localizacion.setBackground(new java.awt.Color(255, 255, 255));
        boton_localizacion.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        boton_localizacion.setText("Localización");
        boton_localizacion.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 153, 51), 4, true));
        boton_localizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_localizacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(boton_docencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(boton_profesorado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(boton_tramites, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                    .addComponent(boton_comedor, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                    .addComponent(boton_localizacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                .addComponent(boton_docencia, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(boton_profesorado, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(boton_tramites, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(boton_comedor, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(boton_localizacion, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void boton_docenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_docenciaActionPerformed
        padre.botonDocenciaPulsado();
    }//GEN-LAST:event_boton_docenciaActionPerformed

    private void boton_profesoradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_profesoradoActionPerformed
        padre.botonProfesoradoPulsado();
    }//GEN-LAST:event_boton_profesoradoActionPerformed

    private void boton_comedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_comedorActionPerformed
        padre.botonComedorPulsado();
    }//GEN-LAST:event_boton_comedorActionPerformed

    private void boton_tramitesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_tramitesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boton_tramitesActionPerformed

    private void boton_localizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_localizacionActionPerformed
        padre.botonLocalizacionPulsado();
    }//GEN-LAST:event_boton_localizacionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton_comedor;
    private javax.swing.JButton boton_docencia;
    private javax.swing.JButton boton_localizacion;
    private javax.swing.JButton boton_profesorado;
    private javax.swing.JButton boton_tramites;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
