package com.hybridlibrary.services.serviceimpl;

import com.hybridlibrary.dtos.BookRentalDto;
import com.hybridlibrary.models.BookCopy;
import com.hybridlibrary.repositories.BookCopyRepository;
import com.hybridlibrary.repositories.BookRentalRepository;
import com.hybridlibrary.services.BookRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        BookCopy bookCopy = bookCopyRepository.getOne(id);
        return bookRentalRepository.countByBookCopy(bookCopy);
    }
}
