package com.roomify.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelResponse {

	private Long id;
	private String name;
	private String country;
	private String city;
	private Double price;
	private String imageUrl;
	
}
