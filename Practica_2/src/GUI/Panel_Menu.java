/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import SQL.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class Panel_Menu extends javax.swing.JPanel {

    /**
     * Creates new form Panel_Menu
     */
    private Ventana padre;
    
    private int dia;
    private static int max_dia = 6;
    
    public Panel_Menu(Ventana p) {
        this.padre = p;
        this.dia = 0;
        
        initComponents();
        actualizarMenu();
    }
    
    public String getDia(){
        switch(dia){
            case 0: return "Lunes";
            case 1: return "Martes";
            case 2: return "Miercoles";
            case 3: return "Jueves";
            case 4: return "Viernes";
            case 5: return "Sabado";
        }
        
        return "Lunes";
    }
    
    public void mostrarDiaAnterior() {
        dia = (dia - 1 + max_dia) % max_dia;
        actualizarMenu();
    }

    public void mostrarDiaSiguiente() {
        dia = (dia + 1) % max_dia;
        actualizarMenu();
    }
    
    public void actualizarMenu(){
        Connection con = null;
        
        PreparedStatement ps;
        ResultSet rs;

        try{
            con = Conexion.getConexion("Comedor");
            
            //Actualizamos menú 1
            String cmd = "SELECT * FROM Menus WHERE Dia = ? AND TipoMenu = 1;";
            ps = con.prepareStatement(cmd);
            
            ps.setString(1, getDia());
            
            rs = ps.executeQuery();
            
            if(rs.next()){
                txtPrimero.setText(rs.getString("Primero"));
                txtSegundo.setText(rs.getString("Segundo"));
                txtAcompaniamiento.setText(rs.getString("Acompanamiento"));
                txtPostre.setText(rs.getString("Postre"));
            }
            else{
                JOptionPane.showMessageDialog(null, "No hay resultados validos");
            }
            
            //Actualizamos menú 2
            cmd = "SELECT * FROM Menus WHERE Dia = ? AND TipoMenu = 2;";
            ps = con.prepareStatement(cmd);
            
            ps.setString(1, getDia());
            
            rs = ps.executeQuery();
            
            if(rs.next()){
                txtPrimero1.setText(rs.getString("Primero"));
                txtSegundo1.setText(rs.getString("Segundo"));
                txtAcompaniamiento1.setText(rs.getString("Acompanamiento"));
                txtPostre1.setText(rs.getString("Postre"));
            }
            else{
                JOptionPane.showMessageDialog(null, "No hay resultados validos");
            }

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        
        //Actualiza también el título
        tagTitulo.setText(getDia());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tagTitulo = new javax.swing.JLabel();
        btnArriba = new javax.swing.JButton();
        btnAbajo = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtPostre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtSegundo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtAcompaniamiento = new javax.swing.JTextField();
        txtPrimero = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtPostre1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtSegundo1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtAcompaniamiento1 = new javax.swing.JTextField();
        txtPrimero1 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(450, 750));

        tagTitulo.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        tagTitulo.setText(getDia());

        btnArriba.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        btnArriba.setText("/\\");
            btnArriba.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnArribaActionPerformed(evt);
                }
            });

            btnAbajo.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
            btnAbajo.setText("\\/");
            btnAbajo.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnAbajoActionPerformed(evt);
                }
            });

            jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
            jLabel5.setText("Postre:");

            txtPostre.setEditable(false);
            txtPostre.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

            jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
            jLabel6.setText("Acompanamiento:");

            jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
            jLabel4.setText("Segundo:");

            txtSegundo.setEditable(false);
            txtSegundo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
            txtSegundo.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    txtSegundoActionPerformed(evt);
                }
            });

            jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
            jLabel7.setText("Primero:");

            txtAcompaniamiento.setEditable(false);
            txtAcompaniamiento.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
            txtAcompaniamiento.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    txtAcompaniamientoActionPerformed(evt);
                }
            });

            txtPrimero.setEditable(false);
            txtPrimero.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
            txtPrimero.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    txtPrimeroActionPerformed(evt);
                }
            });

            jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
            jLabel8.setText("Menú 1");

            jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
            jLabel9.setText("Postre:");

            txtPostre1.setEditable(false);
            txtPostre1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

            jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
            jLabel10.setText("Acompanamiento:");

            jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
            jLabel11.setText("Segundo:");

            txtSegundo1.setEditable(false);
            txtSegundo1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
            txtSegundo1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    txtSegundo1ActionPerformed(evt);
                }
            });

            jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
            jLabel12.setText("Primero:");

            txtAcompaniamiento1.setEditable(false);
            txtAcompaniamiento1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
            txtAcompaniamiento1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    txtAcompaniamiento1ActionPerformed(evt);
                }
            });

            txtPrimero1.setEditable(false);
            txtPrimero1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
            txtPrimero1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    txtPrimero1ActionPerformed(evt);
                }
            });

            jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
            jLabel13.setText("Menú 2:");

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(116, 116, 116)
                                    .addComponent(tagTitulo))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(195, 195, 195)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btnAbajo)
                                        .addComponent(btnArriba))))
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtAcompaniamiento, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtPostre, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, Short.MAX_VALUE)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtPrimero, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtSegundo, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel10)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtAcompaniamiento1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtPostre1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, Short.MAX_VALUE)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtPrimero1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtSegundo1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(23, 23, 23)
                    .addComponent(tagTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(btnArriba, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(11, 11, 11)
                    .addComponent(jLabel8)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txtPrimero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtSegundo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtAcompaniamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtPostre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(31, 31, 31)
                    .addComponent(jLabel13)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(txtPrimero1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(txtSegundo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(txtAcompaniamiento1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txtPostre1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                    .addComponent(btnAbajo, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(32, 32, 32))
            );
        }// </editor-fold>//GEN-END:initComponents

    private void btnArribaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArribaActionPerformed
        mostrarDiaAnterior();
    }//GEN-LAST:event_btnArribaActionPerformed

    private void btnAbajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbajoActionPerformed
        mostrarDiaSiguiente();
    }//GEN-LAST:event_btnAbajoActionPerformed

    private void txtSegundoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSegundoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSegundoActionPerformed

    private void txtAcompaniamientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAcompaniamientoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAcompaniamientoActionPerformed

    private void txtPrimeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrimeroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrimeroActionPerformed

    private void txtSegundo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSegundo1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSegundo1ActionPerformed

    private void txtAcompaniamiento1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAcompaniamiento1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAcompaniamiento1ActionPerformed

    private void txtPrimero1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrimero1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrimero1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbajo;
    private javax.swing.JButton btnArriba;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel tagTitulo;
    private javax.swing.JTextField txtAcompaniamiento;
    private javax.swing.JTextField txtAcompaniamiento1;
    private javax.swing.JTextField txtPostre;
    private javax.swing.JTextField txtPostre1;
    private javax.swing.JTextField txtPrimero;
    private javax.swing.JTextField txtPrimero1;
    private javax.swing.JTextField txtSegundo;
    private javax.swing.JTextField txtSegundo1;
    // End of variables declaration//GEN-END:variables
}
