package com.hybridlibrary.services.serviceimpl;

import com.hybridlibrary.dtos.BookDto;
import com.hybridlibrary.exceptions.NotFoundException;
import com.hybridlibrary.models.Book;
import com.hybridlibrary.models.BookCopy;
import com.hybridlibrary.models.BookRental;
import com.hybridlibrary.models.User;
import com.hybridlibrary.repositories.BookCopyRepository;
import com.hybridlibrary.repositories.BookRentalRepository;
import com.hybridlibrary.repositories.BookRepository;
import com.hybridlibrary.services.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class BookServiceImplTest {


    private BookService bookService;

    private ConversionService conversionServiceMock;

    private BookRepository bookRepositoryMock;

    private BookRentalRepository bookRentalRepositoryMock;

    private BookCopyRepository bookCopyRepositoryMock;

    @Before
    public void setUp() {
        conversionServiceMock = mock(ConversionService.class);
        bookRepositoryMock = mock(BookRepository.class);
        bookRentalRepositoryMock = mock(BookRentalRepository.class);
        bookCopyRepositoryMock = mock(BookCopyRepository.class);
        bookService = new BookServiceImpl(conversionServiceMock, bookRepositoryMock, bookRentalRepositoryMock, bookCopyRepositoryMock);
    }

    @Test
    public void test_findAllBooks() {

        List<Book> list = new ArrayList<>();
        Book book = Book.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build();
        Book book1 = Book.builder()
                .id(2L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build();

        list.add(book);
        list.add(book1);


        when(bookRepositoryMock.findAll()).thenReturn(list);
        when(conversionServiceMock.convert(book, BookDto.class)).thenReturn(BookDto.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build())
                .thenReturn(BookDto.builder()
                        .id(2L)
                        .title("Title")
                        .author("Author")
                        .language("Language")
                        .rentPeriod(120)
                        .build());

        List<BookDto> bookList = bookService.findAll();

        assertEquals(list.size(), bookList.size());

        verify(bookRepositoryMock).findAll();
        verify(conversionServiceMock).convert(book, BookDto.class);
        verify(conversionServiceMock).convert(book1, BookDto.class);

        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);

    }

    @Test(expected = NotFoundException.class)
    public void test_findAllBooksNotFoundException() {

        List<Book> list = new ArrayList<>();
        when(bookRepositoryMock.findAll()).thenReturn(list);
        bookService.findAll();

    }

    @Test
    public void test_getOneBook() {
        Book book = Book.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build();

        when(bookRepositoryMock.existsById(book.getId())).thenReturn(true);
        when(bookRepositoryMock.getOne(book.getId())).thenReturn(book);
        when(conversionServiceMock.convert(book, BookDto.class)).thenReturn(BookDto.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build());

        BookDto result = bookService.getOne(1L);

        assertEquals(book.getId(), result.getId());
        assertEquals(book.getTitle(), result.getTitle());
        assertEquals(book.getAuthor(), result.getAuthor());
        assertEquals(book.getLanguage(), result.getLanguage());
        assertEquals(book.getRentPeriod(), result.getRentPeriod());

        verify(bookRepositoryMock).existsById(book.getId());
        verify(bookRepositoryMock).getOne(book.getId());
        verify(conversionServiceMock).convert(book, BookDto.class);

        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);
    }

    @Test
    public void test_getBookByAuthor() {
        Book book = Book.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build();

        List<Book> bookList = new ArrayList<>();
        bookList.add(book);
        when(bookRepositoryMock.findByAuthorContainingIgnoreCase(book.getAuthor())).thenReturn(bookList);
        when(conversionServiceMock.convert(book, BookDto.class)).thenReturn(BookDto.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build());

        List<BookDto> result = bookService.getByAuthor("Author");

        assertEquals(bookList.size(), result.size());

        verify(bookRepositoryMock).findByAuthorContainingIgnoreCase(book.getAuthor());
        verify(conversionServiceMock).convert(book, BookDto.class);

        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);
    }

    @Test(expected = NotFoundException.class)
    public void test_getBookByAuthorNotFoundException() {
        List<Book> list = new ArrayList<>();
        when(bookRepositoryMock.findByAuthorContainingIgnoreCase("ghjkl")).thenReturn(list);
        bookService.getByAuthor("ghjkl");
    }

    @Test(expected = NotFoundException.class)
    public void test_getOneBookNotFoundException() {

        when(bookRepositoryMock.existsById(-100L)).thenReturn(false);

        bookService.getOne(-100L);
        verify(bookRepositoryMock).existsById(-100L);
        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);
    }

    @Test
    public void test_updateBook() {
        BookDto bookDto = BookDto.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build();
        Book book = Book.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build();
        when(conversionServiceMock.convert(bookDto, Book.class)).thenReturn(book);
        when(bookRepositoryMock.existsById(book.getId())).thenReturn(true);
        when(bookRepositoryMock.save(book)).thenReturn(Book.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build());
        when(conversionServiceMock.convert(book, BookDto.class)).thenReturn(bookDto);

        bookService.update(bookDto);

        verify(conversionServiceMock).convert(bookDto, Book.class);
        verify(conversionServiceMock).convert(book, BookDto.class);
        verify(conversionServiceMock).convert(bookDto, Book.class);
        verify(bookRepositoryMock).existsById(book.getId());
        verify(bookRepositoryMock).save(book);
        verify(conversionServiceMock).convert(book, BookDto.class);


        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);

    }

    @Test(expected = NotFoundException.class)
    public void test_updateBookNotFoundException() {
        BookDto bookDto = BookDto.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build();
        when(conversionServiceMock.convert(bookDto, Book.class)).thenReturn(Book.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build());
        when(bookRepositoryMock.existsById(-100L)).thenReturn(false);
        bookService.update(bookDto);

        verify(conversionServiceMock).convert(bookDto, Book.class);
        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);
    }

    @Test
    public void test_deleteBookWhenThereIsNoBookCopies() {
        Book book = Book.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build();
        when(bookRepositoryMock.existsById(book.getId())).thenReturn(true);
        when(bookRepositoryMock.getOne(book.getId())).thenReturn(Book.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build());
        List<BookCopy> bookCopies = new ArrayList<>();
        when(bookCopyRepositoryMock.findByBook(book)).thenReturn(bookCopies);

        when(conversionServiceMock.convert(book, BookDto.class)).thenReturn(BookDto.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build());

        BookDto bookDto = bookService.delete(book.getId());

        assertEquals(book.getId(), bookDto.getId());

        verify(bookRepositoryMock).existsById(book.getId());
        verify(bookRepositoryMock).getOne(book.getId());
        verify(bookCopyRepositoryMock).findByBook(book);
        verify(bookRepositoryMock).deleteById(book.getId());
        verify(conversionServiceMock).convert(book, BookDto.class);

        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);
    }

    @Test(expected = NotFoundException.class)
    public void test_deleteBookNotFoundException() {
        when(bookRepositoryMock.existsById(-100L)).thenReturn(false);
        bookService.delete(-100L);

        verify(bookRepositoryMock).existsById(-100L);
        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);

    }

    @Test(expected = IllegalArgumentException.class)
    public void test_deleteBookIllegalArgumentException() {
        Book book = Book.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build();
        when(bookRepositoryMock.existsById(book.getId())).thenReturn(true);
        when(bookRepositoryMock.getOne(book.getId())).thenReturn(Book.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build());
        BookCopy bookCopy = BookCopy.builder()
                .id(1L)
                .book(book)
                .rentDate(LocalDate.now())
                .user(User.builder()
                        .id(1L)
                        .firstName("User")
                        .lastName("User")
                        .email("user@mail.com")
                        .password("test").build())
                .rented(true)
                .build();
        List<BookCopy> bookCopies = new ArrayList<>();
        bookCopies.add(bookCopy);
        when(bookCopyRepositoryMock.findByBook(book)).thenReturn(bookCopies);

        when(bookCopyRepositoryMock.countByBook(book)).thenReturn(1);
        when(bookCopyRepositoryMock.countByBookAndRentedEquals(book, false)).thenReturn(0);

        bookService.delete(book.getId());

        verify(bookRepositoryMock).existsById(book.getId());
        verify(bookRepositoryMock).getOne(book.getId());
        verify(bookCopyRepositoryMock).findByBook(book);
        verify(bookRepositoryMock).deleteById(book.getId());
        verify(conversionServiceMock).convert(book, BookDto.class);

        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);
    }

    @Test
    public void test_createBook() {
        BookDto bookDto = BookDto.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build();
        Book book = Book.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build();
        when(conversionServiceMock.convert(bookDto, Book.class)).thenReturn(book);
        when(bookRepositoryMock.save(book)).thenReturn(book);
        when(conversionServiceMock.convert(book, BookDto.class)).thenReturn(bookDto);

        bookService.create(bookDto);

        verify(conversionServiceMock).convert(bookDto, Book.class);
        verify(bookRepositoryMock).save(book);
        verify(conversionServiceMock).convert(book, BookDto.class);


        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);

    }

    @Test
    public void test_getBookByTitle() {
        Book book = Book.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build();

        List<Book> bookList = new ArrayList<>();
        bookList.add(book);
        when(bookRepositoryMock.findByTitleContainingIgnoreCase(book.getTitle())).thenReturn(bookList);
        when(conversionServiceMock.convert(book, BookDto.class)).thenReturn(BookDto.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build());

        List<BookDto> result = bookService.getByTitle("Title");

        assertEquals(bookList.size(), result.size());

        verify(bookRepositoryMock).findByTitleContainingIgnoreCase(book.getTitle());
        verify(conversionServiceMock).convert(book, BookDto.class);

        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);
    }

    @Test(expected = NotFoundException.class)
    public void test_getBookByTitleNotFoundException() {
        List<Book> list = new ArrayList<>();
        when(bookRepositoryMock.findByTitleContainingIgnoreCase("ghjkl")).thenReturn(list);
        bookService.getByTitle("ghjkl");

        verify(bookRepositoryMock).findByTitleContainingIgnoreCase("ghjkl");
        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);



    }

    @Test
    public void test_deleteBookWhenThereIsNoRentedCopies() {
        Book book = Book.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build();
        when(bookRepositoryMock.existsById(book.getId())).thenReturn(true);
        when(bookRepositoryMock.getOne(book.getId())).thenReturn(Book.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build());

        List<BookCopy> bookCopies = new ArrayList<>();
        BookCopy bookCopy = BookCopy.builder()
                .id(1L)
                .book(book)
                .rentDate(null)
                .user(null)
                .rented(false)
                .build();
        bookCopies.add(bookCopy);
        List<BookRental> bookRentals = new ArrayList<>();
        BookRental bookRental = BookRental.builder()
                .id(1L)
                .book(book)
                .bookCopy(bookCopy)
                .returnDate(LocalDate.now())
                .user(User.builder()
                        .id(1L)
                        .firstName("User")
                        .lastName("User")
                        .email("user@mail.com")
                        .password("test").build())
                .build();
        bookRentals.add(bookRental);
        when(bookCopyRepositoryMock.findByBook(book)).thenReturn(bookCopies);

        when(bookCopyRepositoryMock.countByBook(book)).thenReturn(1);
        when(bookCopyRepositoryMock.countByBookAndRentedEquals(book, false)).thenReturn(1);

        when(bookRentalRepositoryMock.deleteByBook(book)).thenReturn(bookRental.getId());
        when(bookCopyRepositoryMock.deleteByBook(book)).thenReturn(bookCopy.getId());

        when(conversionServiceMock.convert(book, BookDto.class)).thenReturn(BookDto.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build());

        BookDto bookDto = bookService.delete(book.getId());
        assertEquals(book.getId(), bookDto.getId());

        verify(bookRepositoryMock).existsById(book.getId());
        verify(bookRepositoryMock).getOne(book.getId());
        verify(bookCopyRepositoryMock).findByBook(book);
        verify(bookCopyRepositoryMock).countByBook(book);
        verify(bookCopyRepositoryMock).countByBookAndRentedEquals(book, false);
        verify(bookRentalRepositoryMock).deleteByBook(book);
        verify(bookCopyRepositoryMock).deleteByBook(book);
        verify(bookRepositoryMock).deleteById(book.getId());
        verify(conversionServiceMock).convert(book, BookDto.class);

        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);

    }

    @Test
    public void test_bookExistByIdIsTrue() {
        Book book = Book.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build();
        when(bookRepositoryMock.existsById(book.getId())).thenReturn(true);

        boolean result = bookService.existById(1L);
        assertThat(true, is(result));

        verify(bookRepositoryMock).existsById(book.getId());

        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);

    }

    @Test
    public void test_bookExistByIdIsFalse() {
        when(bookRepositoryMock.existsById(-100L)).thenReturn(false);
        boolean result = bookService.existById(-100L);
        assertThat(false, is(result));

        verify(bookRepositoryMock).existsById(-100L);

        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);
    }
}