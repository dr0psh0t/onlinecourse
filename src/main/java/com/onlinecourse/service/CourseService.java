package com.onlinecourse.service;

import java.security.Principal;
import java.util.List;

import com.onlinecourse.entity.Course;

public interface CourseService {
    void saveCourse(Course course, Principal principal);
	List<Course> findAll();
	void enroll(int userid, int courseid);
	void dropStudent(int userid, int courseid);
}
