package com.elinext.booking.service.impl;

import com.elinext.booking.dto.request.AuthenticationRequestDto;
import com.elinext.booking.dto.response.AuthenticationResponseDto;
import com.elinext.booking.entity.User;
import com.elinext.booking.exception.EntityAlreadyExistsException;
import com.elinext.booking.exception.EntityNotFoundException;
import com.elinext.booking.exception.ServiceException;
import com.elinext.booking.repository.UserRepository;
import com.elinext.booking.security.JwtTokenProvider;
import com.elinext.booking.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor(onConstructor_={@Lazy})
public class UserServiceImpl implements UserService {

    private static final Lock LOCK = new ReentrantLock();

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void signUp(String username, String password) throws ServiceException {
        LOCK.lock();
        try{
            Optional<User> optionalUser = userRepository.findByUsername(username);
            if(optionalUser.isPresent()) {
                throw new EntityAlreadyExistsException("User with username " + username + " already exists!");
            }
            String encodedPassword = passwordEncoder.encode(password);
            User user = new User(username, encodedPassword);
            userRepository.save(user);
        } finally {
            LOCK.unlock();
        }
    }

    @Override
    public AuthenticationResponseDto login(AuthenticationRequestDto authenticationRequestDto) throws ServiceException {
        String username = authenticationRequestDto.getUsername();
        String password = authenticationRequestDto.getPassword();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        User user = findByUsername(username);
        String token = jwtTokenProvider.generateToken(username);
        return new AuthenticationResponseDto(username, token);
    }

    @Override
    public User findByUsername(String username) throws ServiceException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.orElseThrow(
                () -> new EntityNotFoundException("User with username " + username + " not found!")
        );
    }
}
