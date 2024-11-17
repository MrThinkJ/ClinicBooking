package com.c1se62.clinic_booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    Date timestamp;
    String message;
    String details;
}
