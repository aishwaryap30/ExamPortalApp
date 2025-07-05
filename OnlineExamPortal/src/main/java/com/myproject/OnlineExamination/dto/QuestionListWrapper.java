package com.myproject.OnlineExamination.dto;

import com.myproject.OnlineExamination.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionListWrapper {

    private List<Question> questions;

    public QuestionListWrapper() {
        questions = new ArrayList<>();
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
