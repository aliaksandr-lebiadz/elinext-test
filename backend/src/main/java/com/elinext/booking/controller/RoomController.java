package com.elinext.booking.controller;

import com.elinext.booking.dto.request.RoomRequestDto;
import com.elinext.booking.dto.response.RoomResponseDto;
import com.elinext.booking.entity.RoomType;
import com.elinext.booking.exception.ServiceException;
import com.elinext.booking.service.api.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public List<RoomResponseDto> getRooms(@RequestParam(name = "type", required = false) String roomTypeValue) {
        RoomType roomType = RoomType.getRoomType(roomTypeValue);
        return roomService.getRoomsByType(roomType);
    }

    @GetMapping("/types")
    public List<String> getRoomTypesValues() {
        return roomService.getRoomTypesValues();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoomResponseDto add(@Valid @RequestBody RoomRequestDto requestDto) throws ServiceException {
        return roomService.add(requestDto);
    }

}
