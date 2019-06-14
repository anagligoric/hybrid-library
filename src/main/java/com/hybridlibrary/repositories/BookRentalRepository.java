package com.hybridlibrary.repositories;

import com.hybridlibrary.models.Book;
import com.hybridlibrary.models.BookCopy;
import com.hybridlibrary.models.BookRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;


public interface BookRentalRepository extends JpaRepository<BookRental, Long> {

    Collection<BookRental> findByBookCopy(BookCopy bookCopy);

    Integer countByBookCopy(BookCopy bookCopy);

    Long deleteByBookCopy(BookCopy bookCopy);

    Long deleteByBook(Book book);

    @Query(value = "select top ?1 b.id as bookId, b.title as bookTitle, b.author as bookAuthor, b.language as bookLanguage, count(br.id) AS rentalCount from book_rental br join book b on (b.id=br.book_id) group by b.id order by rentalCount desc", nativeQuery = true)
    List<BookRentCount> mostRented(Long number);





}
