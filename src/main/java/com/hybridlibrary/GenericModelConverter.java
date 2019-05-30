package com.hybridlibrary;

import com.google.common.collect.ImmutableSet;
import com.hybridlibrary.dtos.BookCopyDto;
import com.hybridlibrary.dtos.BookDto;
import com.hybridlibrary.dtos.BookRentalDto;
import com.hybridlibrary.dtos.UserDto;
import com.hybridlibrary.models.Book;
import com.hybridlibrary.models.BookCopy;
import com.hybridlibrary.models.BookRental;
import com.hybridlibrary.models.User;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.Set;


public class GenericModelConverter implements GenericConverter {


    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        ConvertiblePair[] pairs = new ConvertiblePair[]{
                new ConvertiblePair(Book.class, BookDto.class),
                new ConvertiblePair(BookDto.class, Book.class),
                new ConvertiblePair(BookCopy.class, BookCopyDto.class),
                new ConvertiblePair(BookCopyDto.class, BookCopy.class),
                new ConvertiblePair(BookRental.class, BookRentalDto.class),
                new ConvertiblePair(BookRentalDto.class, BookRental.class),
                new ConvertiblePair(User.class, UserDto.class),
                new ConvertiblePair(UserDto.class, User.class)
        };
        return ImmutableSet.copyOf(pairs);
    }

    @Override
    public Object convert(Object o, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (o instanceof Book) {
            return convertBook(o);
        } else if (o instanceof BookDto) {
            return convertBookDto(o);
        } else if (o instanceof BookCopy) {
            return convertBookCopy(o);
        } else if (o instanceof BookCopyDto) {
            return convertBookCopyDto(o);
        } else if (o instanceof BookRental) {
            return convertBookRental(o);
        } else if (o instanceof BookRentalDto) {
            return convertBookRentalDto(o);
        } else if (o instanceof User) {
            return convertUser(o);
        } else if (o instanceof UserDto) {
            return convertUserDto(o);
        } else {
            throw new IllegalArgumentException("Conversion arguments does not match to any converter");
        }
    }

    private static BookDto convertBook(Object o) {
        Book book = (Book) o;
        return BookDto.builder()
                .id(book.getId())
                .author(book.getAuthor())
                .language(book.getLanguage())
                .rentPeriod(book.getRentPeriod())
                .title(book.getTitle()).build();

    }

    private static Book convertBookDto(Object o) {
        BookDto bookDto = (BookDto) o;
        return Book.builder()
                .id(bookDto.getId())
                .author(bookDto.getAuthor())
                .language(bookDto.getLanguage())
                .rentPeriod(bookDto.getRentPeriod())
                .title(bookDto.getTitle()).build();
    }

    private static BookCopyDto convertBookCopy(Object o) {
        BookCopy bookCopy = (BookCopy) o;
        return BookCopyDto.builder()
                .id(bookCopy.getId())
                .bookId(bookCopy.getBook().getId())
                .rentDate(bookCopy.getRentDate())
                .rented(bookCopy.getRented())
                .userId(bookCopy.getUser() == null ? null : bookCopy.getUser().getId())
                .build();

    }

    private static BookCopy convertBookCopyDto(Object o) {
        BookCopyDto bookCopyDto = (BookCopyDto) o;
        return BookCopy.builder()
                .id(bookCopyDto.getId())
                .rentDate(bookCopyDto.getRentDate())
                .rented(bookCopyDto.isRented())
                .build();

    }

    private static BookRentalDto convertBookRental(Object o) {
        BookRental bookRental = (BookRental) o;
        return BookRentalDto.builder()
                .id(bookRental.getId())
                .returnDate(bookRental.getReturnDate())
                .bookCopyId(bookRental.getBookCopy().getId())
                .userId(bookRental.getUser().getId())
                .bookId(bookRental.getBook().getId())
                .build();

    }

    private static BookRental convertBookRentalDto(Object o) {
        BookRentalDto bookRentalDto = (BookRentalDto) o;
        return BookRental.builder()
                .id(bookRentalDto.getId())
                .returnDate(bookRentalDto.getReturnDate())
                .build();

    }

    private static UserDto convertUser(Object o) {
        User user = (User) o;
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();

    }

    private static User convertUserDto(Object o) {
        UserDto userDto = (UserDto) o;
        return User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .build();
    }
}
