package com.hybridlibrary.services.serviceimpl;

import com.hybridlibrary.dtos.BookCopyDto;
import com.hybridlibrary.exceptions.NotFoundException;
import com.hybridlibrary.models.Book;
import com.hybridlibrary.models.BookCopy;
import com.hybridlibrary.models.BookRental;
import com.hybridlibrary.models.User;
import com.hybridlibrary.repositories.BookCopyRepository;
import com.hybridlibrary.repositories.BookRentalRepository;
import com.hybridlibrary.repositories.BookRepository;
import com.hybridlibrary.repositories.UserRepository;
import com.hybridlibrary.services.BookCopyService;
import com.hybridlibrary.utils.ApplicationConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BookCopyServiceImpl implements BookCopyService {

    private BookRentalRepository bookRentalRepository;
    private UserRepository userRepository;
    private BookCopyRepository bookCopyRepository;
    private BookRepository bookRepository;
    private ConversionService conversionService;

    @Autowired
    public BookCopyServiceImpl(BookRentalRepository bookRentalRepository, UserRepository userRepository,
                               BookCopyRepository bookCopyRepository, ConversionService conversionService,
                               BookRepository bookRepository) {
        this.bookCopyRepository = bookCopyRepository;
        this.bookRentalRepository = bookRentalRepository;
        this.userRepository = userRepository;
        this.conversionService = conversionService;
        this.bookRepository = bookRepository;

    }

    public List<BookCopyDto> findAll() {
        List<BookCopyDto> bookCopyList = new ArrayList<>();
        for (BookCopy bookCopy :
                bookCopyRepository.findAll()) {
            bookCopyList.add(conversionService.convert(bookCopy, BookCopyDto.class));
        }
        if (CollectionUtils.isEmpty(bookCopyList)) {
            throw new NotFoundException("Book copies not found.");
        } else {
            log.info("Book copies fetched");
            return bookCopyList;
        }
    }

    @Override
    public BookCopyDto getOne(Long id) {
        if (bookCopyRepository.existsById(id)) {
            log.info("Book copy with id {} is listed.", id);
            return conversionService.convert(bookCopyRepository.getOne(id), BookCopyDto.class);
        } else {
            throw new NotFoundException(ApplicationConstants.BOOK_COPY + id + ApplicationConstants.NOT_FOUND);
        }

    }

    @Override
    public List<BookCopyDto> getByBook(Long id) {
        List<BookCopyDto> bookCopyList = new ArrayList<>();
        Book book = bookRepository.getOne(id);

        for (BookCopy bookCopy :
                bookCopyRepository.findByBook(book)) {
            bookCopyList.add(conversionService.convert(bookCopy, BookCopyDto.class));
        }

        if (CollectionUtils.isEmpty(bookCopyList)) {
            throw new NotFoundException("Book copy for book with id " + id + ApplicationConstants.NOT_FOUND);

        } else {
            log.info("Copies of the book with id {} are listed.", id);
            return bookCopyList;
        }
    }

    @Override
    public BookCopyDto update(BookCopyDto bookCopyDto) {
        BookCopy oldBookCopy = bookCopyRepository.getOne(bookCopyDto.getId());
        BookCopy newBookCopy = conversionService.convert(bookCopyDto, BookCopy.class);

        if (bookCopyRepository.existsById(bookCopyDto.getId())) {
            if (newBookCopy.getRented()) {
                if (oldBookCopy.getRented()) {
                    throw new IllegalArgumentException("You can not rent already rented book.");
                } else {
                    User user = userRepository.getOne(bookCopyDto.getUserId());
                    oldBookCopy.setUser(user);
                    oldBookCopy.setRentDate(LocalDate.now());
                }
            } else {
                if (oldBookCopy.getRented()) {
                    User user = userRepository.getOne(oldBookCopy.getUser().getId());
                    BookRental bookRental = BookRental.builder()
                            .user(user)
                            .book(oldBookCopy.getBook())
                            .bookCopy(oldBookCopy)
                            .returnDate(LocalDate.now())
                            .build();
                    bookRentalRepository.save(bookRental);
                    oldBookCopy.setRentDate(null);
                    oldBookCopy.setUser(null);
                }
            }
            oldBookCopy.setRented(newBookCopy.getRented());

            log.info("Book copy with id {} is updated.", oldBookCopy.getId());
            return conversionService.convert(bookCopyRepository.save(oldBookCopy), BookCopyDto.class);
        } else {
            throw new NotFoundException(ApplicationConstants.BOOK_COPY + bookCopyDto.getId() + ApplicationConstants.NOT_FOUND);
        }

    }

    @Override
    public BookCopyDto create(BookCopyDto bookCopyDto) {
        return new BookCopyDto();
    }

    @Override
    public BookCopyDto create(BookCopyDto bookCopyDto, Long bookId) {

        BookCopy bookCopy = conversionService.convert(bookCopyDto, BookCopy.class);
        Book book = bookRepository.getOne(bookId);
        bookCopy.setBook(book);
        if (bookCopy.getRented()) {
            User user = userRepository.getOne(bookCopyDto.getUserId());
            bookCopy.setUser(user);
            bookCopy.setRentDate(LocalDate.now());
        } else {
            bookCopy.setRentDate(null);
        }
        return conversionService.convert(bookCopyRepository.save(bookCopy), BookCopyDto.class);
    }

    @Transactional
    @Override
    public BookCopyDto delete(Long id) {
        if (bookCopyRepository.existsById(id)) {
            BookCopy bookCopy = bookCopyRepository.getOne(id);
            bookRentalRepository.deleteByBookCopy(bookCopy);
            log.info("Book copy with id {} is deleted.", id);
            bookCopyRepository.deleteById(id);
            return conversionService.convert(bookCopy, BookCopyDto.class);
        } else {
            throw new NotFoundException(ApplicationConstants.BOOK_COPY + id + ApplicationConstants.NOT_FOUND);
        }

    }

    @Override
    public boolean existById(Long id) {
        return bookCopyRepository.existsById(id);
    }

    @Override
    public BookCopyDto findByBookAndId(Long bookId, Long id) {
        if (bookCopyRepository.existsById(id) && bookRepository.existsById(bookId)) {
            Book book = bookRepository.getOne(bookId);
            return conversionService.convert(bookCopyRepository.findByBookAndId(book, id), BookCopyDto.class);
        } else {
            if (bookCopyRepository.existsById(id)) {
                throw new NotFoundException(ApplicationConstants.BOOK + bookId + ApplicationConstants.NOT_FOUND);
            } else {
                throw new NotFoundException(ApplicationConstants.BOOK_COPY + id + ApplicationConstants.NOT_FOUND);
            }
        }
    }

    @Override
    public List<BookCopyDto> overdueBookReturns() {
        List<BookCopyDto> bookCopyList = new ArrayList<>();
        List<BookCopy> overdueBooks = new ArrayList<>();
        for (BookCopy bookCopy : bookCopyRepository.findAll()) {
            if (bookCopy.getRented()) {
                Long duration = ChronoUnit.DAYS.between(bookCopy.getRentDate(), LocalDate.now());
                if (duration > bookCopy.getBook().getRentPeriod()) {
                    overdueBooks.add(bookCopy);
                }
            }

        }
        for (BookCopy bookCopy : overdueBooks) {
            bookCopyList.add(conversionService.convert(bookCopy, BookCopyDto.class));
        }
        if (CollectionUtils.isEmpty(bookCopyList)) {
            throw new NotFoundException("There is no overdue book returns.");
        } else {
            return bookCopyList;
        }
    }
}
