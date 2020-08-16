package com.elinext.booking.validator.impl;

import com.elinext.booking.entity.Reservation;
import com.elinext.booking.validator.ReservationValidator;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReservationValidatorImpl implements ReservationValidator {

    @Override
    public boolean isValid(Reservation reservation) {
        if(!reservation.getStartDate().isBefore(reservation.getEndDate())) {
            return false;
        }
        List<Reservation> userReservations = reservation.getUser().getReservations();
        List<Reservation> roomReservations = reservation.getRoom().getReservations();
        return areReservationsNotIntersected(reservation, userReservations) &&
                areReservationsNotIntersected(reservation, roomReservations);
    }

    private boolean areReservationsNotIntersected(Reservation reservation, List<Reservation> reservations) {
        LocalDateTime startDate = reservation.getStartDate();
        return reservations.stream()
                .noneMatch(r -> !startDate.isBefore(r.getStartDate()) && !startDate.isAfter(r.getEndDate()));
    }

}
