package com.roomify.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingResponseDto {

    private Long bookingId;
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int guests;
    private Double totalPrice;
    private String status;
    private String paymentStatus;
    private String bookingReference;
    private LocalDateTime createdAt;
}