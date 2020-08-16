package com.elinext.booking.dto;

import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ReservationDto {

    @NotNull
    private String name;
    private String description;
    @NotNull
    @Future
    private LocalDateTime startDate;
    @NotNull
    @Future
    private LocalDateTime endDate;
    private boolean canceled;
    private long roomId;

}
