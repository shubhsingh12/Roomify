package com.roomify.dto;


import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelSearchDto {

    private String location;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String guests;
    private String roomType;
}