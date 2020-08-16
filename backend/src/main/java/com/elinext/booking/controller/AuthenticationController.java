package com.elinext.booking.controller;

import com.elinext.booking.dto.request.AuthenticationRequestDto;
import com.elinext.booking.dto.response.AuthenticationResponseDto;
import com.elinext.booking.exception.ServiceException;
import com.elinext.booking.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/login")
    public AuthenticationResponseDto login(@Valid @RequestBody AuthenticationRequestDto requestDto) throws ServiceException {
        return userService.login(requestDto);
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@Valid @RequestBody AuthenticationRequestDto requestDto) throws ServiceException {
        userService.signUp(requestDto.getUsername(), requestDto.getPassword());
    }

}
