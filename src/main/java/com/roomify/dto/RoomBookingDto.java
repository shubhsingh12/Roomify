package com.roomify.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomBookingDto {
     private Long roomId;
     private LocalDate checkIn;
     private LocalDate checkOut;
     private int  guests;
}
