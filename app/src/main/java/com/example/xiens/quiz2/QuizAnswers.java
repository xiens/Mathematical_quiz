package com.example.xiens.quiz2;

/**
 * Author: Zbigniew Pamula
 * version 1.0
 */

public class QuizAnswers {
    private int id; //primary key id of answer
    private String correct; //true or false
    private Integer radioButtonId; //id of checked radio button

    public QuizAnswers(){

    }

    public QuizAnswers(int id, String correct, Integer radioButtonId) {
        this.id = id;
        this.correct = correct;
        this.radioButtonId = radioButtonId;
    }

    public QuizAnswers(String correct, Integer radioButtonId) {
        this.correct = correct;
        this.radioButtonId = radioButtonId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public Integer getRadioButtonId() {
        return radioButtonId;
    }

    public void setRadioButtonId(Integer radioButtonId) {
        this.radioButtonId = radioButtonId;
    }
}
