package com.onlinecourse.service;

import java.util.List;

import com.onlinecourse.entity.Course;

public interface CourseService {
    //Course saveCourse(String courseTitle);
	List<Course> findAll();
}
