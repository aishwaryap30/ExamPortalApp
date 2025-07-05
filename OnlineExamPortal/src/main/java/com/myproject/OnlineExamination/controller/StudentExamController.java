package com.myproject.OnlineExamination.controller;

import com.myproject.OnlineExamination.model.*;
import com.myproject.OnlineExamination.repository.ExamAttemptRepository;
import com.myproject.OnlineExamination.repository.QuestionRepository;
import com.myproject.OnlineExamination.repository.UserRepository;
import com.myproject.OnlineExamination.service.ExamService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;


@Controller
@RequestMapping("/student/exams")
public class StudentExamController {


    @Autowired
    private ExamService examService;

    @Autowired
    private QuestionRepository examRepository;

    @Autowired
    private ExamAttemptRepository examAttemptRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String showAvailableExams(Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login"; // User not logged in
        }

        Long userId = user.getId(); // ✅ This is the ID you need

        model.addAttribute("userId", userId);


        List<Exam> exams = examService.getAllAvailableExamsForUser();
        model.addAttribute("exams", exams);
        return "available-exams"; // Make sure this matches your template filename
    }

    // __________________________________________________________new working


    @PostMapping("/start/{examId}")
    public String startExam(@PathVariable Long examId, HttpSession session, Model model) {

        session.removeAttribute("examQuestions");
        session.removeAttribute("currentIndex");
        session.removeAttribute("selectedAnswers");
        session.removeAttribute("remainingTime");

        User user = (User) session.getAttribute("user");
        Exam exam = examService.getExamById(examId).orElseThrow();
        List<Question> questions = new ArrayList<>(exam.getQuestions());

        if (questions == null || questions.isEmpty()) {
            model.addAttribute("error", "No questions found for this exam.");
            return "error-page"; // Custom error page
        }

        Collections.shuffle(questions);

        session.setAttribute("exam", exam);
        session.setAttribute("examQuestions", questions);
        session.setAttribute("currentIndex", 0);
        session.setAttribute("startTime", LocalDateTime.now());
        session.setAttribute("examDuration", exam.getDuration());
        session.setAttribute("selectedAnswers", new HashMap<Long, String>());

        model.addAttribute("question", questions.get(0));
        model.addAttribute("index", 0);
        model.addAttribute("total", questions.size());
        model.addAttribute("exam", exam);
        model.addAttribute("selectedAnswers", session.getAttribute("selectedAnswers")); // ✅ This line added

        return "exam-page";
    }


    @PostMapping("/navigate")
    public String navigateQuestion(@RequestParam(value = "currentIndex", required = false) Integer currentIndex,
                                   @RequestParam(value = "jumpIndex", required = false) Integer jumpIndex,
                                   @RequestParam(value = "direction", required = false) String direction,
                                   @RequestParam(value = "selectedOption", required = false) String selectedOption,
                                   @RequestParam(value = "remainingTime", required = false) Integer remainingTime,
                                   HttpSession session,
                                   Model model) {

        List<Question> questions = (List<Question>) session.getAttribute("examQuestions");
        if (questions == null || questions.isEmpty()) {
            return "redirect:/student/exams";
        }

        Map<Long, String> selectedAnswers = (Map<Long, String>) session.getAttribute("selectedAnswers");
        if (selectedAnswers == null) selectedAnswers = new HashMap<>();

        if (selectedOption != null && currentIndex != null && currentIndex < questions.size()) {
            selectedAnswers.put(questions.get(currentIndex).getId(), selectedOption);
            session.setAttribute("selectedAnswers", selectedAnswers);
        }

        if (remainingTime != null) {
            session.setAttribute("remainingTime", remainingTime);
        }

        if ("submit".equals(direction)) {
            return "redirect:/student/exams/submit";
        }

        int newIndex = currentIndex != null ? currentIndex : 0;

        if ("next".equals(direction) && currentIndex + 1 < questions.size()) {
            newIndex = currentIndex + 1;
        } else if ("previous".equals(direction) && currentIndex > 0) {
            newIndex = currentIndex - 1;
        } else if (jumpIndex != null) {
            newIndex = jumpIndex;
        }

        Question currentQuestion = questions.get(newIndex);

        model.addAttribute("question", currentQuestion);
        model.addAttribute("index", newIndex);
        model.addAttribute("total", questions.size());
        model.addAttribute("exam", session.getAttribute("exam"));
        model.addAttribute("selectedAnswers", selectedAnswers);
        model.addAttribute("selectedOption", selectedAnswers.get(currentQuestion.getId()));

        return "exam-page";
    }


//---------------------------------------------------------------------------------------------

    @GetMapping("/submit")
    public String submitExam(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        Exam exam = (Exam) session.getAttribute("exam");
        List<Question> questions = (List<Question>) session.getAttribute("examQuestions");
        Map<Long, String> selectedAnswers = (Map<Long, String>) session.getAttribute("selectedAnswers");

        if (user == null || exam == null || questions == null || selectedAnswers == null) {
            return "redirect:/student/exams";
        }

        // ✅ Check if attempt already exists to prevent duplicate submissions
        Optional<ExamAttempt> existingAttempt = examAttemptRepository.findByUserAndExam(user, exam);
        if (existingAttempt.isPresent()) {
            model.addAttribute("attempt", existingAttempt.get());
            return "exam-result"; // or redirect to "/student/exams/results"
        }

        int correctCount = 0;
        List<UserAnswer> userAnswers = new ArrayList<>();

        for (Question question : questions) {
            String selected = selectedAnswers.get(question.getId());

            UserAnswer ua = new UserAnswer();
            ua.setQuestion(question);
            ua.setSelectedOption(selected);

            // ✅ Set isCorrect based on comparison
            boolean correct = selected != null && selected.equals(question.getCorrectAnswer());
            ua.setCorrect(correct);

            if (correct) {
                correctCount++;
            }

            userAnswers.add(ua);
        }


        ExamAttempt attempt = new ExamAttempt();
        attempt.setUser(user);
        attempt.setExam(exam);
        attempt.setStartTime((LocalDateTime) session.getAttribute("startTime"));
        attempt.setEndTime(LocalDateTime.now());
        attempt.setSubmitted(true);
        attempt.setTotalQuestions(questions.size());
        attempt.setCorrectAnswers(correctCount);
        attempt.setWrongAnswers(questions.size() - correctCount);
        attempt.setScore(correctCount); // You can adjust scoring logic here

        for (UserAnswer ua : userAnswers) {
            ua.setAttempt(attempt); // link to attempt
        }

        attempt.setAnswers(userAnswers);
        examAttemptRepository.save(attempt); // cascade saves answers too

        // Clear session
        session.removeAttribute("exam");
        session.removeAttribute("examQuestions");
        session.removeAttribute("currentIndex");
        session.removeAttribute("startTime");
        session.removeAttribute("examDuration");
        session.removeAttribute("selectedAnswers");
        session.removeAttribute("remainingTime");

        model.addAttribute("attempt", attempt);
        return "exam-result";
    }



    @GetMapping("/results")
    public String viewResults(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        List<ExamAttempt> attempts = examAttemptRepository.findByUser(user);
        model.addAttribute("attempts", attempts);
        return "results"; // Make sure this matches the HTML template name
    }
    //---------------------------------------------------------------------



}
