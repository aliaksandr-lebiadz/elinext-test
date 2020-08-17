package com.elinext.booking.service.impl;

import com.elinext.booking.dto.request.RoomRequestDto;
import com.elinext.booking.dto.response.RoomResponseDto;
import com.elinext.booking.entity.Room;
import com.elinext.booking.entity.RoomType;
import com.elinext.booking.exception.EntityNotFoundException;
import com.elinext.booking.exception.ServiceException;
import com.elinext.booking.mapper.RoomMapper;
import com.elinext.booking.repository.RoomRepository;
import com.elinext.booking.service.api.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Override
    public List<String> getRoomTypesValues() {
        return Arrays.stream(RoomType.values())
                .map(RoomType::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomResponseDto> getRoomsByType(RoomType type) {
        List<Room> rooms = type != null
                ? roomRepository.findByType(type)
                : roomRepository.findAll();
        return rooms.stream()
                .map(roomMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoomResponseDto add(RoomRequestDto roomDto) throws ServiceException {
        Room room = roomMapper.mapToEntity(roomDto);
        if(room.getType() == null) {
            throw new EntityNotFoundException("Room type " + roomDto.getTypeValue() + " doesn't exist!");
        }
        Room savedRoom = roomRepository.save(room);
        return roomMapper.mapToDto(savedRoom);
    }
}
