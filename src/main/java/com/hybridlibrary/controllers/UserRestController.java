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
        Collection<User> users = userService.getAllUsers();
        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(users);
        }
    }

    @GetMapping("user/{id}")
    public ResponseEntity<User> getUser(@PathVariable ("id") Integer id){
        //User user =
        if(!userService.existById(id)){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(userService.getUser(id));
        }
    }

    @GetMapping("username/{username}")
    public ResponseEntity<Collection<User>> getUserByUsername(@PathVariable ("username") String username){
        Collection<User> users = userService.getUserByUsername(username);
        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(users);
        }

    }
    @DeleteMapping(value = "user")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id){
        if(!userService.existById(id)){
            return ResponseEntity.noContent().build();
        }else{
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        }
    }
    @PostMapping(value="user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        if(userService.existById(user.getId())){
            return ResponseEntity.noContent().build();
        }else{
            userService.createUser(user);
            return ResponseEntity.ok(user);
        }

    }
    @PutMapping(value="user")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        if(!userService.existById(user.getId())){
            return ResponseEntity.noContent().build();
        }else{
            userService.updateUser(user);
            return ResponseEntity.ok(user);
        }

    }


}
