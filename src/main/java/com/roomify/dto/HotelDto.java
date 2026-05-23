package com.roomify.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelDto {

	private Long id;
	private String name;
	private String country;
	private String city;
	private String address;
	private String imageUrl;
}
