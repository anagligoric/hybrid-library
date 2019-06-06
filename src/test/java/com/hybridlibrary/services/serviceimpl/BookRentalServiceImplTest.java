package com.hybridlibrary.services.serviceimpl;

import com.hybridlibrary.dtos.BookRentalDto;
import com.hybridlibrary.exceptions.NotFoundException;
import com.hybridlibrary.models.Book;
import com.hybridlibrary.models.BookCopy;
import com.hybridlibrary.models.BookRental;
import com.hybridlibrary.models.User;
import com.hybridlibrary.repositories.BookCopyRepository;
import com.hybridlibrary.repositories.BookRentCount;
import com.hybridlibrary.repositories.BookRentalRepository;
import com.hybridlibrary.services.BookRentalService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.convert.ConversionService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class BookRentalServiceImplTest {
    private BookRentalService bookRentalService;

    private ConversionService conversionServiceMock;

    private BookRentalRepository bookRentalRepositoryMock;

    private BookCopyRepository bookCopyRepositoryMock;

    @Before
    public void setUp() {
        conversionServiceMock = mock(ConversionService.class);
        bookRentalRepositoryMock = mock(BookRentalRepository.class);
        bookCopyRepositoryMock = mock(BookCopyRepository.class);
        bookRentalService = new BookRentalServiceImpl(bookRentalRepositoryMock, conversionServiceMock,
                bookCopyRepositoryMock);
    }

    @Test
    public void test_findAllBookRentals() {
        List<BookRental> bookRentals = new ArrayList<>();
        BookRental bookRental = BookRental.builder()
                .id(1L)
                .bookCopy(BookCopy.builder()
                        .id(1L)
                        .book(com.hybridlibrary.models.Book.builder()
                                .id(1L)
                                .title("Title")
                                .author("Author")
                                .language("Language")
                                .rentPeriod(120)
                                .build())
                        .user(null)
                        .rented(false)
                        .rentDate(null)
                        .build())
                .book(Book.builder()
                        .id(1L)
                        .title("Title")
                        .author("Author")
                        .language("Language")
                        .rentPeriod(120)
                        .build())
                .returnDate(LocalDate.now())
                .user(User.builder()
                        .id(1L)
                        .firstName("Nicolais")
                        .lastName("Kiraly")
                        .email("nkiraly0@pcworld.com")
                        .username("nkiraly0")
                        .password("$2a$10$6yOYfJPCpucGIOI9R4/vhuoSo8pwT855Qu.4q7wmcdsSEiWIgXMv2")
                        .role("ROLE_ADMIN")
                        .build())
                .build();
        bookRentals.add(bookRental);
        when(bookRentalRepositoryMock.findAll()).thenReturn(bookRentals);
        when(conversionServiceMock.convert(bookRental, BookRentalDto.class)).thenReturn(BookRentalDto.builder()
                .id(1L)
                .bookCopyId(1L)
                .bookId(1L)
                .userId(1L)
                .returnDate(LocalDate.now())
                .build());
        List<BookRentalDto> result = bookRentalService.findAll();

        assertEquals(bookRentals.size(), result.size());

        verify(bookRentalRepositoryMock).findAll();
        verify(conversionServiceMock).convert(bookRental, BookRentalDto.class);

        verifyNoMoreInteractions(bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);

    }

    @Test(expected = NotFoundException.class)
    public void test_FindAllBookCopiesNotFoundException() {
        List<BookRental> list = new ArrayList<>();
        when(bookRentalRepositoryMock.findAll()).thenReturn(list);
        bookRentalService.findAll();

        verify(bookRentalRepositoryMock).findAll();
        verifyNoMoreInteractions(bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);
    }

    @Test
    public void test_getOneBookRental() {
        BookRental bookRental = BookRental.builder()
                .id(1L)
                .bookCopy(BookCopy.builder()
                        .id(1L)
                        .book(com.hybridlibrary.models.Book.builder()
                                .id(1L)
                                .title("Title")
                                .author("Author")
                                .language("Language")
                                .rentPeriod(120)
                                .build())
                        .user(null)
                        .rented(false)
                        .rentDate(null)
                        .build())
                .book(Book.builder()
                        .id(1L)
                        .title("Title")
                        .author("Author")
                        .language("Language")
                        .rentPeriod(120)
                        .build())
                .returnDate(LocalDate.now())
                .user(User.builder()
                        .id(1L)
                        .firstName("Nicolais")
                        .lastName("Kiraly")
                        .email("nkiraly0@pcworld.com")
                        .username("nkiraly0")
                        .password("$2a$10$6yOYfJPCpucGIOI9R4/vhuoSo8pwT855Qu.4q7wmcdsSEiWIgXMv2")
                        .role("ROLE_ADMIN")
                        .build())
                .build();

        when(bookRentalRepositoryMock.existsById(bookRental.getId())).thenReturn(true);
        when(bookRentalRepositoryMock.getOne(bookRental.getId())).thenReturn(bookRental);
        when(conversionServiceMock.convert(bookRental, BookRentalDto.class)).thenReturn(BookRentalDto.builder()
                .id(1L)
                .bookId(1L)
                .userId(1L)
                .bookCopyId(1L)
                .returnDate(LocalDate.now())
                .build());
        BookRentalDto result = bookRentalService.getOne(1L);

        assertEquals(bookRental.getId(), result.getId());
        assertEquals(bookRental.getBook().getId(), result.getBookId());
        assertEquals(bookRental.getReturnDate(), result.getReturnDate());
        assertEquals(bookRental.getBookCopy().getId(), result.getBookCopyId());

        verify(bookRentalRepositoryMock).existsById(bookRental.getId());
        verify(bookRentalRepositoryMock).getOne(bookRental.getId());
        verify(conversionServiceMock).convert(bookRental, BookRentalDto.class);

        verifyNoMoreInteractions(bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);
    }

    @Test(expected = NotFoundException.class)
    public void test_getOneBookRentalNotFoundException() {
        when(bookRentalRepositoryMock.existsById(-100L)).thenReturn(false);

        bookRentalService.getOne(-100L);
        verify(bookRentalRepositoryMock).existsById(-100L);
        verifyNoMoreInteractions(bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_deleteBookRentalIllegalArgumentException() {
        BookRental bookRental = BookRental.builder()
                .id(1L)
                .bookCopy(BookCopy.builder()
                        .id(1L)
                        .book(com.hybridlibrary.models.Book.builder()
                                .id(1L)
                                .title("Title")
                                .author("Author")
                                .language("Language")
                                .rentPeriod(120)
                                .build())
                        .user(null)
                        .rented(false)
                        .rentDate(null)
                        .build())
                .book(Book.builder()
                        .id(1L)
                        .title("Title")
                        .author("Author")
                        .language("Language")
                        .rentPeriod(120)
                        .build())
                .returnDate(LocalDate.now())
                .user(User.builder()
                        .id(1L)
                        .firstName("Nicolais")
                        .lastName("Kiraly")
                        .email("nkiraly0@pcworld.com")
                        .username("nkiraly0")
                        .password("$2a$10$6yOYfJPCpucGIOI9R4/vhuoSo8pwT855Qu.4q7wmcdsSEiWIgXMv2")
                        .role("ROLE_ADMIN")
                        .build())
                .build();

        bookRentalService.delete(bookRental.getId());

        verifyNoMoreInteractions(bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_updateBookRentalIllegalArgumentException() {
        BookRentalDto bookRentalDto = BookRentalDto.builder()
                .id(1L)
                .bookId(1L)
                .userId(1L)
                .bookCopyId(1L)
                .returnDate(LocalDate.now())
                .build();

        bookRentalService.update(bookRentalDto);

        verifyNoMoreInteractions(bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_createBookRentalIllegalArgumentException() {
        BookRentalDto bookRentalDto = BookRentalDto.builder()
                .id(1L)
                .bookId(1L)
                .userId(1L)
                .bookCopyId(1L)
                .returnDate(LocalDate.now())
                .build();

        bookRentalService.create(bookRentalDto);

        verifyNoMoreInteractions(bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);
    }

    @Test
    public void test_getBookRentalByBookCopy() {

        List<BookRental> bookRentals = new ArrayList<>();
        BookCopy bookCopy = BookCopy.builder()
                .id(1L)
                .book(Book.builder()
                        .id(1L)
                        .title("Title")
                        .author("Author")
                        .language("Language")
                        .rentPeriod(120)
                        .build())
                .user(null)
                .rented(false)
                .rentDate(null)
                .build();

        BookRental bookRental = BookRental.builder()
                .id(1L)
                .bookCopy(bookCopy)
                .book(Book.builder()
                        .id(1L)
                        .title("Title")
                        .author("Author")
                        .language("Language")
                        .rentPeriod(120)
                        .build())
                .returnDate(LocalDate.now())
                .user(User.builder()
                        .id(1L)
                        .firstName("Nicolais")
                        .lastName("Kiraly")
                        .email("nkiraly0@pcworld.com")
                        .username("nkiraly0")
                        .password("$2a$10$6yOYfJPCpucGIOI9R4/vhuoSo8pwT855Qu.4q7wmcdsSEiWIgXMv2")
                        .role("ROLE_ADMIN")
                        .build())
                .build();


        bookRentals.add(bookRental);

        when(bookCopyRepositoryMock.getOne(bookCopy.getId())).thenReturn(bookCopy);
        when(bookRentalRepositoryMock.findByBookCopy(bookCopy)).thenReturn(bookRentals);
        when(conversionServiceMock.convert(bookRental, BookRentalDto.class)).thenReturn(BookRentalDto.builder()
                .id(1L)
                .bookCopyId(1L)
                .bookId(1L)
                .returnDate(LocalDate.now())
                .userId(1L)
                .build());

        List<BookRentalDto> result = bookRentalService.getByBookCopy(bookCopy.getId());

        assertEquals(bookRentals.size(), result.size());

        verify(bookCopyRepositoryMock).getOne(bookCopy.getId());

        verify(bookRentalRepositoryMock).findByBookCopy(bookCopy);
        verify(conversionServiceMock).convert(bookRental, BookRentalDto.class);
        verifyNoMoreInteractions(bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);

    }

    @Test(expected = NotFoundException.class)
    public void test_getBookRentalByBookCopyNotFoundException() {
        List<BookRental> bookRentals = new ArrayList<>();
        BookCopy bookCopy = BookCopy.builder()
                .id(1L)
                .book(Book.builder()
                        .id(1L)
                        .title("Title")
                        .author("Author")
                        .language("Language")
                        .rentPeriod(120)
                        .build())
                .user(null)
                .rented(false)
                .rentDate(null)
                .build();

        when(bookCopyRepositoryMock.getOne(bookCopy.getId())).thenReturn(bookCopy);
        when(bookRentalRepositoryMock.findByBookCopy(bookCopy)).thenReturn(bookRentals);

        bookRentalService.getByBookCopy(bookCopy.getId());

        verify(bookCopyRepositoryMock).getOne(bookCopy.getId());
        verify(bookRentalRepositoryMock).findByBookCopy(bookCopy);
        verifyNoMoreInteractions(bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);

    }


    @Test
    public void test_countByBookCopy() {

        BookCopy bookCopy = BookCopy.builder()
                .id(1L)
                .book(Book.builder()
                        .id(1L)
                        .title("Title")
                        .author("Author")
                        .language("Language")
                        .rentPeriod(120)
                        .build())
                .user(null)
                .rented(false)
                .rentDate(null)
                .build();

        BookRental bookRental = BookRental.builder()
                .id(1L)
                .bookCopy(bookCopy)
                .book(Book.builder()
                        .id(1L)
                        .title("Title")
                        .author("Author")
                        .language("Language")
                        .rentPeriod(120)
                        .build())
                .returnDate(LocalDate.now())
                .user(User.builder()
                        .id(1L)
                        .firstName("Nicolais")
                        .lastName("Kiraly")
                        .email("nkiraly0@pcworld.com")
                        .username("nkiraly0")
                        .password("$2a$10$6yOYfJPCpucGIOI9R4/vhuoSo8pwT855Qu.4q7wmcdsSEiWIgXMv2")
                        .role("ROLE_ADMIN")
                        .build())
                .build();

        when(bookCopyRepositoryMock.existsById(bookCopy.getId())).thenReturn(true);
        when(bookCopyRepositoryMock.getOne(bookCopy.getId())).thenReturn(bookCopy);
        when(bookRentalRepositoryMock.countByBookCopy(bookCopy)).thenReturn(1);

        Integer result = bookRentalService.countByBookCopy(bookCopy.getId());

        assertEquals(new Long(1), new Long(result));

        verify(bookCopyRepositoryMock).existsById(bookCopy.getId());
        verify(bookCopyRepositoryMock).getOne(bookCopy.getId());
        verify(bookRentalRepositoryMock).countByBookCopy(bookCopy);
        verifyNoMoreInteractions(bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);

    }

    @Test(expected = NotFoundException.class)
    public void test_countBookRentalByBookCopyNotFoundException() {
        BookCopy bookCopy = BookCopy.builder()
                .id(1L)
                .book(Book.builder()
                        .id(1L)
                        .title("Title")
                        .author("Author")
                        .language("Language")
                        .rentPeriod(120)
                        .build())
                .user(null)
                .rented(false)
                .rentDate(null)
                .build();

        when(bookCopyRepositoryMock.existsById(bookCopy.getId())).thenReturn(false);

        bookRentalService.getByBookCopy(bookCopy.getId());

        verify(bookCopyRepositoryMock).existsById(bookCopy.getId());

        verifyNoMoreInteractions(bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);

    }

    @Test(expected = NotFoundException.class)
    public void test_mostRentedBooksNotFoundException() {
        Book book = Book.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build();

        List<BookRentCount> books = new ArrayList<>();

        when(bookRentalRepositoryMock.mostRented(1L)).thenReturn(books);

        bookRentalService.mostRented(1L);

        verify(bookRentalRepositoryMock).mostRented(1L);
        verifyNoMoreInteractions(bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);
    }

    @Test
    public void test_existRentalByIdIsTrue() {
        BookRental bookRental = BookRental.builder()
                .id(1L)
                .bookCopy(BookCopy.builder()
                        .id(1L)
                        .book(com.hybridlibrary.models.Book.builder()
                                .id(1L)
                                .title("Title")
                                .author("Author")
                                .language("Language")
                                .rentPeriod(120)
                                .build())
                        .user(null)
                        .rented(false)
                        .rentDate(null)
                        .build())
                .book(Book.builder()
                        .id(1L)
                        .title("Title")
                        .author("Author")
                        .language("Language")
                        .rentPeriod(120)
                        .build())
                .returnDate(LocalDate.now())
                .user(User.builder()
                        .id(1L)
                        .firstName("Nicolais")
                        .lastName("Kiraly")
                        .email("nkiraly0@pcworld.com")
                        .username("nkiraly0")
                        .password("$2a$10$6yOYfJPCpucGIOI9R4/vhuoSo8pwT855Qu.4q7wmcdsSEiWIgXMv2")
                        .role("ROLE_ADMIN")
                        .build())
                .build();
        when(bookRentalRepositoryMock.existsById(bookRental.getId())).thenReturn(true);

        boolean result = bookRentalService.existById(1L);
        assertThat(true, is(result));

        verify(bookRentalRepositoryMock).existsById(bookRental.getId());

        verifyNoMoreInteractions(bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);

    }

    @Test
    public void test_existRentalByIdIsFalse() {
        when(bookRentalRepositoryMock.existsById(-100L)).thenReturn(false);
        boolean result = bookRentalService.existById(-100L);
        assertThat(false, is(result));

        verify(bookRentalRepositoryMock).existsById(-100L);

        verifyNoMoreInteractions(bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);
    }

}