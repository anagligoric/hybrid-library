package com.hybridlibrary.controllers;


import com.hybridlibrary.models.User;
import com.hybridlibrary.services.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class UserRestController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("user")
    public ResponseEntity<Collection<User>> getUsers(){
        Collection<User> users = userServiceImpl.findAll();
        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(users);
        }
    }

    @GetMapping("user/{id}")
    public ResponseEntity<User> getUser(@PathVariable ("id") Long id){
        //User user =
        if(!userServiceImpl.existById(id)){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(userServiceImpl.getOne(id));
        }
    }

    @GetMapping("username/{username}")
    public ResponseEntity<Collection<User>> getUserByUsername(@PathVariable ("username") String username){
        Collection<User> users = userServiceImpl.getByUsername(username);
        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(users);
        }

    }
    @DeleteMapping(value = "user")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id){
        if(!userServiceImpl.existById(id)){
            return ResponseEntity.noContent().build();
        }else{
            userServiceImpl.delete(id);
            return ResponseEntity.ok().build();
        }
    }
    @PostMapping(value="user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        if(userServiceImpl.existById(user.getId())){
            return ResponseEntity.noContent().build();
        }else{
            userServiceImpl.create(user);
            return ResponseEntity.ok(user);
        }

    }
    @PutMapping(value="user")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        if(!userServiceImpl.existById(user.getId())){
            return ResponseEntity.noContent().build();
        }else{
            userServiceImpl.update(user);
            return ResponseEntity.ok(user);
        }

    }


}
