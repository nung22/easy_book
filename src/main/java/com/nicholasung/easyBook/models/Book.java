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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="books")
public class Book {
	// Member Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Size(min = 1, max = 45, message="Title must be provided.")
    private String title;
    
    @NotNull(message="Price must be provided.")
    private Double price;
    
    @NotEmpty(message="Units must be provided.")
    private String units;
    
    @NotNull(message="Duration must be provided.")
    private Integer duration;
    
    @Size(min = 3, message="Description must be at least 3 characters.")
    private String description;
    
    @Size(min = 3, max = 255, message="Private notes must be at least 3 characters.")
    private String privateNotes;
    
    @NotNull(message="Date & Time must be provided.")
    @Past(message="Date & Time cannot be in the future.")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date dateTime;
    
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
    private User poster;
    // Implement many to many relationship with user called comments
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
    		name = "comments",
    		joinColumns = @JoinColumn(name="book_id"),
    		inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> usersWhoCommented;
    // Implement many to many relationship with user called cheers
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
    		name = "cheers",
    		joinColumns = @JoinColumn(name="book_id"),
    		inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> usersWhoCheered;
    
    // Constructors
    public Book() {
    }  
	public Book(String title, Double price, Double distance, String units, Integer duration,
			String privacy, String description, String privateNotes, Date dateTime) {
		this.title = title;
		this.price = price;
		this.distance = distance;
		this.units = units;
		this.duration = duration;
		this.privacy = privacy;
		this.description = description;
		this.privateNotes = privateNotes;
		this.dateTime = dateTime;
	}
}
