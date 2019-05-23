package com.hybridlibrary.services;

import com.hybridlibrary.models.User;
import com.hybridlibrary.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;



    public UserService() {
    }

    public Collection<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUser(Integer id){
        return userRepository.getOne(id);
    }

    public Collection<User> getUserByUsername(String username){
        return userRepository.findByUsernameContainingIgnoreCase(username);
    }

    public boolean existById(Integer id){
        return userRepository.existsById(id);
    }

    public User createUser(User user){

        //user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }
    public User updateUser(User user){
        userRepository.save(user);
        return user;
    }
    public void deleteUser(Integer id){
        userRepository.deleteById(id);
    }
}
