package com.hybridlibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hybridlibrary.models.Book;
import java.util.Collection;

public interface BookRepository extends JpaRepository<Book, Long> {
    Collection<Book> findByTitleContainingIgnoreCase(String title);
    Collection<Book> findByAuthorContainingIgnoreCase(String author);

}
