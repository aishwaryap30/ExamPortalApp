package com.myproject.OnlineExamination.repository;

import com.myproject.OnlineExamination.model.Exam;
import com.myproject.OnlineExamination.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ExamRepository extends JpaRepository<Exam,Long>{

    List<Exam> findByAvailableTrue();

    // Fetch only active (non-deleted) and available exams
    List<Exam> findByAvailableTrueAndDeletedFalse();

    // Admin view - get only non-deleted exams
    List<Exam> findByDeletedFalse();

}
