package com.nicholasung.easyBook.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="users")
public class User {
    // Member Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Size(min=3, max=30, message="First name must be between 3 and 30 characters.")
    private String firstName;
    
    @Size(min=3, max=30, message="Last name must be between 3 and 30 characters.")
    private String lastName;
    
    @NotEmpty(message="Email is required.")
    @Email(message="Please enter a valid email.")
    private String email;
    
    @Size(min=8, max=128, message="Password must be between 8 and 128 characters.")
    private String password;
    
    @Transient
    @Size(min=8, max=128, message="Confirm PW must be between 8 and 128 characters.")
    private String confirm;
    
    // createdAt & updatedAt
    @Column(updatable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;
	@PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
    
    // Implement many to many relationship with other users to track follow status
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
    		name = "users_followers",
    		joinColumns = @JoinColumn(name="user_id"),
    		inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private List<User> usersFollowed;
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
    		name = "users_followers",
    		joinColumns = @JoinColumn(name="follower_id"),
    		inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> followers;
    
    // Implement one to many relationship with exercises
    @OneToMany(mappedBy="poster", fetch=FetchType.LAZY)
    private List<Exercise> posts;
    
    // Implement one to many relationship with foods
    @OneToMany(mappedBy="user", fetch=FetchType.LAZY)
    private List<Food> foods;
    
    // Implement many to many relationship with exercises called comments
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
    		name = "comments",
    		joinColumns = @JoinColumn(name="user_id"),
    		inverseJoinColumns = @JoinColumn(name = "exercise_id"))
    private List<Exercise> exercisesCommented;
    
    // Implement many to many relationship with exercises called cheers
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
    		name = "cheers",
    		joinColumns = @JoinColumn(name="user_id"),
    		inverseJoinColumns = @JoinColumn(name = "exercise_id"))
    private List<Exercise> exercisesCheered;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;
    
    // Constructors
    public User() {}
    public User(String firstName, String lastName, String email, String password) {
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.email = email;
    	this.password = password;
    }
}