package com.hybridlibrary.services.serviceimpl;

import com.hybridlibrary.dtos.BookRentalDto;
import com.hybridlibrary.exceptions.NotFoundException;
import com.hybridlibrary.models.BookCopy;
import com.hybridlibrary.models.BookRental;
import com.hybridlibrary.repositories.BookCopyRepository;
import com.hybridlibrary.repositories.BookRentCount;
import com.hybridlibrary.repositories.BookRentalRepository;
import com.hybridlibrary.services.BookRentalService;
import com.hybridlibrary.utils.ApplicationConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BookRentalServiceImpl implements BookRentalService {

    private BookRentalRepository bookRentalRepository;

    private BookCopyRepository bookCopyRepository;

    private ConversionService conversionService;

    @Autowired
    public BookRentalServiceImpl(BookRentalRepository bookRentalRepository, ConversionService conversionService, BookCopyRepository bookCopyRepository) {
        this.conversionService = conversionService;
        this.bookRentalRepository = bookRentalRepository;
        this.bookCopyRepository = bookCopyRepository;
    }

    @Override
    public List<BookRentalDto> findAll() {
        List<BookRentalDto> bookRentalDtoList = new ArrayList<>();
        for (BookRental bookRental :
                bookRentalRepository.findAll()) {
            bookRentalDtoList.add(conversionService.convert(bookRental, BookRentalDto.class));
        }
        if (CollectionUtils.isEmpty(bookRentalDtoList)) {
            throw new NotFoundException("Book rentals not found.");
        } else {
            log.info("Book rentals fetched");
            return bookRentalDtoList;
        }
    }

    @Override
    public BookRentalDto getOne(Long id) {
        if (bookRentalRepository.existsById(id)) {
            log.info("Book rental with id {} is listed.", id);
            return conversionService.convert(bookRentalRepository.getOne(id), BookRentalDto.class);
        } else {
            throw new NotFoundException(ApplicationConstants.BOOK_RENTAL + id + ApplicationConstants.NOT_FOUND);
        }
    }

    @Override
    public BookRentalDto update(BookRentalDto book) {
        throw new IllegalArgumentException("You can not change book rentals history");
    }

    @Override
    public BookRentalDto create(BookRentalDto bookRental) {
        throw new IllegalArgumentException("You can not add random book rentals history");
    }

    @Override
    public BookRentalDto delete(Long id) {
        throw new IllegalArgumentException("You can not delete book rentals history");
    }

    @Override
    public boolean existById(Long id) {
        return bookRentalRepository.existsById(id);
    }

    @Override
    public List<BookRentalDto> getByBookCopy(Long id) {
        List<BookRentalDto> bookRentalDtoList = new ArrayList<>();
        BookCopy bookCopy = bookCopyRepository.getOne(id);

        for (BookRental bookRental :
                bookRentalRepository.findByBookCopy(bookCopy)) {
            bookRentalDtoList.add(conversionService.convert(bookRental, BookRentalDto.class));
        }

        if (CollectionUtils.isEmpty(bookRentalDtoList)) {
            throw new NotFoundException("Book rentals for book copy with id " + id + ApplicationConstants.NOT_FOUND);

        } else {
            log.info("Rentals of the book copy with id {} are listed.", id);
            return bookRentalDtoList;
        }
    }

    @Override
    public Integer countByBookCopy(Long id) {
        if (bookCopyRepository.existsById(id)) {
            BookCopy bookCopy = bookCopyRepository.getOne(id);
            return bookRentalRepository.countByBookCopy(bookCopy);
        } else {
            throw new NotFoundException(ApplicationConstants.BOOK_COPY + id + ApplicationConstants.NOT_FOUND);
        }
    }

    public List<BookRentCount> mostRented(Long number) {
        List<BookRentCount> mostRentedList = bookRentalRepository.mostRented(number);
        if (CollectionUtils.isEmpty(mostRentedList)) {
            throw new NotFoundException("There is no any rented books.");
        } else {
            return mostRentedList;
        }
    }
}
