package com.elinext.booking.service.api;

import com.elinext.booking.dto.request.RoomRequestDto;
import com.elinext.booking.dto.response.RoomResponseDto;
import com.elinext.booking.entity.RoomType;
import com.elinext.booking.exception.ServiceException;

import java.util.List;

public interface RoomService {

    List<String> getRoomTypesValues();
    List<RoomResponseDto> getRoomsByType(RoomType type);
    RoomResponseDto add(RoomRequestDto roomDto) throws ServiceException;

}
