package com.nicholasung.easyBook.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nicholasung.easyBook.models.Rental;
import com.nicholasung.easyBook.repositories.RentalRepository;

@Service
public class RentalService {
	
	@Autowired
	RentalRepository rentalRepo;
	
	// returns all the rentals
	public List<Rental> allRentals(){
		return rentalRepo.findAll();
	}
	// returns all the rentals for a given book
	public List<Rental> allBookRentals(Long bookId){
		return rentalRepo.findByBookIdIs(bookId);
	}
	// returns all the rentals for a given user
	public List<Rental> allUserRentals(Long userId){
		return rentalRepo.findByUserIdIs(userId);
	}
	// creates a rental
	public Rental createRental(Rental rental) {
		return rentalRepo.save(rental);
	}
	// updates a rental
	public void deleteRental(Rental rental) {
		rentalRepo.delete(rental);
	}
    // retrieves a rental
    public Rental findById(Long id) {
    	return rentalRepo.findById(id).orElse(null);
    }
    // deletes a rental
    public void deleteRental(Long id) {
    	rentalRepo.deleteById(id);    		
    }
}
