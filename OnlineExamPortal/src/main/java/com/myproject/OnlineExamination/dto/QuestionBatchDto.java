package com.myproject.OnlineExamination.dto;

import java.util.ArrayList;
import java.util.List;

public class QuestionBatchDto {

    private Long examId;
    private List<QuestionDTO> questions = new ArrayList<>();

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }
}
