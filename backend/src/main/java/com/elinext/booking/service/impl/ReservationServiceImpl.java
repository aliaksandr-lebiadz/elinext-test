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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final ReservationMapper reservationMapper;
    private final ReservationValidator reservationValidator;

    @Override
    public ReservationDto add(ReservationDto reservationDto) throws ServiceException {
        long roomId = reservationDto.getRoomId();
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room with id " + roomId + " not found!"));
        long userId = reservationDto.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found!"));
        Reservation reservation = reservationMapper.mapToEntity(reservationDto);
        reservation.setRoom(room);
        reservation.setUser(user);
        if(!reservationValidator.isValid(reservation)) {
            throw new ValidationException("Reservation dates are intersected!");
        }
        Reservation savedReservation = reservationRepository.save(reservation);
        return reservationMapper.mapToDto(reservation);
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
