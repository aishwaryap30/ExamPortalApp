package com.myproject.OnlineExamination.model;

import jakarta.persistence.*;

@Entity
public class UserAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ExamAttempt attempt;

    @ManyToOne
    private Question question;

    private String selectedOption;

    @Column(name = "is_correct", nullable = false)
    private boolean isCorrect; // ✅ Add this field

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExamAttempt getAttempt() {
        return attempt;
    }

    public void setAttempt(ExamAttempt attempt) {
        this.attempt = attempt;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
