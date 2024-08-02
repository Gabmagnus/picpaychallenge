package com.gabma.picpay_challenge.services;

import com.gabma.picpay_challenge.domain.user.User;
import com.gabma.picpay_challenge.domain.user.UserType;
import com.gabma.picpay_challenge.repositories.UserRepository;
import com.gabma.picpay_challenge.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;



    public User createUser(UserDTO data) {
        User newUser = new User(data);
        this.saveUser(newUser);

        return newUser;
    }

    public User findUserById(Long id) throws Exception {
        return repository.findUserById(id).orElseThrow(() -> new Exception("Usuario invalido")) ;
    }

    public void saveUser(User user) {
        this.repository.save(user);
    }

    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users =  this.repository.findAll();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
