package com.hybridlibrary.controllers;


import com.hybridlibrary.dtos.UserDto;
import com.hybridlibrary.models.User;
import com.hybridlibrary.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@RestController
@Slf4j
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private ConversionService conversionService;


    @GetMapping("user")
    public ResponseEntity<List<UserDto>> findAll() {
        Collection<User> users = userService.findAll();
        List<UserDto> userList = new ArrayList<>();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            for (User user :
                    users) {
                userList.add(conversionService.convert(user, UserDto.class));

            }
            log.info("Users fetched.");

            return ResponseEntity.ok(userList);
        }
    }

    @GetMapping("user/{id}")
    public ResponseEntity<UserDto> getOne(@PathVariable("id") Long id) {
        if (!userService.existById(id)) {
            log.info("User with id {} is listed.", id);
            return ResponseEntity.noContent().build();
        } else {
            UserDto userDto = conversionService.convert(userService.getOne(id), UserDto.class);
            return ResponseEntity.ok(userDto);
        }
    }

    @GetMapping("username/{username}")
    public ResponseEntity<List<UserDto>> getByUsername(@PathVariable("username") String username) {
        Collection<User> users = userService.getByUsername(username);
        List<UserDto> userList = new ArrayList<>();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            for (User user :
                    users) {
                userList.add(conversionService.convert(user, UserDto.class));

            }
            log.info("Users which username contains '{}' are listed.", username);

            return ResponseEntity.ok(userList);
        }

    }

    @DeleteMapping(value = "user")
    public ResponseEntity<UserDto> delete(@PathVariable("id") Long id) {
        if (userService.existById(id)) {
            UserDto userDto = conversionService.convert(userService.getOne(id), UserDto.class);
            userService.delete(id);
            log.info("User with id {} is deleted.", id);
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.noContent().build();

        }
    }

    @PostMapping(value = "user")
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        try {
            User user = conversionService.convert(userDto, User.class);
            userService.create(user);
            return ResponseEntity.ok(userDto);
        } catch (ConstraintViolationException e) {
            log.error("Error occurred {}", e.toString(), e);
            return ResponseEntity.badRequest().build();
        }


    }

    @PutMapping(value = "user")
    public ResponseEntity<UserDto> update(@RequestBody User user) {
        try {
            if (userService.existById(user.getId())) {
                userService.update(user);
                UserDto userDto = conversionService.convert(user, UserDto.class);
                return ResponseEntity.ok(userDto);
            } else {
                return ResponseEntity.noContent().build();

            }
        } catch (ConstraintViolationException e) {
            log.error("Error occurred {}", e.toString(), e);
            return ResponseEntity.badRequest().build();
        }


    }


}
