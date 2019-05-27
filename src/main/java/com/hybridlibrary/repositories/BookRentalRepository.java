package com.hybridlibrary.repositories;

import com.hybridlibrary.models.BookCopy;
import com.hybridlibrary.models.BookRental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;


public interface BookRentalRepository extends JpaRepository<BookRental, Long> {

    Collection<BookRental> getByBookCopy(BookCopy bookCopy);

}
