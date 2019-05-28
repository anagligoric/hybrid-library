package com.hybridlibrary.services.serviceimpl;

import com.hybridlibrary.models.Book;
import com.hybridlibrary.models.BookCopy;
import com.hybridlibrary.models.BookRental;
import com.hybridlibrary.repositories.BookCopyRepository;
import com.hybridlibrary.repositories.BookRentalRepository;
import com.hybridlibrary.repositories.BookRepository;
import com.hybridlibrary.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {


    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookRentalRepository bookRentalRepository;

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Override
    public Collection<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book getOne(Long id) {
        return bookRepository.getOne(id);
    }

    @Override
    public Collection<Book> getByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);

    }

    @Override
    public Collection<Book> getByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    @Override
    public Book update(Book book) {
        bookRepository.save(book);
        return book;
    }

    @Override
    public Book create(Book book) {
        Book newBook = bookRepository.save(book);
        return newBook;
    }

    @Override
    public void delete(Long id) {

                Book book = bookRepository.getOne(id);

                Collection<BookCopy> bookCopies = bookCopyRepository.findByBook(book);

                if(CollectionUtils.isEmpty(bookCopies)){
                    bookRepository.deleteById(id);
                }else{
                    List<BookRental> bookRentals = new ArrayList<>();
                    List<Date> rentalDates =  new ArrayList<>();
                    for (BookCopy bookCopy:
                         bookCopies) {
                        Collection<BookRental> rentals = bookRentalRepository.getByBookCopy(bookCopy);
                       for (BookRental bookRental:
                             rentals) {
                           bookRentals.add(bookRental);
                           rentalDates.add(bookRental.getReturnDate());
                        }
                    }
                    if(CollectionUtils.isEmpty(bookRentals) || !rentalDates.contains(null)){
                        for(BookRental bookRental: bookRentals){
                            bookRentalRepository.deleteById(bookRental.getId());
                        }
                        for (BookCopy bookCopy:
                                bookCopies) {
                            bookCopyRepository.deleteById(bookCopy.getId());

                        }
                        bookRepository.deleteById(id);
                        System.out.println("Deleted");

                    }else{
                        System.out.println("Exist");
                    }
                }



    }

    @Override
    public boolean existById(Long id) {
        return bookRepository.existsById(id);
    }

}
