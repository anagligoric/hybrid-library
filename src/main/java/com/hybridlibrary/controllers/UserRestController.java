package com.hybridlibrary.controllers;


import com.hybridlibrary.dtos.UserDto;
import com.hybridlibrary.models.User;
import com.hybridlibrary.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private ConversionService conversionService;

    @GetMapping("user")
    public ResponseEntity<List<UserDto>> findAll(){
        Collection<User> users = userService.findAll();
        List<UserDto> userList = new ArrayList<>();
        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            Iterator<User> it = users.iterator();
            while(it.hasNext()){
                userList.add(conversionService.convert(it.next(), UserDto.class));
            }
            return ResponseEntity.ok(userList);
        }
    }

    @GetMapping("user/{id}")
    public ResponseEntity<UserDto> getOne(@PathVariable ("id") Long id){
        //User user =
        if(!userService.existById(id)){
            return ResponseEntity.noContent().build();
        }else{
            UserDto userDto = conversionService.convert(userService.getOne(id), UserDto.class);
            return ResponseEntity.ok(userDto);
        }
    }

    @GetMapping("username/{username}")
    public ResponseEntity<List<UserDto>> getByUsername(@PathVariable ("username") String username){
        Collection<User> users = userService.getByUsername(username);
        List<UserDto>  userList= new ArrayList<>();
        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            Iterator<User> it = users.iterator();
            while(it.hasNext()){
                userList.add(conversionService.convert(it.next(), UserDto.class));
            }
            return ResponseEntity.ok(userList);
        }

    }
    @DeleteMapping(value = "user")
    public ResponseEntity<UserDto> delete(@PathVariable("id") Long id){
        if(userService.existById(id)){
            UserDto userDto = conversionService.convert(userService.getOne(id), UserDto.class);
            userService.delete(id);
            return ResponseEntity.ok(userDto);
        }else{
            return ResponseEntity.noContent().build();

        }
    }
    @PostMapping(value="user")
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto){

            User user = conversionService.convert(userDto, User.class);
            userService.create(user);
            return ResponseEntity.ok(userDto);
    }
    @PutMapping(value="user")
    public ResponseEntity<UserDto> update(@RequestBody UserDto userDto){
        User user = conversionService.convert(userDto, User.class);
        if(userService.existById(user.getId())){
            userService.update(user);
            return ResponseEntity.ok(userDto);
        }else{
            return ResponseEntity.noContent().build();

        }

    }


}
