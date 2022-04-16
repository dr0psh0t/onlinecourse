package com.onlinecourse.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.onlinecourse.entity.Course;
import com.onlinecourse.service.CourseService;

@Controller
@RequestMapping("/Instructor")
public class CourseListController {
	
	private final CourseService courseService;
	
	public CourseListController(CourseService courseService) {
		this.courseService = courseService;
	}
	
	@GetMapping("/AddCourseForm")
    public String addCourseForm(Model model) {
        
    	model.addAttribute("course", new Course());
    	
    	return "course-form";
    }
	
	@PostMapping("/SaveCourse")
    public String saveUser(@ModelAttribute("course") Course course, Principal principal) {
		
		courseService.saveCourse(course, principal);
        
    	return "redirect:/Instructor/CourseList";
    }
	
	@GetMapping("/username") public void username(Principal principal) { System.out.println(principal.getName()); } 
}
