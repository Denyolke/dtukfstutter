package com.example.stutter.model;

import java.util.List;

public class Question {
    public String question;
    public List<String> options;
    public int correctAnswer;
    public String explanation;

    public Question() {}

    public Question(String question, List<String> options, int correctAnswer, String explanation) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
    }
}
