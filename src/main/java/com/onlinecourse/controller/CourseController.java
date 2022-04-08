package com.onlinecourse.controller;

import com.onlinecourse.entity.User;
import com.onlinecourse.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CourseController {

    private UserService userService;

    public CourseController(UserService userService) {
        this.userService = userService;
    }
	
	@GetMapping("/AdminHome")
    public String adminHome() {
        return "admin-home";
    }

    @GetMapping("/Login")
    public String showMyLoginPage() {
        return "login";
    }
    
    @GetMapping("/AccessDenied")
    public String accessDenied() {
        return "access-denied";
    }

    @GetMapping("/ListUsers")
    public String listUsers(Model model) {
        
    	List<User> users = userService.findAll();
        
    	model.addAttribute("users", users);
        
    	return "list-users";
    }

    @GetMapping("/AddUserForm")
    public String addUserForm(Model model) {
        
    	User user = new User();
        
    	model.addAttribute("user", user);
        
    	return "user-form";
    }

    @PostMapping("/SaveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        
    	userService.saveUser(user);
        
    	return "redirect:/ListUsers";
    }

    @GetMapping("UpdateUserForm")
    public String updateUserForm(@RequestParam("userId") int userId, Model model) {
        
    	User user = userService.findById(userId);
        
    	model.addAttribute("user", user);
    	
    	return "user-form";
    }

    @GetMapping("/Delete")
    public String delete(@RequestParam("userId") int userId) {
        
    	userService.deleteById(userId);
        
    	return "redirect:/ListUsers";
    }
}