package com.onlinecourse.service;

import com.onlinecourse.entity.Place;
import com.onlinecourse.entity.Role;
import com.onlinecourse.entity.User;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    User saveUser(User user, List<Role> roles, MultipartFile profilepicture);
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
    String getBase64(User user);
    byte[] getBytes(User user);
    void getImageAsStream(int id, HttpServletResponse response);
}
