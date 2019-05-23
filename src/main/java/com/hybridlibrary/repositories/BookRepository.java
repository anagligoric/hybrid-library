package com.hybridlibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hybridlibrary.models.Book;
import java.util.Collection;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Collection<Book> findByNameContainingIgnoreCase(String name);
    Collection<Book> findByAuthorContainingIgnoreCase(String author);

}
