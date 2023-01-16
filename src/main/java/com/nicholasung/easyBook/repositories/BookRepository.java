package com.nicholasung.easyBook.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.nicholasung.easyBook.models.Book;
import com.nicholasung.easyBook.models.User;

public interface BookRepository extends CrudRepository<Book, Long> {
	List<Book> findAll();
	Book findByIdIs(Long id);
	List<Book> findByGenreIs(String genre);
	Optional<Book> findByTitleIs(String title);
	List<Book> findByAuthorFirstNameIs(String authorFirstName);
	List<Book> findByAuthorLastNameIs(String authorLastName);
	Optional<Book> findByIsbnIs(String isbn);
    List<Book> findAllByUsersWhoRented(User user);
    List<Book> findAllByUsersWhoRentedNotContains(User user);
}
