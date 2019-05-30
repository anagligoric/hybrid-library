package com.hybridlibrary.controllers;


import com.hybridlibrary.dtos.UserDto;
import com.hybridlibrary.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getOne(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<List<UserDto>> getByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.getByUsername(username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.delete(id));
    }

    @PostMapping("")
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.create(userDto));
    }

    @PostMapping("/save-password/{id}")
    public ResponseEntity<UserDto> createPassword(@PathVariable("id") Long id, @RequestBody String password) {
        return ResponseEntity.ok(userService.createPassword(id, password));
    }

    @PutMapping
    public ResponseEntity<UserDto> update(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.update(userDto));
    }
}
