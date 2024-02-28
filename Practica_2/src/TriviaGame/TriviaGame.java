/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TriviaGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Collections;
import java.util.Random;

public class TriviaGame extends JFrame {
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private JLabel questionLabel, timerLabel;
    private JPanel buttonPanel;
    private int score = 0;
    private Timer questionTimer;
    private final int QUESTION_TIME = 20; // 20 segundos por pregunta
    private final int MAX_QUESTIONS = 10; // Máximo de 10 preguntas

    public TriviaGame() {
        
        setTitle("Trivia Game");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        questionLabel = new JLabel("Pregunta", SwingConstants.CENTER);
        add(questionLabel, BorderLayout.NORTH);

        timerLabel = new JLabel("Tiempo: 20", SwingConstants.CENTER);
        add(timerLabel, BorderLayout.SOUTH);

        buttonPanel = new JPanel(new GridLayout(3, 1));
        add(buttonPanel, BorderLayout.CENTER);

        initializeQuestions();
        displayQuestion();
    }
    private void shuffleOptions(Question question) {
        String[] options = question.getOptions();
        int answerIndex = question.getAnswerIndex();
        String correctAnswer = options[answerIndex];

        Random rnd = new Random();
        for (int i = options.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            String temp = options[index];
            options[index] = options[i];
            options[i] = temp;
        }

        // Actualizar el índice de la respuesta correcta
        for (int i = 0; i < options.length; i++) {
            if (options[i].equals(correctAnswer)) {
                question.setAnswerIndex(i);
                break;
            }
        }
}

