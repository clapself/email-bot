/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clapself.notif.controller;

import com.clapself.notif.entities.User;
import com.clapself.notif.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping( "/api" )
@CrossOrigin(origins="http://localhost:3000")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getUsers(){

        return this.userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUsersById(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
        return ResponseEntity.ok().body(user);
    }


    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }


    @PutMapping("/users")
    public ResponseEntity<User> updateUser(
            @RequestBody User user)
            throws ResourceNotFoundException {
        user.getId();
        user.setEmail(user.getEmail());
        user.setName(user.getName());
        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }


    @DeleteMapping("/user/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));

        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @DeleteMapping(value="/users/{ids}")
    public ResponseEntity<?> deleteByIds(@PathVariable("ids") List<String> ids){
        ids.forEach(d->{
            if(userRepository.existsById((long) Integer.parseInt(d))){
                userRepository.deleteById((long) Integer.parseInt(d));
            }
        });
        return ResponseEntity.ok().body("Users has been removed");
    }


}
