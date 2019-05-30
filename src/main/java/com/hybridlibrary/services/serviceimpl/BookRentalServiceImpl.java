package com.hybridlibrary.services.serviceimpl;

import com.hybridlibrary.dtos.BookRentalDto;
import com.hybridlibrary.exception.NotFoundException;
import com.hybridlibrary.models.BookCopy;
import com.hybridlibrary.repositories.BookCopyRepository;
import com.hybridlibrary.repositories.BookRentCount;
import com.hybridlibrary.repositories.BookRentalRepository;
import com.hybridlibrary.services.BookRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class BookRentalServiceImpl implements BookRentalService {
    @Autowired
    private BookRentalRepository bookRentalRepository;

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Override
    public List<BookRentalDto> findAll() {
        return null;
    }

    @Override
    public BookRentalDto getOne(Long id) {
        return null;
    }

    @Override
    public BookRentalDto update(BookRentalDto book) {
        return null;
    }

    @Override
    public BookRentalDto create(BookRentalDto bookRental) {
        return null;
    }

    @Override
    public BookRentalDto delete(Long id) {
        return null;
    }

    @Override
    public boolean existById(Long id) {
        return bookRentalRepository.existsById(id);
    }

    @Override
    public List<BookRentalDto> getByBookCopy(Long id) {
        return null;
    }

    @Override
    public Integer countByBookCopy(Long id) {
        if(bookCopyRepository.existsById(id)){
            BookCopy bookCopy = bookCopyRepository.getOne(id);
            return bookRentalRepository.countByBookCopy(bookCopy);
        }else{
            throw new NotFoundException("Book copy with id " + id + " not found.");
        }
    }

    public List<BookRentCount> mostRented(Long number){
        List<BookRentCount> mostRentedList = bookRentalRepository.mostRented(number);
        if(CollectionUtils.isEmpty(mostRentedList)){
            throw new NotFoundException("There is no any rented books.");
        }else{
            return mostRentedList;
        }
    }
}
