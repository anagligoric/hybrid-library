package com.hybridlibrary.services.serviceImpl;

import com.hybridlibrary.models.BookRental;
import com.hybridlibrary.repositories.BookRentalRepository;
import com.hybridlibrary.services.BookRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BookRentalServiceImpl implements BookRentalService {
    @Autowired
    private BookRentalRepository bookRentalRepository;

    @Override
    public Collection<BookRental> findAll() {
        return bookRentalRepository.findAll();
    }

    @Override
    public BookRental getOne(Long id) {
        return bookRentalRepository.getOne(id);
    }

    @Override
    public BookRental update(BookRental book) {
        return null;
    }

    @Override
    public BookRental create(BookRental book) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public boolean existById(Long id) {
        return false;
    }
    /*public HashMap<String, Integer> mostRentedBook(){
        //return bookRentalRepository.mostRentedBooks();
    }*/


}
