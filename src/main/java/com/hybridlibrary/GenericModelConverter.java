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
        ConvertiblePair[] pairs = new ConvertiblePair[] {
                new ConvertiblePair(Book.class, BookDto.class),
                new ConvertiblePair(BookDto.class, Book.class),
                new ConvertiblePair(BookCopy.class, BookCopyDto.class),
                new ConvertiblePair(BookCopyDto.class, BookCopy.class),
                new ConvertiblePair(BookRental.class, BookRentalDto.class),
                new ConvertiblePair(BookRentalDto.class, BookRental.class),
                new ConvertiblePair(User.class, UserDto.class),
                new ConvertiblePair(UserDto.class, User.class)};
        return ImmutableSet.copyOf(pairs);
    }

    @Override
    public Object convert(Object o, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if(o instanceof Book){
            return convertBook(o);
        }else if(o instanceof BookDto){
            return convertBookDto(o);
        }else if(o instanceof  BookCopy){
            return convertBookCopy(o);
        }else if(o instanceof  BookCopyDto){
            return convertBookCopyDto(o);
        }else if(o instanceof BookRental){
            return convertBookRental(o);
        }else if(o instanceof  BookRentalDto){
            return convertBookRentalDto(o);
        }else if(o instanceof User){
            return convertUser(o);
        }else if(o instanceof UserDto){
            return convertUserDto(o);
        }else{
            return null;
        }
    }
    private static BookDto convertBook(Object o){
        Book book = (Book)o;
        BookDto bookDto = new BookDto();
        bookDto.setAuthor(book.getAuthor());
        bookDto.setLanguage(book.getLanguage());
        bookDto.setRentPeriod(book.getRentPeriod());
        bookDto.setTitle(book.getTitle());
        return bookDto;
    }
    private static Book convertBookDto(Object o){
        BookDto bookDto = (BookDto)o;
        Book book = new Book();
        book.setAuthor(bookDto.getAuthor());
        book.setLanguage(bookDto.getLanguage());
        book.setRentPeriod(bookDto.getRentPeriod());
        book.setTitle(bookDto.getTitle());
        return book;
    }
    private static BookCopyDto convertBookCopy(Object o){
        BookCopy bookCopy = (BookCopy)o;
        BookCopyDto bookCopyDto = new BookCopyDto();
        bookCopyDto.setBook(bookCopy.getBook());
        return bookCopyDto;
    }
    private static BookCopy convertBookCopyDto(Object o){
        BookCopyDto bookCopyDto = (BookCopyDto)o;
        BookCopy bookCopy = new BookCopy();
        bookCopy.setBook(bookCopyDto.getBook());
        return bookCopy;
    }
    private static BookRentalDto convertBookRental(Object o){
        BookRental bookRental = (BookRental)o;
        BookRentalDto bookRentalDto = new BookRentalDto();
        bookRentalDto.setUser(bookRental.getUser());
        bookRentalDto.setBookCopy(bookRental.getBookCopy());
        bookRentalDto.setRentDate(bookRental.getRentDate());
        bookRentalDto.setReturnDate(bookRental.getReturnDate());
        return bookRentalDto;
    }
    private static BookRental convertBookRentalDto(Object o){
        BookRentalDto bookRentalDto = (BookRentalDto)o;
        BookRental bookRental= new BookRental();
        bookRental.setUser(bookRentalDto.getUser());
        bookRental.setBookCopy(bookRentalDto.getBookCopy());
        bookRental.setRentDate(bookRentalDto.getRentDate());
        bookRental.setReturnDate(bookRentalDto.getReturnDate());
        return bookRental;
    }
    private static UserDto convertUser(Object o){
        User user = (User)o;
        UserDto userDto = new UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        return userDto;
    }
    private static User convertUserDto(Object o){
        UserDto userDto = (UserDto)o;
        User user= new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        return user;
    }
}
