package com.myproject.OnlineExamination.service;

import com.myproject.OnlineExamination.model.Exam;
import com.myproject.OnlineExamination.model.Question;
import com.myproject.OnlineExamination.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void saveQuestions(List<Question> questions, Exam exam) {
        for (Question q : questions) {
            q.setExam(exam); // Associate each question with the given exam
        }
        questionRepository.saveAll(questions);
    }

    public List<Question> getQuestionsByExamId(Long examId) {
        return questionRepository.findByExam_ExamId(examId);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    public void updateQuestion(Question question) {
        questionRepository.save(question);
    }

    public List<Question> getQuestionsByExam(Exam exam) {
        return questionRepository.findByExam(exam);
    }

}
