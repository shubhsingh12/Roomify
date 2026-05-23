package com.roomify.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomSearchDto {

    private Long roomId;
    private String roomType;
    private Double price;
    private String roomImage;
    private boolean available;
    private boolean showCheckout;
    private Long bookingId;
}