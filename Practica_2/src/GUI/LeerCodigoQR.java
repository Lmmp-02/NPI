package GUI;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import qr_scanner.Horario;

public class LeerCodigoQR extends JPanel implements Runnable, ThreadFactory {

    private WebcamPanel panel = null;
    private Webcam webcam = null;
    private Executor executor = Executors.newSingleThreadExecutor(this);
    private Ventana padre;

    private javax.swing.JTextField campo_resultado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel horarioLabel;

    public LeerCodigoQR(Ventana p) {
        this.padre = p;
        initComponents();
        initWebcam();
    }

    public String getLectura() {
        return campo_resultado.getText();
    }

    private void initComponents() {
        this.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        campo_resultado = new javax.swing.JTextField();
        horarioLabel = new javax.swing.JLabel();

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        this.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 370, 230));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel1.setText("Lectura");
        this.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, -1, -1));

        campo_resultado.addActionListener((java.awt.event.ActionEvent evt) -> {
            campo_resultadoActionPerformed(evt);
        });
        this.add(campo_resultado, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, 320, -1));
    }

    private void campo_resultadoActionPerformed(java.awt.event.ActionEvent evt) {
        // Código de acción aquí
    }

    private void initWebcam() {
        List<Webcam> lista_webcams;
        Dimension size = WebcamResolution.QVGA.getSize();

        do {
            lista_webcams = Webcam.getWebcams();
        } while (lista_webcams.isEmpty());

        webcam = lista_webcams.get(0);
        webcam.setViewSize(size);

        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(size);
        panel.setFPSDisplayed(true);

        jPanel2.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 470, 300));

        executor.execute(this);
    }

    public void closeWebcam() {
        if (webcam.isOpen()) {
            webcam.close();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(LeerCodigoQR.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }

            Result resultado = null;
            BufferedImage imagen = null;

            if (webcam.isOpen()) {
                imagen = webcam.getImage();
                if (imagen == null) {
                    continue;
                }
            }

            LuminanceSource fuente = new BufferedImageLuminanceSource(imagen);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(fuente));

            try {
                resultado = new MultiFormatReader().decode(bitmap);
            } catch (NotFoundException ex) {
                continue; // No se encontró un código QR
            }

            if (resultado != null) {
                String textoQR = resultado.getText();
                javax.swing.SwingUtilities.invokeLater(() -> {
                    campo_resultado.setText(textoQR);
                    closeWebcam();
                    // Actualizar y mostrar el JLabel con la imagen del Horario
                    Horario horario = new Horario();
                    horario.actualizarImagen(textoQR);
                    
                    jLabel1.setIcon(new ImageIcon(horario.getLabelIcon()));
                    // Suponiendo que Horario.actualizarImagen establece una imagen en un JLabel interno
                    

                    

                    // Ocultar o deshabilitar otros componentes de LeerCodigoQR
                    jLabel1.setText("");                
                    panel.setVisible(false);
                    campo_resultado.setEnabled(false);
                    campo_resultado.setVisible(false);
                });
                break; // Detiene el bucle, finaliza el hilo
            }
        }
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "Nueva hebra");
        t.setDaemon(true);
        return t;
    }
}
