package com.nicholasung.easyBook.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nicholasung.easyBook.models.Rental;

public interface RentalRepository extends CrudRepository<Rental, Long> {
	List<Rental> findAll();
	List<Rental> findByBookIdIs(Long id);
	List<Rental> findByUserIdIs(Long id);
}