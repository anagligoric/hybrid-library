package com.hybridlibrary.services.serviceimpl;

import com.hybridlibrary.dtos.UserDto;
import com.hybridlibrary.models.User;
import com.hybridlibrary.repositories.UserRepository;
import com.hybridlibrary.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConversionService conversionService;

    @Override
    public List<UserDto> findAll() {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user :
                userRepository.findAll()) {
            userDtoList.add(conversionService.convert(user, UserDto.class));
        }
        log.info("Users fetched");
        return userDtoList;
    }

    @Override
    public UserDto getOne(Long id) {
        log.info("User with id {} is listed", id);
        return conversionService.convert(userRepository.getOne(id), UserDto.class);
    }

    @Override
    public List<UserDto> getByUsername(String username) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user :
                userRepository.findByUsernameContainingIgnoreCase(username)) {
            userDtoList.add(conversionService.convert(user, UserDto.class));
        }
        log.info("Books which title contains '{}' are listed", username);
        return userDtoList;
    }

    @Override
    public UserDto update(UserDto userDto) {
        User user = conversionService.convert(userDto, User.class);
        log.info("{} is updated.", user);
        return conversionService.convert(userRepository.save(user), UserDto.class);
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = conversionService.convert(userDto, User.class);
        log.info("{} is added.", user);
        return conversionService.convert(userRepository.save(user), UserDto.class);
    }

    @Override
    public UserDto delete(Long id) {
        User user = userRepository.getOne(id);
        log.info("User with id {} is deleted.", id);
        userRepository.deleteById(id);
        return conversionService.convert(user, UserDto.class);
    }

    @Override
    public boolean existById(Long id) {
        return userRepository.existsById(id);
    }

    public UserDto createPassword(Long id, String password) {
        User user = userRepository.getOne(id);
        user.setPassword(password);
        update(conversionService.convert(user, UserDto.class));
        return conversionService.convert(user, UserDto.class);
    }

}
