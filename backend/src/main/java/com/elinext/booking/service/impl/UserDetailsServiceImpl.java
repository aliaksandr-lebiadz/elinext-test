package com.elinext.booking.service.impl;

import com.elinext.booking.entity.User;
import com.elinext.booking.exception.ServiceException;
import com.elinext.booking.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            User user = userService.findByUsername(username);
            return new org.springframework.security.core.userdetails.User(username, user.getPassword(), Collections.emptyList());
        } catch (ServiceException e) {
            throw new UsernameNotFoundException(e.getMessage(), e);
        }
    }
}
