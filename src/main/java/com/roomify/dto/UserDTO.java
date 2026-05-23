package com.roomify.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
	private String name;
	private String email;
	private String password;
	private String phone;
	private String gender;
	private int age;
	private String profileImage;
	
}
