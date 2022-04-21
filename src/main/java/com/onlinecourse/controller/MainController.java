package com.onlinecourse.controller;

import com.onlinecourse.entity.Role;
import com.onlinecourse.entity.Roles;
import com.onlinecourse.entity.User;
import com.onlinecourse.service.PlaceService;
import com.onlinecourse.service.RoleService;
import com.onlinecourse.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private UserService userService;
    private RoleService roleService;
    private PlaceService placeService;

    public MainController(
    		UserService userService, 
    		RoleService roleService, 
    		PlaceService placeService) {
    	
        this.userService = userService;
        this.roleService = roleService;
        this.placeService = placeService;
    }
    
    @GetMapping("/Index")
    public String index() {
    	return "index";
    }
	
	@GetMapping("/Home")
    public String adminHome(Model model, Principal principal) {
		
		model.addAttribute("username", principal.getName());
		
        return "home";
    }
    
    @GetMapping("/AccessDenied")
    public String accessDenied() {
        return "access-denied";
    }
    
    @GetMapping("/Update/UpdateUserForm")
    public String updateUserForm(@RequestParam("userId") int userId, Model model) {
        
    	User user = userService.findById(userId);
    	
    	//	create a new user so that password will not be empty when going to update user form
    	User user2 = new User(user);
    	user2.setPassword("");
    	
    	model.addAttribute("user", user2);
    	model.addAttribute("userImageId", user.getUserImage().getId());
    	model.addAttribute("rolesList", roleService.findAll());
    	model.addAttribute("placesList", placeService.findAll());
    	
    	return "user-form";
    }
    
    @PostMapping("/Save/SaveUser")
    public String saveUser(
    		@ModelAttribute("user") User user,
    		@RequestParam(value = "roles") ArrayList<Integer> roles,
    		@RequestParam(value = "profilepicture") MultipartFile profilepicture,
    		@RequestParam(value = "userImageId") int userImageId) {
    	
    	final List<Role> rolesList = roles.stream().map(id -> roleService.findOne(id)).collect(Collectors.toList());
    	
    	userService.saveUser(user, rolesList, profilepicture, userImageId);
        
    	//return "redirect:/ListUsers";
    	//return "redirect:/Home";
    	return "redirect:/Logout";
    }
    
    @GetMapping("/UserProfile/{username}")
    public String profile(@PathVariable(value = "username") String username) {
    	
    	User user = userService.findByUsername(username);
    	
    	Role role = user.getRoles().get(0);
    	
    	if (role.getName().equals(Roles.INSTRUCTOR.name())) {
    		return "redirect:/Instructor/InstructorProfile";
		} else if (role.getName().equals(Roles.STUDENT.name())) {
			return "redirect:/Student/StudentProfile";
		} else if (role.getName().equals(Roles.ADMIN.name()) || role.getName().equals(Roles.MODERATOR.name())) {
			return "redirect:/Admin/ListUsers";
		} {
			return "redirect:/AccessDenied";
		}
    }
    
    @GetMapping("/Deletion/Delete")
    public String delete(@RequestParam("userId") int userId) {
        
    	userService.deleteById(userId);
        
    	return "redirect:/ListUsers";
    }
}