package com.hybridlibrary.services;

import com.hybridlibrary.models.User;

import java.util.Collection;


public interface UserService extends AbstractService<User, Long>{

    Collection<User> findAll();
    User getOne(Long id);
    Collection<User> getByUsername(String username);
    User update(User user);
    User create(User user);
    void delete(Long id);
    boolean existById(Long id);
}
