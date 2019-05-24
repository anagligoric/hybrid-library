package com.hybridlibrary.controllers;


import com.hybridlibrary.models.User;
import com.hybridlibrary.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("user")
    public ResponseEntity<Collection<User>> getUsers(){
        Collection<User> users = userService.findAll();
        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(users);
        }
    }

    @GetMapping("user/{id}")
    public ResponseEntity<User> getUser(@PathVariable ("id") Long id){
        //User user =
        if(!userService.existById(id)){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(userService.getOne(id));
        }
    }

    @GetMapping("username/{username}")
    public ResponseEntity<Collection<User>> getUserByUsername(@PathVariable ("username") String username){
        Collection<User> users = userService.getByUsername(username);
        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(users);
        }

    }
    @DeleteMapping(value = "user")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id){
        if(userService.existById(id)){
            userService.delete(id);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.noContent().build();

        }
    }
    @PostMapping(value="user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        if(userService.existById(user.getId())){
            return ResponseEntity.noContent().build();
        }else{
            User newUser = userService.create(user);
            return ResponseEntity.ok(newUser);
        }

    }
    @PutMapping(value="user")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        if(userService.existById(user.getId())){
            userService.update(user);
            return ResponseEntity.ok(user);
        }else{
            return ResponseEntity.noContent().build();

        }

    }


}
