package com.myproject.OnlineExamination.controller;

import com.myproject.OnlineExamination.dto.QuestionListWrapper;
import com.myproject.OnlineExamination.model.Exam;
import com.myproject.OnlineExamination.model.ExamAttempt;
import com.myproject.OnlineExamination.model.Question;
import com.myproject.OnlineExamination.model.User;
import com.myproject.OnlineExamination.repository.ExamAttemptRepository;
import com.myproject.OnlineExamination.repository.ExamRepository;
import com.myproject.OnlineExamination.service.ExamService;
import com.myproject.OnlineExamination.service.QuestionService;
import com.myproject.OnlineExamination.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/exams")
public class AdminController {

    @Autowired
    private ExamService examService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ExamRepository examRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private ExamAttemptRepository examAttemptRepository;



    @GetMapping("/create")
    public String showCreateExamForm(Model model) {
        model.addAttribute("exam", new Exam());
        return "create-exam";  // file: templates/create-exam.html
    }

    @PostMapping("/create")
    public String createExam(@ModelAttribute Exam exam, HttpSession session) {
        // Set the currently logged-in user as exam creator
        User loggedInUser = (User) session.getAttribute("user");
        if (loggedInUser != null) {
            exam.setCreatedBy(loggedInUser);
        }
        Exam savedExam = examService.createExam(exam);
        return "redirect:/admin/exams/" + savedExam.getExamId() + "/add-questions";
    }

    @GetMapping("/{examId}/add-questions")
    public String showAddQuestionsForm(@PathVariable Long examId, Model model) {
        QuestionListWrapper wrapper = new QuestionListWrapper();
        for (int i = 0; i < 20; i++) {
            wrapper.getQuestions().add(new Question());
        }
        model.addAttribute("questionWrapper", wrapper);
        model.addAttribute("examId", examId);
        return "add-questions";
    }

    @PostMapping("/{examId}/add-questions")
    public String saveQuestions(@PathVariable Long examId,
                                @ModelAttribute("questionWrapper") QuestionListWrapper wrapper) {
        Exam exam = examService.getExamById(examId).orElseThrow();
        questionService.saveQuestions(wrapper.getQuestions(), exam);
        return "redirect:/admin/exams";

    }

    @GetMapping
    public String viewAllExams(Model model) {
        List<Exam> exams = examRepo.findByDeletedFalse(); // instead of findAll()

        model.addAttribute("exams", exams);
        return "manage-exam"; // Make sure this matches your Thymeleaf template
    }


    @PostMapping("/delete/{examId}")
    public String deleteExam(@PathVariable Long examId) {
        examService.softDeleteExam(examId); // not actual delete
        return "redirect:/admin/exams";
    }


    @GetMapping("/manage-user")
    public String showAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("userList", users); // Matches ${userList} in Thymeleaf
        return "manage-user";
    }
    @PostMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/exams/manage-user";
    }

//---------------------------------------------------------------------------------------------------------

    @GetMapping("/show-results")
    public String showAllExamResults(Model model) {
        List<Exam> allExams = examService.getAllExams();

        Map<Exam, List<ExamAttempt>> examResults = new LinkedHashMap<>();
        for (Exam exam : allExams) {
            List<ExamAttempt> attempts = examAttemptRepository.findByExam(exam);
            examResults.put(exam, attempts);
        }

        model.addAttribute("examResults", examResults);
        return "show-results";
    }

}
