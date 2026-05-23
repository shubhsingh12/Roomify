package com.roomify.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogedUserDTO {
	private Long id;
	private String email;
	private String token;
	private String name;
	private String imageUrl;
	private String role;
}
