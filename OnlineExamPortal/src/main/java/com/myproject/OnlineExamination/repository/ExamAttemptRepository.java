package com.myproject.OnlineExamination.repository;

import com.myproject.OnlineExamination.model.Exam;
import com.myproject.OnlineExamination.model.ExamAttempt;
import com.myproject.OnlineExamination.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamAttemptRepository extends JpaRepository<ExamAttempt, Long> {

    List<ExamAttempt> findByUser(User user);

    Optional<ExamAttempt> findByUserAndExam(User user, Exam exam);

    List<ExamAttempt> findByExam(Exam exam);
}
