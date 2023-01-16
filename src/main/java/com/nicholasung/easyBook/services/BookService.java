package com.nicholasung.easyBook.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nicholasung.easyBook.models.Book;
import com.nicholasung.easyBook.models.User;
import com.nicholasung.easyBook.repositories.BookRepository;
import com.nicholasung.easyBook.repositories.UserRepository;

@Service
public class BookService {
	
	@Autowired
	BookRepository bookRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	UserService userServ;
	
	// returns all the books
	public List<Book> allBooks(){
		return bookRepo.findAll();
	}
	
	// updates a book
	public Book updateBook(Book book) {
		return bookRepo.save(book);
	}
//	// updates a book for API
//	public Book updateBook(Long id, String title, String sport, Double distance, String units, Integer duration,
//			String privacy, String description, String privateNotes, Date dateTime) {
//		Book updatedBook = this.findById(id);
//		updatedBook.setTitle(title);
//		updatedBook.set(sport);
//		updatedBook.setDistance(distance);
//		updatedBook.setUnits(units);
//		updatedBook.setDuration(duration);
//		updatedBook.setPrivacy(privacy);
//		updatedBook.setDescription(description);
//		updatedBook.setPrivateNotes(privateNotes);
//		updatedBook.setDateTime(dateTime);
//		return bookRepo.save(updatedBook);
//	}

    // creates a book
	public Book createBook(Book book) {
		return bookRepo.save(book);
	}
	// deletes a book
	public void deleteBook(Book book) {
		bookRepo.delete(book);
	}
	// retrieves a book by its id
	public Book findById(Long id) {
		return bookRepo.findByIdIs(id);
	}
	// retrieves all the books of a genre
	public List<Book> findByGenre(String genre) {
		return bookRepo.findByGenreIs(genre);
	}
	// retrieves a book by title
	public Book findByTitle(String title) {
		return bookRepo.findByTitleIs(title).orElse(null);
	}
	// retrieves all the books written by an author with a given first name
	public List<Book> findByAuthorFirstName(String authorFirstName) {
		return bookRepo.findByAuthorFirstNameIs(authorFirstName);
	}
	// retrieves all the books written by an author with a given last name
	public List<Book> findByAuthorLastName(String authorLastName) {
		return bookRepo.findByAuthorLastNameIs(authorLastName);
	}
	public Book findByIsbn(String isbn) {
		return bookRepo.findByIsbnIs(isbn).orElse(null);
	}
    public List<Book> findRenters(User user) {
    	return bookRepo.findAllByUsersWhoRented(user);
    }
    public List<Book> findNonRenters(User user) {
    	return bookRepo.findAllByUsersWhoRentedNotContains(user);
    }
}

