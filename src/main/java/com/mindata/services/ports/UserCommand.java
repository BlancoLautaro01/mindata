package com.mindata.services.ports;

import com.mindata.domain.inout.LoginDto;
import com.mindata.domain.inout.LoginRequest;
import com.mindata.domain.inout.UserRequest;
import com.mindata.domain.models.UserEntity;
import com.mindata.exception.cases.DuplicatedEmailException;
import com.mindata.exception.cases.UserNotFoundException;

public interface UserCommand {

    UserEntity register(UserRequest request) throws DuplicatedEmailException;
    LoginDto login(LoginRequest request) throws UserNotFoundException;
}