    private void initializeQuestions() {
        questions = new ArrayList<>();
        questions.add(new Question("¿Qué es un sistema operativo?",
                                   1,
                                   new String[]{"Un tipo de malware", "Es el software principal que gestiona los recursos del pc", "Un programa de procesamiento de textos"}));
        
        questions.add(new Question("¿Qué lenguaje de programación es conocido por su logo de una serpiente?",
                                   2,
                                   new String[]{"Java", "C++", "Python"}));

        questions.add(new Question("¿Cuál de estos es un dispositivo de entrada?",
                                   2,
                                   new String[]{"Monitor", "Impresora", "Teclado"}));

        questions.add(new Question("¿Qué significa HTML?",
                                   0,
                                   new String[]{"HyperText Markup Language", "HighText Machine Language", "HyperType Marked Language"}));

        questions.add(new Question("¿Qué es una dirección IP?",
                                   2,
                                   new String[]{"Un código de seguridad de Internet", "Una dirección de correo electrónico", "Una etiqueta numérica que identifica un dispositivo en una red"}));
        questions.add(new Question("¿Qué es una base de datos?",
                                   2,
                                   new String[]{"Un tipo de virus informático", "Un programa de diseño gráfico", "Un sistema organizado para almacenar, gestionar y recuperar información"}));

        questions.add(new Question("¿Cuál de los siguientes es un lenguaje de programación orientado a objetos?",
                                   2,
                                   new String[]{"HTML", "SQL", "Java"}));

        questions.add(new Question("¿Qué es un algoritmo en informática?",
                                   2,
                                   new String[]{"Un problema de hardware", "Un tipo de malware", "Un conjunto de instrucciones o reglas definidas para realizar una tarea"}));

        questions.add(new Question("¿Qué dispositivo se utiliza principalmente para almacenar datos de manera permanente?",
                                   2,
                                   new String[]{"RAM", "CPU", "Disco duro"}));

        questions.add(new Question("¿Qué es un bucle en programación?",
                                   1,
                                   new String[]{"Una función especial", "Una estructura que repite una secuencia de instrucciones", "Un error en el código"}));

        questions.add(new Question("¿Qué es un servidor?",
                                   2,
                                   new String[]{"Un tipo de software antivirus", "Un dispositivo de entrada", "Una computadora o sistema que proporciona recursos, datos, servicios o programas a otras computadoras"}));

        questions.add(new Question("¿Qué es el 'cloud computing' o computación en la nube?",
                                   1,
                                   new String[]{"Un término para el almacenamiento de datos en discos duros", "La entrega de servicios de computación a través de Internet, incluyendo almacenamiento y procesamiento de datos", "Un software para gestionar correos electrónicos"}));

        questions.add(new Question("¿Qué representa el término 'bit' en informática?",
                                   2,
                                   new String[]{"Una unidad de velocidad de Internet", "Un programa de edición de texto", "La unidad más pequeña de datos en computación, que puede ser 0 o 1"}));

        questions.add(new Question("¿Cuál es la función principal de la memoria RAM en un ordenador?",
                                   2,
                                   new String[]{"Almacenar datos de forma permanente", "Procesar gráficos y videos", "Almacenar temporalmente los datos que están siendo utilizados activamente"}));

        questions.add(new Question("¿Qué es un firewall?",
                                   2,
                                   new String[]{"Un tipo de malware", "Un sistema operativo", "Un sistema de seguridad que monitorea y controla el tráfico de red entrante y saliente"}));
        questions.add(new Question("¿Qué es el phishing?",
                                   1,
                                   new String[]{"Una técnica de programación", "Un tipo de ataque cibernético que intenta obtener información sensible de manera fraudulenta", "Un servicio de correo electrónico"}));

        questions.add(new Question("¿Qué significa 'GUI' en informática?",
                                   0,
                                   new String[]{"Graphical User Interface, interfaz gráfica de usuario", "General Unified Installer, instalador unificado general", "Global User Interaction, interacción global de usuario"}));

        questions.add(new Question("¿Qué lenguaje de programación se utiliza principalmente para el desarrollo web?",
                                   1,
                                   new String[]{"C#", "JavaScript", "Python"}));

        questions.add(new Question("¿Qué es el software de código abierto?",
                                   2,
                                   new String[]{"Un programa que es gratuito", "Software que está en fase beta", "Software cuyo código fuente es accesible y puede ser modificado o compartido"}));

        questions.add(new Question("¿Qué es un sistema de gestión de bases de datos (DBMS)?",
                                   2,
                                   new String[]{"Un software antivirus", "Un sistema operativo", "Un software que proporciona los medios para almacenar, recuperar, modificar y gestionar datos en una base de datos"}));

        questions.add(new Question("¿Qué significa 'CPU'?",
                                   1,
                                   new String[]{"Central Processing Unit", "Computer Processing Unit", "Critical Performance Upgrade"}));

        questions.add(new Question("¿Qué es una red LAN?",
                                   1,
                                   new String[]{"Large Area Network", "Local Area Network", "Long Access Node"}));

        questions.add(new Question("¿Qué es un sistema de control de versiones?",
                                   2,
                                   new String[]{"Un tipo de software de seguridad", "Un sistema de base de datos", "Un sistema que registra los cambios en los archivos a lo largo del tiempo"}));

        questions.add(new Question("¿Qué es un dominio en Internet?",
                                   2,
                                   new String[]{"Un tipo de virus informático", "Una herramienta de software", "Un nombre único asignado a una dirección IP para facilitar su acceso"}));

        questions.add(new Question("¿Qué es SEO?",
                                   2,
                                   new String[]{"Software Essential Operation", "Secure Electronic Order", "Search Engine Optimization"}));
        questions.add(new Question("¿Qué es la programación orientada a objetos?",
                                   2,
                                   new String[]{"Un lenguaje de programación", "Un tipo de base de datos", "Un paradigma de programación basado en el concepto de objetos"}));

        questions.add(new Question("¿Qué es un protocolo en redes?",
                                   1,
                                   new String[]{"Un tipo de hardware", "Un conjunto de reglas que determinan cómo se transmiten los datos entre dispositivos", "Una aplicación de mensajería"}));

        questions.add(new Question("¿Qué significa 'HTTP'?",
                                   2,
                                   new String[]{"High Transfer Text Protocol", "Hypertext Transfer Protocol", "Hyperlink Tracking Technology Protocol"}));

        questions.add(new Question("¿Qué es la encriptación?",
                                   2,
                                   new String[]{"Un método de compresión de datos", "Un sistema operativo", "El proceso de convertir información o datos en un código para prevenir accesos no autorizados"}));

        questions.add(new Question("¿Qué es la inteligencia artificial?",
                                   2,
                                   new String[]{"Una nueva generación de procesadores", "Un tipo de virus informático", "Simulación de procesos de inteligencia humana por sistemas informáticos"}));

        questions.add(new Question("¿Qué es un bug en programación?",
                                   1,
                                   new String[]{"Un tipo de virus informático", "Un error o fallo en un programa que causa un comportamiento incorrecto o inesperado", "Una característica especial de un programa"}));

        questions.add(new Question("¿Qué es un dominio web?",
                                   2,
                                   new String[]{"Un tipo de virus informático", "Una herramienta de software", "Un nombre único asignado a una dirección IP para facilitar su acceso"}));

        questions.add(new Question("¿Qué es un sistema de archivos?",
                                   1,
                                   new String[]{"Un tipo de software antivirus", "Un método para almacenar y organizar archivos en un medio de almacenamiento", "Una parte del procesador"}));

        questions.add(new Question("¿Qué es un algoritmo de búsqueda?",
                                   2,
                                   new String[]{"Un protocolo de red", "Un virus informático", "Un proceso para encontrar un elemento dentro de una estructura de datos"}));

        questions.add(new Question("¿Qué es la memoria virtual?",
                                   1,
                                   new String[]{"Una nueva tecnología de disco duro", "Una técnica que usa el disco duro como extensión de la memoria RAM", "Un tipo de memoria RAM más rápida"}));
        questions.add(new Question("¿Qué es una cookie en términos de web?",
                                   1,
                                   new String[]{"Un programa de seguimiento en línea", "Un pequeño archivo de datos almacenado en el navegador del usuario por un sitio web", "Una herramienta de diseño web"}));

        questions.add(new Question("¿Qué es un sistema operativo de código abierto?",
                                   2,
                                   new String[]{"Un sistema operativo desarrollado por Microsoft", "Un sistema operativo que es gratuito", "Un sistema operativo cuyo código fuente está disponible públicamente para ser modificado y distribuido"}));

        questions.add(new Question("¿Qué es un SSD en informática?",
                                   1,
                                   new String[]{"Software System Design", "Solid State Drive", "Secure Server Domain"}));

        questions.add(new Question("¿Qué es una VPN?",
                                   0,
                                   new String[]{"Virtual Private Network", "Virus Protection Notification", "Variable Path Network"}));

        questions.add(new Question("¿Qué es un compilador en programación?",
                                   1,
                                   new String[]{"Una herramienta de depuración", "Un programa que convierte el código fuente en código máquina", "Un tipo de software antivirus"}));

        questions.add(new Question("¿Qué es un IDE en programación?",
                                   0,
                                   new String[]{"Integrated Development Environment", "Internet Data Encryption", "Internal Disk Error"}));

        questions.add(new Question("¿Qué es un framework en desarrollo de software?",
                                   1,
                                   new String[]{"Un tipo de lenguaje de programación", "Una plataforma de trabajo predefinida que sirve como base para el desarrollo de software", "Un protocolo de red"}));

        questions.add(new Question("¿Qué es la autenticación de dos factores?",
                                   2,
                                   new String[]{"Un proceso de instalación de software", "Un tipo de encriptación", "Un método de seguridad que requiere dos tipos diferentes de verificación de identidad"}));

        questions.add(new Question("¿Qué es un ataque DDoS?",
                                   1,
                                   new String[]{"Data Delivery Optimization Service", "Distributed Denial of Service", "Direct Data Output System"}));

        questions.add(new Question("¿Qué es un script en programación?",
                                   1,
                                   new String[]{"Un tipo de virus informático", "Un programa o secuencia de instrucciones que se ejecuta en un entorno específico", "Una herramienta de base de datos"}));

        questions.add(new Question("¿Qué es el Big Data?",
                                   2,
                                   new String[]{"Un tipo de base de datos en la nube", "Una nueva tecnología de almacenamiento de datos", "Grandes conjuntos de datos que se analizan para revelar patrones, tendencias y asociaciones"}));

        questions.add(new Question("¿Qué es la criptografía?",
                                   1,
                                   new String[]{"El estudio de los algoritmos de búsqueda", "El arte de escribir o resolver códigos", "Una técnica de desarrollo de software"}));

        questions.add(new Question("¿Qué es el Machine Learning?",
                                   1,
                                   new String[]{"Una nueva generación de hardware para computadoras", "Un campo de la inteligencia artificial que usa algoritmos para aprender de los datos", "Un tipo de base de datos"}));

        questions.add(new Question("¿Qué es un pixel?",
                                   2,
                                   new String[]{"Una unidad de medida de velocidad de Internet", "Una herramienta de diseño gráfico", "El punto más pequeño de una imagen en una pantalla digital"}));

        questions.add(new Question("¿Qué es una API?",
                                   1,
                                   new String[]{"Advanced Programming Interface", "Application Programming Interface", "Automated Processing Instruction"}));
        
        for (Question question : questions) {
            shuffleOptions(question);
        }
        
        Collections.shuffle(questions);
    }

