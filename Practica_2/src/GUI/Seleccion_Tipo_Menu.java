/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

/**
 *
 * @author 34611
 */
public class Seleccion_Tipo_Menu extends javax.swing.JPanel {

    private Ventana padre;
    
    /**
     * Creates new form Seleccion_Tipo_Menu
     */
    public Seleccion_Tipo_Menu(Ventana p) {
        initComponents();
        padre = p;        
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
        Almuerzo = new javax.swing.JButton();
        Cena = new javax.swing.JButton();
        Ovolacteoveg = new javax.swing.JButton();
        Vegano = new javax.swing.JButton();
        Celiaco = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setText("¿Qué menú desea?");

        Almuerzo.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        Almuerzo.setText("Almuerzo");
        Almuerzo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AlmuerzoActionPerformed(evt);
            }
        });

        Cena.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        Cena.setText("Cena");
        Cena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CenaActionPerformed(evt);
            }
        });

        Ovolacteoveg.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        Ovolacteoveg.setText("Ovolacteovegetariano");
        Ovolacteoveg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OvolacteovegActionPerformed(evt);
            }
        });

        Vegano.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        Vegano.setText("Vegano");
        Vegano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VeganoActionPerformed(evt);
            }
        });

        Celiaco.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        Celiaco.setText("T-Celiaco");
        Celiaco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CeliacoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 53, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(Almuerzo)
                        .addGap(135, 135, 135))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(Cena)
                        .addGap(166, 166, 166))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(Ovolacteoveg)
                        .addGap(28, 28, 28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(Vegano)
                        .addGap(143, 143, 143))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Celiaco)
                .addGap(134, 134, 134))
            .addGroup(layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(jLabel1)
                .addGap(56, 56, 56)
                .addComponent(Almuerzo)
                .addGap(58, 58, 58)
                .addComponent(Cena)
                .addGap(52, 52, 52)
                .addComponent(Ovolacteoveg)
                .addGap(61, 61, 61)
                .addComponent(Vegano)
                .addGap(51, 51, 51)
                .addComponent(Celiaco)
                .addContainerGap(85, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void AlmuerzoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlmuerzoActionPerformed
        padre.siguiente();
    }//GEN-LAST:event_AlmuerzoActionPerformed

    private void CenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CenaActionPerformed
        padre.siguiente();
    }//GEN-LAST:event_CenaActionPerformed

    private void OvolacteovegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OvolacteovegActionPerformed
        padre.siguiente();
    }//GEN-LAST:event_OvolacteovegActionPerformed

    private void VeganoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VeganoActionPerformed
        padre.siguiente();
    }//GEN-LAST:event_VeganoActionPerformed

    private void CeliacoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CeliacoActionPerformed
        padre.siguiente();
    }//GEN-LAST:event_CeliacoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Almuerzo;
    private javax.swing.JButton Celiaco;
    private javax.swing.JButton Cena;
    private javax.swing.JButton Ovolacteoveg;
    private javax.swing.JButton Vegano;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
