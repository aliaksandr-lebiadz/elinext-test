package com.elinext.booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class RoomResponseDto {

    private Long id;
    private String name;
    private List<ReservationDto> activeReservations;

    @Data
    @AllArgsConstructor
    public static class ReservationDto {
        private LocalDateTime startDate;
        private LocalDateTime endDate;
    }

}
