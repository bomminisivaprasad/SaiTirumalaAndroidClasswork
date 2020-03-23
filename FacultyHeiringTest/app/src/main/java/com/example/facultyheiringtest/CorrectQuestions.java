package com.example.facultyheiringtest;

class CorrectQuestions {
    String cuurectQuestion,selectedAnswer;

    public CorrectQuestions(String cuurectQuestion, String selectedAnswer) {
        this.cuurectQuestion = cuurectQuestion;
        this.selectedAnswer = selectedAnswer;
    }

    public CorrectQuestions() {
    }

    public String getCuurectQuestion() {
        return cuurectQuestion;
    }

    public void setCuurectQuestion(String cuurectQuestion) {
        this.cuurectQuestion = cuurectQuestion;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }
}
