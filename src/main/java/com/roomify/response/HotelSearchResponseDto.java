package com.roomify.response;

import java.util.List;

import com.roomify.dto.RoomSearchDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelSearchResponseDto {

    private Long hotelId;
    private String hotelName;
    private String location;
    private String hotelImage;

    private List<RoomSearchDto> rooms;
    private List<ReviewResponse> reviews;
    private Double avgRating;
    private Long totalReviews;
}