    private void displayQuestion() {
        if (currentQuestionIndex >= MAX_QUESTIONS) {
            gameOver();
            return;
        }

        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            questionLabel.setText(currentQuestion.getQuestion());
            buttonPanel.removeAll();

            for (String option : currentQuestion.getOptions()) {
                JButton optionButton = new JButton(option);
                optionButton.addActionListener((ActionEvent e) -> handleAnswer(option, optionButton));
                buttonPanel.add(optionButton);
            }

            buttonPanel.revalidate();
            buttonPanel.repaint();
            startTimer();
        }
    }

    private void startTimer() {
        timerLabel.setText("Tiempo: " + QUESTION_TIME);
        questionTimer = new Timer(1000, new AbstractAction() {
            int timeLeft = QUESTION_TIME;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeLeft > 0) {
                    timerLabel.setText("Tiempo: " + --timeLeft);
                } else {
                    ((Timer) e.getSource()).stop();
                    handleTimeout();
                }
            }
        });
        questionTimer.start();
    }


    private void handleTimeout() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        String correctAnswer = currentQuestion.getAnswer();
        for (Component comp : buttonPanel.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                if (button.getText().equalsIgnoreCase(correctAnswer)) {
                    button.setBackground(Color.GREEN);
                }
                button.setEnabled(false); // Deshabilitar todos los botones
            }
    }

    Timer pause = new Timer(1000, e -> {
        resetButtonColors();
        currentQuestionIndex++;
        if (currentQuestionIndex < MAX_QUESTIONS) {
            displayQuestion();
        } else {
            gameOver();
        }
    });
    pause.setRepeats(false);
    pause.start();
    }

