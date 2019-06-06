package com.hybridlibrary.services.serviceimpl;

import com.hybridlibrary.dtos.UserDto;
import com.hybridlibrary.exceptions.NotFoundException;
import com.hybridlibrary.models.User;
import com.hybridlibrary.repositories.UserRepository;
import com.hybridlibrary.services.UserService;
import com.hybridlibrary.utils.ApplicationConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;
    private ConversionService conversionService;

    @Autowired
    public UserServiceImpl(ConversionService conversionService, UserRepository userRepository) {
        this.conversionService = conversionService;
        this.userRepository = userRepository;

    }

    @Override
    public List<UserDto> findAll() {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user :
                userRepository.findAll()) {
            userDtoList.add(conversionService.convert(user, UserDto.class));
        }
        if (CollectionUtils.isEmpty(userDtoList)) {
            throw new NotFoundException("There is no any user.");
        } else {
            log.info("Users fetched");
            return userDtoList;
        }
    }

    @Override
    public UserDto getOne(Long id) {
        if (userRepository.existsById(id)) {
            log.info("User with id {} is listed", id);
            return conversionService.convert(userRepository.getOne(id), UserDto.class);
        } else {
            throw new NotFoundException(ApplicationConstants.USER + id + ApplicationConstants.NOT_FOUND);
        }
    }

    @Override
    public List<UserDto> getByUsername(String username) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user :
                userRepository.findByUsernameContainingIgnoreCase(username)) {
            userDtoList.add(conversionService.convert(user, UserDto.class));
        }
        if (CollectionUtils.isEmpty(userDtoList)) {
            throw new NotFoundException("There is no any user which username contains" + username);
        } else {
            log.info("Users which username contains '{}' are listed", username);
            return userDtoList;
        }

    }

    @Override
    public User findByUsername(String username) {

        User user = userRepository.findByUsername(username);
        if (userRepository.existsById(user.getId())) {
            return user;
        } else {
            throw new NotFoundException("There is no any user with username " + username);
        }
    }

    @Override
    public UserDto update(UserDto userDto) {
        User user = conversionService.convert(userDto, User.class);
        if (userRepository.existsById(userDto.getId())) {
            log.info("{} is updated.", user);
            return conversionService.convert(userRepository.save(user), UserDto.class);
        } else {
            throw new NotFoundException("There is no any user with id " + user.getId());
        }

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
        if (userRepository.existsById(id)) {
            log.info("User with id {} is deleted.", id);
            userRepository.deleteById(id);
            return conversionService.convert(user, UserDto.class);
        } else {
            throw new NotFoundException(ApplicationConstants.USER + id + ApplicationConstants.NOT_FOUND);
        }

    }

    @Override
    public boolean existById(Long id) {
        return userRepository.existsById(id);
    }

    @Transactional
    public UserDto createPassword(Long id, String password) {
        if (userRepository.existsById(id)) {
            User user = userRepository.getOne(id);
            user.setPassword(password);
            UserDto userDto = conversionService.convert(user, UserDto.class);
            userRepository.executeUpdate(password, id);
            return userDto;
        } else {
            throw new NotFoundException(ApplicationConstants.USER + id + ApplicationConstants.NOT_FOUND);
        }
    }
}
