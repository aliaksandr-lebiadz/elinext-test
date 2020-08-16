package com.elinext.booking.mapper;

import com.elinext.booking.dto.ReservationDto;
import com.elinext.booking.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ReservationMapper {

    private final ModelMapper modelMapper;
    
    public ReservationDto mapToDto(Reservation entity) {
        if(Objects.isNull(entity)) {
            return null;
        }
        ReservationDto dto = modelMapper.map(entity, ReservationDto.class);
        dto.setRoomId(entity.getRoom().getId());
        return dto;
    }
    
    public Reservation mapToEntity(ReservationDto dto) {
        return Objects.nonNull(dto) ? modelMapper.map(dto, Reservation.class) : null;
    }
}
