package com.hybridlibrary.services;

import java.util.Collection;

public interface AbstractService<T, K> {
    Collection<T> findAll();

    T getOne(K id);

    T update(T t);

    T create(T t);

    void delete(K id);

    boolean existById(K id);
}
