package com.onlinecourse.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.onlinecourse.entity.Course;
import com.onlinecourse.entity.Place;
import com.onlinecourse.entity.Role;
import com.onlinecourse.entity.User;
import com.onlinecourse.service.CourseService;
import com.onlinecourse.service.UserService;

@Controller
@RequestMapping("/Instructor")
public class CourseListController {
	
	private final CourseService courseService;
	private final UserService userService;
	
	public CourseListController(CourseService courseService, UserService userService) {
		this.courseService = courseService;
		this.userService = userService;
	}
	
	@GetMapping("/CourseList")
	public String courseList() {
		
		
		List<Course> courses = courseService.findAll();
		
		//System.out.println(courses);
		
		return "course-list";
	}
	
	@GetMapping("/AddCourseForm")
    public String addCourseForm(Model model) {
        
    	model.addAttribute("course", new Course());
    	
    	return "course-form";
    }
	
	@PostMapping("/SaveCourse")
    public String saveUser(@ModelAttribute("course") Course course, Principal principal) {
		
		course.setInstructor(userService.findByUsername(principal.getName()));
		
		System.out.println(course);
    	
    	/*
    	final List<Role> rolesList = roles.stream().map(id -> roleService.findOne(id)).collect(Collectors.toList());
    	
    	userService.saveUser(user, rolesList, profilepicture, userImageId);
    	*/
        
    	return "redirect:/Instructor/CourseList";
    }
	
	@GetMapping("/username") 
	public void username(Principal principal) {
		System.out.println(principal.getName());
		//return principal.getName();
	} 
}
