package com.onlinecourse.controller;

import com.onlinecourse.entity.Place;
import com.onlinecourse.entity.Role;
import com.onlinecourse.entity.User;
import com.onlinecourse.service.PlaceService;
import com.onlinecourse.service.RoleService;
import com.onlinecourse.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CourseController {

    private UserService userService;
    private RoleService roleService;
    private PlaceService placeService;

    public CourseController(UserService userService, RoleService roleService, PlaceService placeService) {
        this.userService = userService;
        this.roleService = roleService;
        this.placeService = placeService;
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
    
    @GetMapping("/ViewImage/{id}")
    public String viewImage(@PathVariable(value = "id") int id, Model model) {

    	model.addAttribute("userImage", userService.getBase64(userService.findById(id)));
    	
		return "view-image";
    }

    @GetMapping("/AddUserForm")
    public String addUserForm(Model model) {
        
    	User user = new User();

    	List<Role> rolesList = roleService.findAll();
    	List<Place> placesList = placeService.findAll();
    	
    	user.getRoles().addAll(rolesList);
        
    	model.addAttribute("user", user);
    	model.addAttribute("rolesList", rolesList);
    	model.addAttribute("placesList", placesList);
    	
    	return "user-form";
    }

    @PostMapping("/SaveUser")
    public String saveUser(
    		@ModelAttribute("user") User user,
    		@RequestParam(value = "roles") ArrayList<Integer> roles,
    		@RequestParam(value = "profilepicture") MultipartFile profilepicture) {
    	
    	//	transform ArrayList<Integer> to List<Role> using map function
    	final List<Role> rolesList = roles.stream().map(id -> roleService.findOne(id)).collect(Collectors.toList());
    	
    	userService.saveUser(user, rolesList, profilepicture);
        
    	return "redirect:/ListUsers";
    }

    @GetMapping("UpdateUserForm")
    public String updateUserForm(@RequestParam("userId") int userId, Model model) {
        
    	User user = userService.findById(userId);
    	
    	//	create a new user so that password will not be empty when going to update user form
    	User user2 = new User(user);
    	user2.setPassword("");
    	
    	model.addAttribute("user", user2);
    	model.addAttribute("rolesList", roleService.findAll());
    	model.addAttribute("placesList", placeService.findAll());
    	
    	return "user-form";
    }

    @GetMapping("/Delete")
    public String delete(@RequestParam("userId") int userId) {
        
    	userService.deleteById(userId);
        
    	return "redirect:/ListUsers";
    }
}