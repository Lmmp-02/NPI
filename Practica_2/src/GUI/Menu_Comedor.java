/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

/**
 *
 * @author 34611
 */
public class Menu_Comedor extends javax.swing.JPanel {
    
    private Ventana padre;

    /**
     * Creates new form Menu_Comedor
     */
    public Menu_Comedor(Ventana p) {
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
        MenuAydanamar = new javax.swing.JButton();
        MenuParaLLevar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 204, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setText("Menú Comedores");

        MenuAydanamar.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        MenuAydanamar.setText("Menú Aydanamar");
        MenuAydanamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuAydanamarActionPerformed(evt);
            }
        });

        MenuParaLLevar.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        MenuParaLLevar.setText("Menú para llevar");
        MenuParaLLevar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuParaLLevarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(76, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(86, 86, 86))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(MenuParaLLevar, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MenuAydanamar))
                        .addGap(63, 63, 63))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106)
                .addComponent(MenuAydanamar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(135, 135, 135)
                .addComponent(MenuParaLLevar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(210, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void MenuAydanamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuAydanamarActionPerformed
        padre.botonMenuAydanamarPulsado();
    }//GEN-LAST:event_MenuAydanamarActionPerformed

    private void MenuParaLLevarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuParaLLevarActionPerformed
        padre.botonMenuParaLlevarPulsado();
    }//GEN-LAST:event_MenuParaLLevarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton MenuAydanamar;
    private javax.swing.JButton MenuParaLLevar;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}