package com.hybridlibrary.services;

import com.hybridlibrary.dtos.UserDto;

import java.util.List;


public interface UserService extends AbstractService<UserDto, Long> {

    List<UserDto> findAll();

    UserDto getOne(Long id);

    List<UserDto> getByUsername(String username);

    UserDto update(UserDto userDto);

    UserDto create(UserDto userDto);

    UserDto delete(Long id);

    boolean existById(Long id);

    UserDto createPassword(Long id, String password);
}
