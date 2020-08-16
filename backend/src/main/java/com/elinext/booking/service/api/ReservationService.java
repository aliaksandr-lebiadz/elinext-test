package com.elinext.booking.service.api;

import com.elinext.booking.dto.ReservationDto;
import com.elinext.booking.exception.ServiceException;

public interface ReservationService {

    ReservationDto add(ReservationDto reservationDto) throws ServiceException;
    ReservationDto cancelById(long id) throws ServiceException;

}
