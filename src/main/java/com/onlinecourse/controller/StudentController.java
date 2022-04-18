package com.onlinecourse.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.onlinecourse.entity.Course;
import com.onlinecourse.service.CourseService;
import com.onlinecourse.service.UserService;

@Controller
@RequestMapping("/Student")
public class StudentController {
	
	private final CourseService courseService;
	private final UserService userService;
	
	public StudentController(CourseService courseService, UserService userService) {
		this.courseService = courseService;
		this.userService = userService;
	}
	
	@GetMapping("/StudentProfile")
	public String studentProfile(Model model, Principal principal) {
		
		List<Course> courses = courseService.findAll();
		
		model.addAttribute("courses", courses);
		model.addAttribute("user", userService.findByUsername(principal.getName()));
		
		return "student-profile";
	}
	
	@PostMapping("/Enroll")
	public String enroll(
			@RequestParam(value = "userid") int userid,
			@RequestParam(value = "courseid") int courseid) {

		courseService.enroll(userid, courseid);
		
		return "redirect:/Student/CourseList";
	}
	
	@PostMapping("/Drop")
	public String drop(
			@RequestParam(value = "userid") int userid,
			@RequestParam(value = "courseid") int courseid) {

		courseService.dropStudent(userid, courseid);
		
		return "redirect:/Student/CourseList";
	}
}
