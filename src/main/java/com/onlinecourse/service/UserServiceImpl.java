package com.onlinecourse.service;

import com.onlinecourse.dao.CourseRepo;
import com.onlinecourse.dao.PlaceRepo;
import com.onlinecourse.dao.RoleRepo;
import com.onlinecourse.dao.UserImageRepo;
import com.onlinecourse.dao.UserRepo;
import com.onlinecourse.entity.Course;
import com.onlinecourse.entity.Place;
import com.onlinecourse.entity.Role;
import com.onlinecourse.entity.User;
import com.onlinecourse.entity.UserImage;
import com.onlinecourse.utils.Log;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final CourseRepo courseRepo;
    private final PlaceRepo placeRepo;
    private final UserImageRepo userImageRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepo userRepo, 
            RoleRepo roleRepo, 
            CourseRepo courseRepo, 
            PlaceRepo placeRepo,
            UserImageRepo userImageRepo,
            PasswordEncoder passwordEncoder) {
    	
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.courseRepo = courseRepo;
        this.placeRepo = placeRepo;
        this.userImageRepo = userImageRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user, List<Role> roles, MultipartFile photo, int userImageId) {
    	Log.info("Saving new user to the database: "+user.getUsername());
    	
    	try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.getRoles().addAll(roles);
          
            //	update image
            UserImage image = new UserImage().id(userImageId)
            		.filename(photo.getOriginalFilename())
            		.filetype(photo.getContentType())
            		.bytes(photo.getBytes());
            
            userImageRepo.save(image);
            
            //	update image which is mapped to user
            user.setUserImage(image);
            
            //	save changes of the user
	        return userRepo.save(user);
	        
		} catch (IOException e) {
			return new User();
		}
    }

    @Override
    public Role saveRole(Role role) {
        //log.info("Saving new role {} to the database", role.getName());
        return roleRepo.save(role);
    }
    
    @Override
    public Place addPlace(Place place) {
        //log.info("Saving new role {} to the database", role.getName());
        return placeRepo.save(place);
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
    	return userRepo.findById(userId).orElseThrow((() -> new RuntimeException("did not find user id - "+userId)));
    }

    @Override
    public User findByUsername(String username) {
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

	@Override
	public String getBase64(User user) {
		try {
			byte[] encodeBase64 = Base64.getEncoder().encode(user.getUserImage().getFile());
			return new String(encodeBase64, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "UnsupportedEncodingException when converting file to base64";
		}
	}

	@Override
	public byte[] getBytes(User user) {
		return user.getUserImage().getFile();
	}

	@Override
	public void getImageAsStream(int id, HttpServletResponse response) {
		response.setContentType("image/png");
    	
    	InputStream inputStream = new ByteArrayInputStream(getBytes(findById(id)));
    	try {
			IOUtils.copy(inputStream, response.getOutputStream());
		} catch (IOException e) {}
	}
}