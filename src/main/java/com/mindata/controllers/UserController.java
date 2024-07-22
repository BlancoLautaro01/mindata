package com.mindata.controllers;

import com.mindata.domain.inout.LoginDto;
import com.mindata.domain.inout.LoginRequest;
import com.mindata.domain.inout.UserRequest;
import com.mindata.domain.models.UserEntity;
import com.mindata.exception.cases.DuplicatedEmailException;
import com.mindata.exception.cases.UserNotFoundException;
import com.mindata.services.ports.UserCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserCommand userUsecase;

    @PostMapping
    public ResponseEntity<UserEntity> register(@RequestBody UserRequest userRequest) throws DuplicatedEmailException {
        return new ResponseEntity<>(userUsecase.register(userRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<LoginDto> login(@RequestBody LoginRequest loginRequest) throws UserNotFoundException {
        return new ResponseEntity<>(userUsecase.login(loginRequest), HttpStatus.OK);
    }
}