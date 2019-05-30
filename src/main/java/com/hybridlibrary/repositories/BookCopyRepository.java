package com.hybridlibrary.repositories;

import com.hybridlibrary.models.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hybridlibrary.models.Book;

import javax.transaction.Transactional;
import java.util.Collection;


public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    Collection<BookCopy> findByBook(Book book);
    Integer countByBook(Book book);
    @Transactional
    Long deleteByBook(Book book);
    Integer countByBookAndRentedEquals(Book book, Boolean rented);
    BookCopy findByBookAndId(Book book, Long id);
}

