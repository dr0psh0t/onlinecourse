package com.onlinecourse.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.onlinecourse.entity.Course;
import com.onlinecourse.service.CourseService;
import com.onlinecourse.service.UserService;

@Controller
@RequestMapping("/Instructor")
public class InstructorController {
	
	private final CourseService courseService;
	private final UserService userService;
	
	public InstructorController(CourseService courseService, UserService userService) {
		this.courseService = courseService;
		this.userService = userService;
	}
	
	@GetMapping("/InstructorProfile")
	public String studentProfile(Model model, Principal principal) {
		
		List<Course> courses = courseService.findAll();
		
		model.addAttribute("courses", courses);
		model.addAttribute("user", userService.findByUsername(principal.getName()));
		
		return "instructor-profile";
	}
	
	@GetMapping("/AddCourseForm")
    public String addCourseForm(Model model) {
        
    	model.addAttribute("course", new Course());
    	
    	return "course-form";
    }
	
	@PostMapping("/SaveCourse")
    public String saveUser(@ModelAttribute("course") Course course, Principal principal) {
		
		courseService.saveCourse(course, principal);
        
    	return "redirect:/Instructor/InstructorProfile";
    }
	
	@GetMapping("/DeleteCourse/{id}")
	public String deleteCourse(@PathVariable(value = "id") int id) {
		
		courseService.deleteCourse(id);
		
		return "redirect:/Instructor/InstructorProfile";
	}
	
	@GetMapping("/UpdateCourse")
	public String updateCourse(@RequestParam(value = "id") int id, Model model) {
		
		model.addAttribute("course", courseService.findById(id));
		
		return "course-form";
	}
}
