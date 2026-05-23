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
public class ReviewDto {

	
	private Long hotelId;
	private int rating;
	private String comment;
	private String userName;
	private LocalDate checkIn;
	private LocalDate checkOut;
}
