package com.hybridlibrary.services;

import com.hybridlibrary.dtos.BookDto;

import java.util.List;


public interface BookService extends AbstractService<BookDto, Long> {

    List<BookDto> findAll();

    BookDto getOne(Long id);

    List<BookDto> getByTitle(String title);

    List<BookDto> getByAuthor(String author);

    BookDto update(BookDto bookDto);

    BookDto create(BookDto book);

    BookDto delete(Long id);

    boolean existById(Long id);


}
