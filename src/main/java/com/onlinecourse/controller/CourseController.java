package com.onlinecourse.controller;

import com.onlinecourse.entity.Role;
import com.onlinecourse.entity.Roles;
import com.onlinecourse.entity.User;
import com.onlinecourse.service.RoleService;
import com.onlinecourse.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CourseController {

    private UserService userService;
    private RoleService roleService;

    public CourseController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
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

    	List<Role> rolesList = roleService.findAll();
    	
    	user.getRoles().addAll(rolesList);
        
    	model.addAttribute("user", user);
    	model.addAttribute("rolesList", rolesList);
    	
    	return "user-form";
    }

    @PostMapping("/SaveUser")
    public String saveUser(
    		@ModelAttribute("user") User user,
    		@RequestParam(value = "roles") ArrayList<Integer> roles) {
    	
    	//	transform ArrayList<Integer> to List<Role> using map function
    	final List<Role> rolesList = roles.stream().map(id -> roleService.findOne(id)).collect(Collectors.toList());
    	
    	user.getRoles().addAll(rolesList);
    	
    	userService.saveUser(user);
        
    	return "redirect:/ListUsers";
    }

    @GetMapping("UpdateUserForm")
    public String updateUserForm(@RequestParam("userId") int userId, Model model) {
        
    	User user = userService.findById(userId);
        
    	model.addAttribute("user", user);
    	model.addAttribute("rolesList", roleService.findAll());
    	
    	return "user-form";
    }

    @GetMapping("/Delete")
    public String delete(@RequestParam("userId") int userId) {
        
    	userService.deleteById(userId);
        
    	return "redirect:/ListUsers";
    }
}