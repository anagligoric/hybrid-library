package com.hybridlibrary.services;

import java.util.List;

public interface AbstractService<T, K> {
    List<T> findAll();

    T getOne(K id);

    T update(T t);

    T create(T t);

    T delete(K id);

    boolean existById(K id);
}
