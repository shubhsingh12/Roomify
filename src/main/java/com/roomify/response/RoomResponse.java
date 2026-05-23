package com.roomify.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {

	private String type;
	private Double price;
	private String imageUrl;
	private String hotelName;
	
}
