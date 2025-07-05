package com.myproject.OnlineExamination.dto;

import java.time.LocalDateTime;

public class PublishExamRequest {

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public PublishExamRequest(){}

    public PublishExamRequest(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "PublishExamRequest{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
