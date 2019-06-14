package com.hybridlibrary.services.serviceimpl;

import com.hybridlibrary.dtos.BookCopyDto;
import com.hybridlibrary.exceptions.NotFoundException;
import com.hybridlibrary.models.Book;
import com.hybridlibrary.models.BookCopy;
import com.hybridlibrary.models.User;
import com.hybridlibrary.repositories.BookCopyRepository;
import com.hybridlibrary.repositories.BookRentalRepository;
import com.hybridlibrary.repositories.BookRepository;
import com.hybridlibrary.repositories.UserRepository;
import com.hybridlibrary.services.BookCopyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class BookCopyServiceImplTest {
    private BookCopyService bookCopyService;

    private ConversionService conversionServiceMock;

    private BookRepository bookRepositoryMock;

    private BookRentalRepository bookRentalRepositoryMock;

    private BookCopyRepository bookCopyRepositoryMock;

    private UserRepository userRepositoryMock;

    @Before
    public void setUp() {
        conversionServiceMock = mock(ConversionService.class);
        bookRepositoryMock = mock(BookRepository.class);
        bookRentalRepositoryMock = mock(BookRentalRepository.class);
        bookCopyRepositoryMock = mock(BookCopyRepository.class);
        userRepositoryMock = mock(UserRepository.class);
        bookCopyService = new BookCopyServiceImpl(bookRentalRepositoryMock, userRepositoryMock, bookCopyRepositoryMock,
                conversionServiceMock, bookRepositoryMock);
    }

    @Test
    public void test_findAllBookCopies() {
        List<BookCopy> bookCopies = new ArrayList<>();
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
        bookCopies.add(bookCopy);
        when(bookCopyRepositoryMock.findAll()).thenReturn(bookCopies);
        when(conversionServiceMock.convert(bookCopy, BookCopyDto.class)).thenReturn(BookCopyDto.builder()
                .id(1L)
                .bookId(1L)
                .userId(1L)
                .rented(false)
                .rentDate(null)
                .build());
        List<BookCopyDto> result = bookCopyService.findAll();

        assertEquals(bookCopies.size(), result.size());

        verify(bookCopyRepositoryMock).findAll();
        verify(conversionServiceMock).convert(bookCopy, BookCopyDto.class);

        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, userRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);

    }

    @Test(expected = NotFoundException.class)
    public void test_FindAllBookCopiesNotFoundException() {
        List<BookCopy> list = new ArrayList<>();
        when(bookCopyRepositoryMock.findAll()).thenReturn(list);
        bookCopyService.findAll();

        verify(bookCopyRepositoryMock).findAll();
        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, userRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);
    }

    @Test
    public void test_getOneBookCopy() {
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

        when(bookCopyRepositoryMock.existsById(bookCopy.getId())).thenReturn(true);
        when(bookCopyRepositoryMock.getOne(bookCopy.getId())).thenReturn(bookCopy);
        when(conversionServiceMock.convert(bookCopy, BookCopyDto.class)).thenReturn(BookCopyDto.builder()
                .id(1L)
                .bookId(1L)
                .userId(1L)
                .rented(false)
                .rentDate(null)
                .build());
        BookCopyDto result = bookCopyService.getOne(1L);

        assertEquals(bookCopy.getId(), result.getId());
        assertEquals(bookCopy.getBook().getId(), result.getBookId());
        assertEquals(bookCopy.getRentDate(), result.getRentDate());
        assertEquals(bookCopy.getRented(), result.isRented());

        verify(bookCopyRepositoryMock).existsById(bookCopy.getId());
        verify(bookCopyRepositoryMock).getOne(bookCopy.getId());
        verify(conversionServiceMock).convert(bookCopy, BookCopyDto.class);

        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, userRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);

    }

    @Test(expected = NotFoundException.class)
    public void test_getOneBookCopyNotFoundException() {
        when(bookCopyRepositoryMock.existsById(-100L)).thenReturn(false);

        bookCopyService.getOne(-100L);
        verify(bookCopyRepositoryMock).existsById(-100L);
        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, userRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);
    }

    @Test
    public void test_getBookCopyByBook() {
        List<BookCopy> bookCopies = new ArrayList<>();
        Book book = Book.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build();
        BookCopy bookCopy = BookCopy.builder()
                .id(1L)
                .book(book)
                .user(null)
                .rented(false)
                .rentDate(null)
                .build();

        bookCopies.add(bookCopy);

        when(bookRepositoryMock.getOne(book.getId())).thenReturn(book);
        when(bookCopyRepositoryMock.findByBook(book)).thenReturn(bookCopies);
        when(conversionServiceMock.convert(bookCopy, BookCopyDto.class)).thenReturn(BookCopyDto.builder()
                .id(1L)
                .bookId(book.getId())
                .userId(null)
                .rented(false)
                .rentDate(null)
                .build());

        List<BookCopyDto> result = bookCopyService.getByBook(book.getId());

        assertEquals(bookCopies.size(), result.size());

        verify(bookRepositoryMock).getOne(book.getId());

        verify(bookCopyRepositoryMock).findByBook(book);
        verify(conversionServiceMock).convert(bookCopy, BookCopyDto.class);

        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, userRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);

    }

    @Test(expected = NotFoundException.class)
    public void test_getBookCopyByBookNotFoundException() {
        List<BookCopy> bookCopies = new ArrayList<>();
        Book book = Book.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build();

        when(bookRepositoryMock.getOne(book.getId())).thenReturn(book);
        when(bookCopyRepositoryMock.findByBook(book)).thenReturn(bookCopies);

        bookCopyService.getByBook(book.getId());

        verify(bookRepositoryMock).getOne(book.getId());
        verify(bookCopyRepositoryMock).findByBook(book);
        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, userRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);


    }


    @Test(expected = NotFoundException.class)
    public void test_updateBookCopyNotFoundException() {

        Book book = Book.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build();
        BookCopyDto bookCopyDto = BookCopyDto.builder()
                .id(1L)
                .bookId(book.getId())
                .userId(null)
                .rented(false)
                .rentDate(null)
                .build();

        when(bookCopyRepositoryMock.getOne(bookCopyDto.getId())).thenReturn(BookCopy.builder()
                .id(1L)
                .book(book)
                .user(null)
                .rented(false)
                .rentDate(null)
                .build());

        when(conversionServiceMock.convert(bookCopyDto, BookCopy.class)).thenReturn(BookCopy.builder()
                .id(1L)
                .book(book)
                .user(null)
                .rented(false)
                .rentDate(null)
                .build());

        when(bookCopyRepositoryMock.existsById(bookCopyDto.getId())).thenReturn(false);

        bookCopyService.update(bookCopyDto);

        verify(bookCopyRepositoryMock).getOne(bookCopyDto.getId());
        verify(conversionServiceMock).convert(bookCopyDto, Book.class);

        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, userRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);


    }

    @Test
    public void test_createBookCopyWhichIsNotRented() {
        BookCopyDto bookCopyDto = BookCopyDto.builder()
                .id(1L)
                .userId(null)
                .bookId(1L)
                .rentDate(null)
                .rented(false)
                .build();
        Book book = Book.builder()
                .id(1L)
                .author("Author")
                .title("Title")
                .language("Language")
                .rentPeriod(120)
                .build();
        BookCopy bookCopy = BookCopy.builder()
                .id(1L)
                .user(null)
                .book(book)
                .rentDate(null)
                .rented(false)
                .build();

        when(conversionServiceMock.convert(bookCopyDto, BookCopy.class)).thenReturn(bookCopy);
        when(bookRepositoryMock.getOne(bookCopyDto.getBookId())).thenReturn(book);

        when(bookCopyRepositoryMock.save(bookCopy)).thenReturn(bookCopy);
        when(conversionServiceMock.convert(bookCopy, BookCopyDto.class)).thenReturn(bookCopyDto);

        BookCopyDto result = bookCopyService.create(bookCopyDto, 1L);

        assertEquals(bookCopyDto.getId(), result.getId());

        verify(conversionServiceMock).convert(bookCopyDto, BookCopy.class);
        verify(bookRepositoryMock).getOne(bookCopyDto.getBookId());
        verify(bookCopyRepositoryMock).save(bookCopy);
        verify(conversionServiceMock).convert(bookCopy, BookCopyDto.class);


        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, userRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);
    }

    @Test
    public void test_createBookCopyWhichIsRented() {
        BookCopyDto bookCopyDto = BookCopyDto.builder()
                .id(1L)
                .userId(1L)
                .bookId(1L)
                .rentDate(null)
                .rented(true)
                .build();
        Book book = Book.builder()
                .id(1L)
                .author("Author")
                .title("Title")
                .language("Language")
                .rentPeriod(120)
                .build();
        BookCopy bookCopy = BookCopy.builder()
                .id(1L)
                .user(null)
                .book(book)
                .rentDate(null)
                .rented(true)
                .build();

        when(conversionServiceMock.convert(bookCopyDto, BookCopy.class)).thenReturn(bookCopy);
        when(bookRepositoryMock.getOne(bookCopyDto.getBookId())).thenReturn(book);

        when(bookCopyRepositoryMock.save(bookCopy)).thenReturn(bookCopy);

        when(userRepositoryMock.getOne(bookCopyDto.getUserId())).thenReturn(User.builder()
                .id(1L)
                .firstName("Name")
                .lastName("Last name")
                .email("mail@hj.com")
                .password("test")
                .role("ROLE_USER")
                .username("username")
                .build());
        when(conversionServiceMock.convert(bookCopy, BookCopyDto.class)).thenReturn(bookCopyDto);

        BookCopyDto result = bookCopyService.create(bookCopyDto, 1L);

        assertEquals(bookCopyDto.getId(), result.getId());

        verify(conversionServiceMock).convert(bookCopyDto, BookCopy.class);
        verify(bookRepositoryMock).getOne(bookCopyDto.getBookId());
        verify(bookCopyRepositoryMock).save(bookCopy);
        verify(userRepositoryMock).getOne(bookCopyDto.getUserId());
        verify(conversionServiceMock).convert(bookCopy, BookCopyDto.class);


        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, userRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);
    }

    @Test
    public void test_deleteBookCopy() {
        Book book = Book.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build();
        BookCopyDto bookCopyDto = BookCopyDto.builder()
                .id(1L)
                .bookId(book.getId())
                .userId(null)
                .rented(false)
                .rentDate(null)
                .build();
        BookCopy bookCopy = BookCopy.builder()
                .id(1L)
                .book(book)
                .user(null)
                .rented(false)
                .rentDate(null)
                .build();

        when(bookCopyRepositoryMock.existsById(bookCopyDto.getId())).thenReturn(true);
        when(bookCopyRepositoryMock.getOne(bookCopyDto.getId())).thenReturn(bookCopy);

        when(bookRentalRepositoryMock.deleteByBookCopy(bookCopy)).thenReturn(1L);

        when(conversionServiceMock.convert(bookCopy, BookCopyDto.class)).thenReturn(bookCopyDto);

        BookCopyDto result = bookCopyService.delete(bookCopyDto.getId());

        assertEquals(bookCopyDto.getId(), result.getId());
        verify(bookCopyRepositoryMock).existsById(bookCopyDto.getId());
        verify(bookCopyRepositoryMock).getOne(bookCopyDto.getId());
        verify(bookRentalRepositoryMock).deleteByBookCopy(bookCopy);
        verify(conversionServiceMock).convert(bookCopy, BookCopyDto.class);

        verify(bookCopyRepositoryMock).deleteById(bookCopyDto.getId());

        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, userRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);

    }

    @Test(expected = NotFoundException.class)
    public void test_deleteBookCopyNotFoundException() {
        when(bookCopyRepositoryMock.existsById(-100L)).thenReturn(false);
        bookCopyService.delete(-100L);

        verify(bookCopyRepositoryMock).existsById(-100L);
        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, userRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);


    }

    @Test
    public void test_existByIdIsTrue() {
        Book book = Book.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build();
        when(bookCopyRepositoryMock.existsById(book.getId())).thenReturn(true);

        boolean result = bookCopyService.existById(1L);
        assertThat(true, is(result));

        verify(bookCopyRepositoryMock).existsById(book.getId());
        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, userRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);

    }

    @Test
    public void test_existByIdIsFalse() {
        when(bookCopyRepositoryMock.existsById(-100L)).thenReturn(false);
        boolean result = bookCopyService.existById(-100L);
        assertThat(false, is(result));

        verify(bookCopyRepositoryMock).existsById(-100L);
        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, userRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);
    }

    @Test(expected = NotFoundException.class)
    public void test_findBookCopyByBookAndIdNotFoundException() {
        when(bookCopyRepositoryMock.existsById(-100L)).thenReturn(false);
        when(bookRepositoryMock.existsById(1L)).thenReturn(true);

        bookCopyService.findByBookAndId(-100L, 1L);

        verify(bookCopyRepositoryMock).existsById(-100L);
        verify(bookRepositoryMock).existsById(1L);
        verifyNoMoreInteractions(bookRepositoryMock, bookRentalRepositoryMock, userRepositoryMock, bookCopyRepositoryMock, conversionServiceMock);

    }


}