private void handleAnswer(String selectedOption, JButton selectedButton) {
    // Deshabilitar todos los botones inmediatamente para evitar múltiples clics
    for (Component comp : buttonPanel.getComponents()) {
        comp.setEnabled(false);
    }

    if (questionTimer != null) {
        questionTimer.stop(); // Detener el temporizador actual
    }
    
    
    
    Question currentQuestion = questions.get(currentQuestionIndex);
    String correctAnswer = currentQuestion.getAnswer(); // Obtener la respuesta correcta usando el índice

    boolean isCorrect = selectedOption.equalsIgnoreCase(correctAnswer);
    selectedButton.setBackground(isCorrect ? Color.GREEN : Color.RED);

    selectedButton.setBackground(isCorrect ? Color.GREEN : Color.RED);
    if (isCorrect) {
        score++;
    }
    Timer pause = new Timer(1000, e -> {
        resetButtonColors();
        currentQuestionIndex++;
        if (currentQuestionIndex < MAX_QUESTIONS) {
            displayQuestion();
        } else {
            gameOver();
        }
    });
    pause.setRepeats(false);
    pause.start();
    
}
    

    private void resetButtonColors() {
        for (Component comp : buttonPanel.getComponents()) {
            comp.setBackground(null);
            comp.setEnabled(true);
        }
    }

    private void gameOver() {
        JOptionPane.showMessageDialog(this, "Juego terminado. Aciertos: " + score + ", Errores: " + (MAX_QUESTIONS - score));
        currentQuestionIndex = 0;
        score = 0;
        displayQuestion();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TriviaGame game = new TriviaGame();
            game.setVisible(true);
        });
    }

}