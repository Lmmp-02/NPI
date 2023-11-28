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
public class Menu_Localizacion extends javax.swing.JPanel {

    private Ventana padre;
    
    public Menu_Localizacion(Ventana p) {
        initComponents();
        padre = p;
        
        // Ajustamos el texto al tamaño de ventana
        int ancho_menu = padre.getWidth();
        System.out.println(ancho_menu);
        Font fuente_botones = boton_clases_despachos.getFont().deriveFont((float) ancho_menu/15);
        Font fuente_titulo = jLabel1.getFont().deriveFont((float) ancho_menu/13);
        boton_clases_despachos.setFont(fuente_botones);
        boton_espacios_comunes.setFont(fuente_botones);
        boton_servicios_externos.setFont(fuente_botones);
        jLabel1.setFont(fuente_titulo);
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
        boton_servicios_externos = new javax.swing.JButton();
        boton_clases_despachos = new javax.swing.JButton();
        boton_espacios_comunes = new javax.swing.JButton();

        setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Menú de Localización");

        boton_servicios_externos.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        boton_servicios_externos.setText("Servicios Externos");
        boton_servicios_externos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_servicios_externosActionPerformed(evt);
            }
        });

        boton_clases_despachos.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        boton_clases_despachos.setText("Clases y Despachos");
        boton_clases_despachos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_clases_despachosActionPerformed(evt);
            }
        });

        boton_espacios_comunes.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        boton_espacios_comunes.setText("Espacios Comunes");
        boton_espacios_comunes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_espacios_comunesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(boton_servicios_externos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(boton_clases_despachos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
                    .addComponent(boton_espacios_comunes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(boton_clases_despachos, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(boton_espacios_comunes, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(boton_servicios_externos, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void boton_servicios_externosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_servicios_externosActionPerformed

    }//GEN-LAST:event_boton_servicios_externosActionPerformed

    private void boton_clases_despachosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_clases_despachosActionPerformed
        padre.botonLocalizacionClasesPulsado();
    }//GEN-LAST:event_boton_clases_despachosActionPerformed

    private void boton_espacios_comunesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_espacios_comunesActionPerformed
        padre.botonLocalizacionEspaciosComunesPulsado();
    }//GEN-LAST:event_boton_espacios_comunesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton_clases_despachos;
    private javax.swing.JButton boton_espacios_comunes;
    private javax.swing.JButton boton_servicios_externos;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
