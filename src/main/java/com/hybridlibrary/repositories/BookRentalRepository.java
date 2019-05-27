package com.hybridlibrary.repositories;

import com.hybridlibrary.models.BookCopy;
import com.hybridlibrary.models.BookRental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;


public interface BookRentalRepository extends JpaRepository<BookRental, Long> {

         Collection<BookRental> getByBookCopy(BookCopy bookCopy);

        /*@Query(value = "select top ?1 count(br.id), b.id, b.title from book_rental br "+
 "               join book_copy bc on (bc.id = br.book_copy_id) "+
                "join book b on (b.id = bc.book_id) "+
                "group by b.title, b.id")
        HashMap<String, Integer> mostRentedBooks(Integer number);*/
}
