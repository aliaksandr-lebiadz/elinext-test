package com.elinext.booking.service.impl;

import com.elinext.booking.dto.ReservationDto;
import com.elinext.booking.entity.Reservation;
import com.elinext.booking.entity.Room;
import com.elinext.booking.entity.User;
import com.elinext.booking.exception.EntityNotFoundException;
import com.elinext.booking.exception.ServiceException;
import com.elinext.booking.exception.ValidationException;
import com.elinext.booking.mapper.ReservationMapper;
import com.elinext.booking.repository.ReservationRepository;
import com.elinext.booking.repository.RoomRepository;
import com.elinext.booking.repository.UserRepository;
import com.elinext.booking.service.api.ReservationService;
import com.elinext.booking.validator.ReservationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private static final Lock LOCK = new ReentrantLock();

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final ReservationMapper reservationMapper;
    private final ReservationValidator reservationValidator;

    @Override
    public ReservationDto add(ReservationDto reservationDto) throws ServiceException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        long roomId = reservationDto.getRoomId();
        Reservation reservation = reservationMapper.mapToEntity(reservationDto);
        LOCK.lock();
        try{
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new EntityNotFoundException("User with username " + username + " not found!"));
            reservation.setUser(user);
            Room room = roomRepository.findById(roomId)
                    .orElseThrow(() -> new EntityNotFoundException("Room with id " + roomId + " not found!"));
            reservation.setRoom(room);
            if(!reservationValidator.isValid(reservation)) {
                throw new ValidationException("Reservation dates are intersected!");
            }
            Reservation savedReservation = reservationRepository.save(reservation);
            return reservationMapper.mapToDto(reservation);
        } finally {
            LOCK.unlock();
        }
    }

    @Override
    public ReservationDto cancelById(long id) throws ServiceException {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation with id " + id + " not found!"));
        reservation.setCanceled(true);
        Reservation savedReservation = reservationRepository.save(reservation);
        return reservationMapper.mapToDto(savedReservation);
    }

}
