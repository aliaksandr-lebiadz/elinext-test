package com.elinext.booking.validator;

import com.elinext.booking.entity.Reservation;

public interface ReservationValidator {

    boolean isValid(Reservation reservation);

}
