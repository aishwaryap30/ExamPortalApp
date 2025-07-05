package com.myproject.OnlineExamination.controller;
import com.myproject.OnlineExamination.model.*;
import com.myproject.OnlineExamination.repository.ExamAttemptRepository;
import com.myproject.OnlineExamination.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService; // Make sure to create the UserService class for handling business logic


    @GetMapping("/")
    public String home() {
        return "dashboard"; // dashboard.html inside src/main/resources/templates/
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());  // Creating a new User object to bind the form fields
        return "register";  // This corresponds to the Thymeleaf template (register.html)
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        // Save the user to the database using the UserService
        System.out.println(user);
        userService.saveUser(user);
        //return "redirect:/login";  // Redirect to login page after successful registration
        return "user-dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; // Thymeleaf template name (dashboard.html)
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";  // Thymeleaf template name login.html
    }


    @PostMapping("/login")
    public String loginUser(@RequestParam("userName") String userName,
                            @RequestParam("password") String password,
                            Model model,
                            HttpSession session) {
        // Authenticate user by username or email
        User user = userService.authenticateUserByUsernameOrEmail(userName, password);

        if (user != null) {
            // Save user in session
            session.setAttribute("user", user);

            // Redirect based on role
            if ("admin".equalsIgnoreCase(user.getRole())) {
                return "redirect:/admin-dashboard";   // admin page
            } else {
                return "redirect:/user-dashboard";    // user page
            }
        } else {
            // Authentication failed
            model.addAttribute("error", "Invalid username/email or password");
            return "login";
        }
    }



    @GetMapping("/admin-dashboard")
    public String showAdminDashboard(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        // Check role instead of username
        if (user != null && "admin".equalsIgnoreCase(user.getRole())) {
            model.addAttribute("user", user);
            return "admin-dashboard";  // Show the admin dashboard
        } else {
            // If not admin, redirect to login
            return "redirect:/login";
        }
    }


    @GetMapping("/user-dashboard")
    public String showDashboard(Model model, HttpSession session) {
        // Retrieve user from session
        User user = (User) session.getAttribute("user");

        if (user != null) {
            model.addAttribute("user", user); // Add user info to the model to display on the page
            return "user-dashboard"; //
        } else {
            // If no user found in the session, redirect to login
            return "redirect:/login";
        }
    }

    @GetMapping("/manage-user")
    public String manageUsers() {
        return "manage-user";  // Corresponds to manage-user.html
    }


    @GetMapping("/manage-exam")
    public String manageExams() {
        return "manage-exam";  // Corresponds to manage-exam.html
    }
//----------------------------------------------------------------------------------------------


}
