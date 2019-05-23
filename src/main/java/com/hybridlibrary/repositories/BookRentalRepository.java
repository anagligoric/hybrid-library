package com.hybridlibrary.repositories;

import com.hybridlibrary.models.BookRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.HashMap;

public interface BookRentalRepository extends JpaRepository<BookRental, Long> {

       /* @Query(value = "select top 1 count(br.id), b.name from book_rental br " +
                "join book_copy bc on (bc.id = br.book_copy) " +
                "join book b on (b.id = bc.book) " +
                "group by b.name")
        HashMap<String, Integer> mostRentedBooks();*/
}
