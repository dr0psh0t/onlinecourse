package com.onlinecourse.service;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.onlinecourse.dao.CourseRepo;
import com.onlinecourse.entity.Course;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {
	
	private final CourseRepo courseRepo;
	
	public CourseServiceImpl(CourseRepo courseRepo) {
		this.courseRepo = courseRepo;
	}

	@Override
	public List<Course> findAll() {
		return courseRepo.findAll();
	}
}
