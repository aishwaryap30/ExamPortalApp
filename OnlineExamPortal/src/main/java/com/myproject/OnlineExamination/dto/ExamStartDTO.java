package com.myproject.OnlineExamination.dto;

import java.util.List;

public class ExamStartDTO {

    private Long attemptId;
    private int duration;
    private List<QuestionDTO> questions;

    public ExamStartDTO(Long attemptId, int duration, List<QuestionDTO> questions) {
        this.attemptId = attemptId;
        this.duration = duration;
        this.questions = questions;
    }


    public Long getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(Long attemptId) {
        this.attemptId = attemptId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }
}
