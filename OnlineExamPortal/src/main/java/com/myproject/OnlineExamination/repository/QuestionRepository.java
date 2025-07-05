package com.myproject.OnlineExamination.repository;

import com.myproject.OnlineExamination.model.Exam;
import com.myproject.OnlineExamination.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {

     List<Question> findByExam_ExamId(Long examId);

    List<Question> findByExam(Exam exam);
}
