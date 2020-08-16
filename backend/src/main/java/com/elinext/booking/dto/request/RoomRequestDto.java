package com.elinext.booking.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RoomRequestDto {

    @NotNull
    private String name;
    private String typeValue;

}
