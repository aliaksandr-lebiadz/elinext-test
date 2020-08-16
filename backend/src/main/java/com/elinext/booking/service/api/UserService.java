package com.elinext.booking.service.api;

import com.elinext.booking.dto.request.AuthenticationRequestDto;
import com.elinext.booking.dto.response.AuthenticationResponseDto;
import com.elinext.booking.entity.User;
import com.elinext.booking.exception.ServiceException;

public interface UserService {

    void signUp(String username, String password) throws ServiceException;
    AuthenticationResponseDto login(AuthenticationRequestDto authenticationRequestDto) throws ServiceException;
    User findByUsername(String username) throws ServiceException;

}
