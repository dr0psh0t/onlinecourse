package com.onlinecourse.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.onlinecourse.entity.Course;
import com.onlinecourse.entity.Place;
import com.onlinecourse.entity.Role;
import com.onlinecourse.entity.User;
import com.onlinecourse.service.CourseService;
import com.onlinecourse.service.PlaceService;
import com.onlinecourse.service.RoleService;
import com.onlinecourse.service.UserService;

@Controller
@RequestMapping("/Admin")
public class AdminModController {
	
	private final UserService userService;
    private final CourseService courseService;
    private final PlaceService placeService;
    private final RoleService roleService;

	public AdminModController(
			UserService userService, 
    		RoleService roleService, 
    		PlaceService placeService,
    		CourseService courseService) {
		
		 this.userService = userService;
		 this.courseService = courseService;
		 this.roleService = roleService;
		 this.placeService = placeService;
	}
	
	@GetMapping("/ListUsers")
    public String listUsers(Model model) {
        
    	List<User> users = userService.findAll();
        
    	model.addAttribute("users", users);
        
    	return "list-users";
    }
    
    @GetMapping("/CourseList")
	public String courseList(Model model, Principal principal) {
		
		List<Course> courses = courseService.findAll();
		
		model.addAttribute("courses", courses);
		
		return "course-list";
	}
    
    @GetMapping("/ViewImage/{id}")
    public String viewImage(@PathVariable(value = "id") int id, Model model) {

    	model.addAttribute("userImage", userService.getBase64(userService.findById(id)));
    	
		return "view-image";
    }
    
    @GetMapping("/GetImage/{id}")
    public void getImage(@PathVariable(value = "id") int id, HttpServletResponse response) {

    	userService.getImageAsStream(id, response);
    }

    @GetMapping("/AddUserForm")
    public String addUserForm(Model model) {
        
    	User user = new User();

    	List<Role> rolesList = roleService.findAll();
    	List<Place> placesList = placeService.findAll();
    	
    	user.getRoles().addAll(rolesList);
        
    	model.addAttribute("user", user);
    	model.addAttribute("userImageId", 0);
    	model.addAttribute("rolesList", rolesList);
    	model.addAttribute("placesList", placesList);
    	
    	return "user-form";
    }
}
