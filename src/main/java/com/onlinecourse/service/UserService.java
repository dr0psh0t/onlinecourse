package com.onlinecourse.service;

import com.onlinecourse.entity.Place;
import com.onlinecourse.entity.Role;
import com.onlinecourse.entity.User;
import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    Place addPlace(Place place);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getUsers();
    void addCreatedCourseToInstructor(String username, String courseTitle);
    void addTakenCourseToStudent(String username, String courseTitle);
    List<User> findAll();
    void deleteById(int userId);
    User findById(int userId);
}
