package com.myproject.OnlineExamination.service;
import com.myproject.OnlineExamination.model.*;
import com.myproject.OnlineExamination.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.Optional;


@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;


    public Exam createExam(Exam exam) {
        return examRepo.save(exam);
    }

    public List<Exam> getAllExams() {
        return examRepo.findAll();
    }

    public Optional<Exam> getExamById(Long examId) {
        return examRepo.findById(examId);
    }

    public void softDeleteExam(Long id) {
        Optional<Exam> optionalExam = examRepo.findById(id);
        if (optionalExam.isPresent()) {
            Exam exam = optionalExam.get();
            exam.setDeleted(true);
            exam.setAvailable(false); // also remove it from public view
            examRepo.save(exam);
        }
    }


    public void deleteExam(Long id) {
        examRepo.deleteById(id);
    }

    public List<Exam> getAllAvailableExamsForUser() {
        return examRepo.findByAvailableTrue();
    }
// _--------------------------------------------------------------new working



}
