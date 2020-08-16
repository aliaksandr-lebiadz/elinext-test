package com.elinext.booking.mapper;

import com.elinext.booking.dto.request.RoomRequestDto;
import com.elinext.booking.dto.response.RoomResponseDto;
import com.elinext.booking.entity.Reservation;
import com.elinext.booking.entity.Room;
import com.elinext.booking.entity.RoomType;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class RoomMapper {
    
    public RoomResponseDto mapToDto(Room room) {
        if(Objects.isNull(room)) {
            return null;
        }
        List<Reservation> reservations = room.getReservations();
        List<RoomResponseDto.ReservationDto> reservationsDto = reservations.stream()
                .map(reservation -> new RoomResponseDto.ReservationDto(reservation.getStartDate(), reservation.getEndDate()))
                .collect(Collectors.toList());
        return new RoomResponseDto(room.getId(), room.getName(), reservationsDto);
    }
    
    public Room mapToEntity(RoomRequestDto roomRequestDto) {
        if(Objects.isNull(roomRequestDto)) {
            return null;
        }
        String roomTypeValue = roomRequestDto.getTypeValue();
        RoomType roomType = RoomType.getRoomType(roomTypeValue);
        return new Room(roomRequestDto.getName(), roomType);
    }
}
