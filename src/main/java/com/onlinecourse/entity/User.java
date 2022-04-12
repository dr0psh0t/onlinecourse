package com.onlinecourse.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
    
    @Column(name = "place")
    private String place;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_image_id")
    private UserImage userImage;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "instructor",
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<Course> createdCourses;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name="student_id"),
            inverseJoinColumns = @JoinColumn(name="course_id"))
    private List<Course> takenCourses;

    @ManyToMany(fetch = EAGER)
    private List<Role> roles = new ArrayList<>();

    public User() {}

    public User(String firstName, String lastName, String email, String username, String password, String place) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.place = place;
    }
    
    public User(User otherUser) {
    	setId(otherUser.getId());
    	setFirstName(otherUser.getFirstName());
    	setLastName(otherUser.getLastName());
    	setEmail(otherUser.getEmail());
    	setUsername(otherUser.getUsername());
    	setPassword(otherUser.getPassword());
    	setPlace(otherUser.getPlace());
    	this.roles = otherUser.getRoles();
    	this.createdCourses = otherUser.getCreatedCourses();
    	this.takenCourses = otherUser.getTakenCourses();
    }
 
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getPlace() { return place; }
    public void setPlace(String place) { this.place = place; }

    public List<Role> getRoles() { return roles; }
    public List<Course> getCreatedCourses() { return createdCourses; }
    public List<Course> getTakenCourses() { return takenCourses; }
    
    public UserImage getUserImage() { return userImage; }
    public void setUserImage(UserImage userImage) { this.userImage = userImage; }

    @Override
    public String toString() {
    	return new StringBuilder()
    			.append(lastName).append(",")
    			.append(firstName).append(",")
    			.append(username).append(",")
    			.append(email).append(",")
    			.append(password).append(",")
    			.append(roles).append(",")
    			.append(place)
    			.toString();
    }
}
