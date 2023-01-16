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
    
    // To comply with Spring Security framework, the variable holding email will be referred to as 'username'
    @NotEmpty(message="Email is required.")
    @Email(message="Please enter a valid email.")
    private String username;
    
    @Size(min=10, max=10, message="Phone number must be 10 digits.")
    private String phoneNumber;
    
    @Size(min=8, max=128, message="Password must be at least 8 characters.")
    private String password;
    
    @Transient
    @Size(min=8, max=128, message="Confirm PW must match password.")
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
    
    // Implement one to many relationship with books
    @OneToMany(mappedBy="renter", fetch=FetchType.LAZY)
    private List<Book> books;
    
    // Implement many to many relationship with books called rentals
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
    		name = "rentals",
    		joinColumns = @JoinColumn(name="user_id"),
    		inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> booksRented;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;
    
    // Constructors
    public User() {}
    public User(String firstName, String lastName, String username, String phoneNumber, String password) {
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.username = username;
    	this.phoneNumber = phoneNumber;
    	this.password = password;
    }
    
    // Getters & Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setEmail(String username) {
		this.username = username;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirm() {
		return confirm;
	}
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	public List<Book> getBooksRented() {
		return booksRented;
	}
	public void setBooksRented(List<Book> booksRented) {
		this.booksRented = booksRented;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
    
}