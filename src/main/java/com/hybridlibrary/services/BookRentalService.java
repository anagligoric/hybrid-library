package com.hybridlibrary.services;

import com.hybridlibrary.models.BookRental;


import java.util.Collection;

public interface BookRentalService extends AbstractService<BookRental, Long> {

    Collection<BookRental> findAll();

    BookRental getOne(Long id);

    BookRental update(BookRental book);

    BookRental create(BookRental book);

    void delete(Long id);

    boolean existById(Long id);

}
