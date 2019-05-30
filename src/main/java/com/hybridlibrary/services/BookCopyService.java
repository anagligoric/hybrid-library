package com.hybridlibrary.services;

import com.hybridlibrary.dtos.BookCopyDto;

import java.util.List;

public interface BookCopyService extends AbstractService<BookCopyDto, Long> {

    List<BookCopyDto> findAll();

    BookCopyDto getOne(Long id);

    List<BookCopyDto> getByBook(Long id);

    BookCopyDto update(BookCopyDto bookCopyDto);

    BookCopyDto create(BookCopyDto bookCopyDto, Long id);

    BookCopyDto delete(Long id);

    boolean existById(Long id);

    BookCopyDto findByBookAndId(Long bookId, Long id);

    List<BookCopyDto> overdueBookReturns();


}
