package com.gabma.picpay_challenge.controllers;

import com.gabma.picpay_challenge.domain.user.User;
import com.gabma.picpay_challenge.services.UserService;
import com.gabma.picpay_challenge.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO user) {
        User newUser = this.userService.createUser(user);

        return new ResponseEntity<>(newUser, HttpStatus.OK);

    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return this.userService.getAllUsers();
    }
}
