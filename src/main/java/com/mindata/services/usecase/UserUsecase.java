package com.mindata.services.usecase;

import com.mindata.domain.inout.LoginDto;
import com.mindata.domain.inout.LoginRequest;
import com.mindata.domain.inout.UserRequest;
import com.mindata.domain.models.UserEntity;
import com.mindata.exception.cases.DuplicatedEmailException;
import com.mindata.exception.cases.UserNotFoundException;
import com.mindata.repositories.UserRepository;
import com.mindata.services.ports.UserCommand;
import com.mindata.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserUsecase implements UserCommand {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public UserEntity register(UserRequest request) throws DuplicatedEmailException {
        Boolean emailExists = userRepository.emailExists(request.getEmail());
        if(emailExists) {
            throw new DuplicatedEmailException();
        }

        UserEntity userEntity = UserEntity.builder()
                .name(request.getName())
                .password(request.getPassword())
                .email(request.getEmail()).build();

        return userRepository.save(userEntity);
    }

    @Override
    public LoginDto login(LoginRequest request) throws UserNotFoundException {
        UserEntity user = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new UserNotFoundException("Invalid Email or Password"));

        return new LoginDto(user.getName(), jwtUtil.getJWTToken(user.getId().toString()));
    }
}
