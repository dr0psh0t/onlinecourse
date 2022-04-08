package com.onlinecourse.service;

import com.onlinecourse.dao.CourseRepo;
import com.onlinecourse.dao.RoleRepo;
import com.onlinecourse.dao.UserRepo;
import com.onlinecourse.entity.Course;
import com.onlinecourse.entity.Role;
import com.onlinecourse.entity.User;
import com.onlinecourse.utils.Log;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final CourseRepo courseRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepo userRepo, RoleRepo roleRepo, CourseRepo courseRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.courseRepo = courseRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        //log.info("Saving new user {} to the database", user.getName());
        Log.info("Saving new user to the database: "+user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        //log.info("Saving new role {} to the database", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        //log.info("Adding role {} to user {}", roleName, username);
        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public void addCreatedCourseToInstructor(String username, String courseTitle) {
        User instructor = userRepo.findByUsername(username);

        Course course = courseRepo.findByTitle(courseTitle);
        if (course == null) {
            course = courseRepo.save(new Course(courseTitle, instructor));
        }

        instructor.getCreatedCourses().add(course);
    }

    @Override
    public void addTakenCourseToStudent(String username, String courseTitle) {
        User student = userRepo.findByUsername(username);

        Course course = courseRepo.findByTitle(courseTitle);
        if (course == null) {
            course = courseRepo.save(new Course(courseTitle, student));
        }

        course.addStudent(student);
        //student.getTakenCourses().add(course);
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public void deleteById(int userId) {
        userRepo.deleteById(userId);
    }

    @Override
    public User findById(int userId) {
        Optional<User> user = userRepo.findById(userId);
        
        if (user.isPresent()) {
			return user.get();
		} else {
			throw new RuntimeException("did not find user id - "+userId);
		}
    }

    @Override
    public User getUser(String username) {
        //log.info("Fetching user {}", username);
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        //log.info("Fetching all users");
        return userRepo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        Objects.requireNonNull(user, "User not found in the database");

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), authorities);
    }
}
