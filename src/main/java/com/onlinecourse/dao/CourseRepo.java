package com.onlinecourse.dao;

import com.onlinecourse.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepo extends JpaRepository<Course, Integer> {
    Course findByTitle(String title);
}
