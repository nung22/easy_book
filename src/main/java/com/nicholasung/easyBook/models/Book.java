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
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="books")
public class Book {
	// Member Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message="Title must be provided.")
    @Max(value = 45, message="Title must be 45 characters or less.")
    private String title;
    
    @NotNull(message="Price must be provided.")
    private Double price;
    
    @NotEmpty(message="Genre must be provided.")
    @Max(value = 45, message="Genre must be 45 characters or less.")
    private String genre;

    @Min(value = 3, message="Description must be at least 3 characters.")
    private String description;
    
    @Size(min=3, max=30, message="Author first name must be between 3 and 30 characters.")
    private String authorFirstName;
    
    @Size(min=3, max=30, message="Author last name must be between 3 and 30 characters.")
    private String authorLastName;
    
    @Column(nullable = true, length = 64)
    private String cover;
    
    @Size(min=13, max=13, message="ISBN must be 13 digits.")
    private String isbn;
    
    @NotNull(message="Number of available copies must be provided.")
    private Integer available;
    
    private Integer rented;
    
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
    
    // Implement many to one relationship with users
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User renter;
    
    // Implement many to many relationship with user called rentals
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
    		name = "rentals",
    		joinColumns = @JoinColumn(name="book_id"),
    		inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> usersWhoRented;
    
    // Constructors
    public Book() {
    }  
	public Book(String title, Double price, String genre, Integer available, Integer rented,
			String description, String authorFirstName, String authorLastName, String isbn, String cover) {
		this.title = title;
		this.price = price;
		this.isbn = isbn;
		this.genre = genre;
		this.available = available;
		this.rented = rented;
		this.description = description;
		this.authorFirstName = authorFirstName;
		this.authorLastName = authorLastName;
		this.cover = cover;
	}
	
	// Getters & Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAuthorFirstName() {
		return authorFirstName;
	}
	public void setAuthorFirstName(String authorFirstName) {
		this.authorFirstName = authorFirstName;
	}
	public String getAuthorLastName() {
		return authorLastName;
	}
	public void setAuthorLastName(String authorLastName) {
		this.authorLastName = authorLastName;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public Integer getAvailable() {
		return available;
	}
	public void setAvailable(Integer available) {
		this.available = available;
	}
	public Integer getRented() {
		return rented;
	}
	public void setRented(Integer rented) {
		this.rented = rented;
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
	public User getRenter() {
		return renter;
	}
	public void setRenter(User renter) {
		this.renter = renter;
	}
	public List<User> getUsersWhoRented() {
		return usersWhoRented;
	}
	public void setUsersWhoRented(List<User> usersWhoRented) {
		this.usersWhoRented = usersWhoRented;
	}
	
}
