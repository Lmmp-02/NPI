/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TriviaGame;
public class Question {
    private final String question;
    private int answerIndex; // Índice de la respuesta correcta
    private final String[] options;

    public Question(String question, int answerIndex, String[] options) {
        this.question = question;
        this.answerIndex = answerIndex;
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return options[answerIndex]; // Retorna la respuesta correcta basada en el índice
    }

    public String[] getOptions() {
        return options;
    }
    public int getAnswerIndex() {
        return answerIndex;
    }
    public void setAnswerIndex(int index) {
        this.answerIndex = index;
    }
}

