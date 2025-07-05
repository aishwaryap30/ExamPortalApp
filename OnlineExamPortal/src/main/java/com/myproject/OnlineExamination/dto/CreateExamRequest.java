package com.myproject.OnlineExamination.dto;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.LocalDateTime;
import java.util.List;

public class CreateExamRequest {

    private String title;
    private String description;
    private Integer durationInMinutes;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<QuestionRequest> questions;

    // Getters and Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public List<QuestionRequest> getQuestions() {
        return questions;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setQuestions(List<QuestionRequest> questions) {
        this.questions = questions;
    }



    // Inner static class for question details
    public static class QuestionRequest {
        private String questionText;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String correctAnswer;

        // Getters and Setters

        public String getQuestionText() {
            return questionText;
        }

        public void setQuestionText(String questionText) {
            this.questionText = questionText;
        }

        public String getOptionA() {
            return optionA;
        }

        public void setOptionA(String optionA) {
            this.optionA = optionA;
        }

        public String getOptionB() {
            return optionB;
        }

        public void setOptionB(String optionB) {
            this.optionB = optionB;
        }

        public String getOptionC() {
            return optionC;
        }

        public void setOptionC(String optionC) {
            this.optionC = optionC;
        }

        public String getOptionD() {
            return optionD;
        }

        public void setOptionD(String optionD) {
            this.optionD = optionD;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }

        public void setCorrectAnswer(String correctAnswer) {
            this.correctAnswer = correctAnswer;
        }
    }
}
