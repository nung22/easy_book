package com.nicholasung.easyBook.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nicholasung.easyBook.models.Book;
import com.nicholasung.easyBook.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	List<User> findAll();
	User findByUsername(String username);
//    Optional<User> findByEmail(String email);
    User findByIdIs(Long id);
    List<User> findAllByBooksRented(Book book);
    List<User> findAllByBooksRentedNotContains(Book book);
}
