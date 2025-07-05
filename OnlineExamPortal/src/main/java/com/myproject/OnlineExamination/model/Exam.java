package com.myproject.OnlineExamination.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exam")
public class Exam {

    @Id
    @Column(name = "examId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long examId;

    private String title;
    private String description;
    private int duration;
    private boolean available = false;
    private boolean deleted = false;


    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User createdBy;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getCreatedBy() {
        return createdBy.getUserName();
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
