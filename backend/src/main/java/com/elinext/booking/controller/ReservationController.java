package com.elinext.booking.controller;

import com.elinext.booking.dto.ReservationDto;
import com.elinext.booking.exception.ServiceException;
import com.elinext.booking.service.api.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDto add(@Valid @RequestBody ReservationDto reservationDto) throws ServiceException {
        return reservationService.add(reservationDto);
    }

    @PatchMapping("/{id}/cancel")
    public ReservationDto cancel(@PathVariable long id) throws ServiceException {
        return reservationService.cancelById(id);
    }

}
