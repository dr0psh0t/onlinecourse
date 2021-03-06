package com.onlinecourse.service;

import java.security.Principal;
import java.util.List;

import com.onlinecourse.entity.Course;

public interface CourseService {
    void saveCourse(Course course);
	List<Course> findAll();
	void enroll(int userid, int courseid);
	void dropStudent(int userid, int courseid);
	void deleteCourse(int id);
	Course findById(int id);
}
