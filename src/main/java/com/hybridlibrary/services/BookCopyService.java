package com.hybridlibrary.services;

import com.hybridlibrary.models.BookCopy;

import java.util.Collection;

public interface BookCopyService extends AbstractService<BookCopy, Long>{

    Collection<BookCopy> findAll();
    BookCopy getOne(Long id);
    Collection<BookCopy> getByBook(Long id);
    BookCopy update(BookCopy bookCopy);
    BookCopy create(BookCopy bookCopy);
    void delete(Long id);
    boolean existById(Long id);


}
