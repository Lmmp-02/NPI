/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

/**
 *
 * @author Usuario
 */
public class Confirmacion_Pago extends javax.swing.JPanel {

    /**
     * Creates new form Confirmacion_Menu_Final
     */
    private Ventana padre;
    private String codigo;
    
    public Confirmacion_Pago(Ventana p) {
        initComponents();
        padre = p;
        codigo = "";
    }
    
    public void setCodigo(String c){
        this.codigo = c;
        txtfCodigo.setText(c);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel_titulo1 = new javax.swing.JLabel();
        boton_si = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtfCodigo = new javax.swing.JTextField();

        setOpaque(false);

        jLabel_titulo1.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel_titulo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_titulo1.setText("Pago confirmado");

        boton_si.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        boton_si.setText("Volver al menú");
        boton_si.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_siActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Código:");

        txtfCodigo.setEditable(false);
        txtfCodigo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        txtfCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfCodigoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_titulo1, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(boton_si, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel_titulo1, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(156, 156, 156)
                .addComponent(boton_si, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void boton_siActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_siActionPerformed
        padre.siguiente();
    }//GEN-LAST:event_boton_siActionPerformed

    private void txtfCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfCodigoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton_si;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel_titulo1;
    private javax.swing.JTextField txtfCodigo;
    // End of variables declaration//GEN-END:variables
}
