package com.elinext.booking.exception.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ApiError {

    private List<String> messages;

}
