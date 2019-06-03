package com.hybridlibrary.services;

import com.hybridlibrary.dtos.BookRentalDto;
import com.hybridlibrary.repositories.BookRentCount;

import java.util.List;

public interface BookRentalService extends AbstractService<BookRentalDto, Long> {

    List<BookRentalDto> findAll();

    BookRentalDto getOne(Long id);

    BookRentalDto update(BookRentalDto bookRentalDto);

    BookRentalDto create(BookRentalDto bookRentalDto);

    BookRentalDto delete(Long id);

    boolean existById(Long id);

    List<BookRentalDto> getByBookCopy(Long id);

    Integer countByBookCopy(Long id);

    List<BookRentCount> mostRented(Long number);

}
