package com.onlinecourse.service;

import java.security.Principal;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.onlinecourse.dao.CourseRepo;
import com.onlinecourse.entity.Course;
import com.onlinecourse.entity.User;
import com.onlinecourse.utils.Log;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {
	
	private final CourseRepo courseRepo;
	private final UserService userService;
	
	public CourseServiceImpl(CourseRepo courseRepo, UserService userService) {
		this.courseRepo = courseRepo;
		this.userService = userService;
	}

	@Override
	public List<Course> findAll() {
		Log.info("fetching all courses");
		return courseRepo.findAll();
	}

	@Override
	public void saveCourse(Course course, Principal principal) {
		course.setInstructor(userService.findByUsername(principal.getName()));
		
		Log.info("saving course: "+course);
		courseRepo.save(course);
	}

	@Override
	public void enroll(int userid, int courseid) {
		
		Course course = courseRepo.findById(courseid).orElse(new Course());
		User student = userService.findById(userid);
		
		course.addStudent(student);
		Log.info("Enrolling student "+student.getUsername()+" to course "+course.getTitle());
	}
	
	@Override
	public void dropStudent(int userid, int courseid) {
		
		Course course = courseRepo.findById(courseid).orElse(new Course());
		User student = userService.findById(userid);
		
		course.dropStudent(student);
		
		Log.info("Dropping student "+student.getUsername()+" from course "+course.getTitle());
	}
}
