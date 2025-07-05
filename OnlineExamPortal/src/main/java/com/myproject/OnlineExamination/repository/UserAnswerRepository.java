package com.myproject.OnlineExamination.repository;

import com.myproject.OnlineExamination.model.ExamAttempt;
import com.myproject.OnlineExamination.model.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {

    List<UserAnswer> findByAttempt(ExamAttempt attempt);
}
