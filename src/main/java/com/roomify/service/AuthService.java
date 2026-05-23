package com.roomify.service;

import java.io.IOError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.roomify.dto.LogedUserDTO;
import com.roomify.dto.LoginDTO;
import com.roomify.dto.UpdateProfileDto;
import com.roomify.dto.UserDTO;
import com.roomify.response.ResponseApi;
import com.roomify.response.ResponseApi;

@Service
public class AuthService {

	RestTemplate restTemplate=new RestTemplate();
	
	String URL="http://localhost:2001/user";
	
	public ResponseApi<?> registerUser(UserDTO userDTO) {
		
		String API="/register";
		
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDTO> httpEntity=new HttpEntity<>(userDTO,headers);
		
		ResponseEntity<ResponseApi> responseEntity=
				restTemplate.exchange(
						URL+API,
						HttpMethod.POST,
						httpEntity,
						ResponseApi.class);
		return responseEntity.getBody();
	}

	public ResponseApi<LogedUserDTO> loginRequest(LoginDTO loginDTO) {

	    String API = "/loginRequest";
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<LoginDTO> httpEntity = new HttpEntity<>(loginDTO, headers);
	    ResponseEntity<ResponseApi<LogedUserDTO>> response =
	            restTemplate.exchange(
	                    URL + API,
	                    HttpMethod.POST,
	                    httpEntity,
	                    new ParameterizedTypeReference<ResponseApi<LogedUserDTO>>() {}
	            );

	    return response.getBody();   
	}

	public UserDTO getUser(Long id, String token) {
		String API="/getUser/"+id;
		HttpHeaders headers=new HttpHeaders();
		headers.set("Authorization", "Bearer "+token);
		HttpEntity<?> httpEntity=new HttpEntity<>(id,headers);
		ResponseEntity<UserDTO> responseEntity=restTemplate.exchange(URL+API, HttpMethod.GET,httpEntity,UserDTO.class);
		
		return responseEntity.getBody();
	}

	public ResponseApi<?> updateProfile(UpdateProfileDto updateDTO, String token,MultipartFile img) throws IOException {
		String API="/updateProfile";
		HttpHeaders headers=new HttpHeaders();
		System.out.println(updateDTO);
		headers.set("Authorization", "Bearer "+token);
	    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		    body.add("name", updateDTO.getName());
		    body.add("email", updateDTO.getEmail());
		    body.add("password", updateDTO.getPassword());
		    body.add("phone", updateDTO.getPhone());
		   
		    ByteArrayResource resource = new ByteArrayResource(img.getBytes()) {
		        @Override
		        public String getFilename() {
		            return img.getOriginalFilename();
		        }
		    };
		    body.add("img", resource);
		    HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, headers);
		    System.out.println("aaya  aaya service");
		ResponseEntity<ResponseApi<?>> responseEntity=restTemplate.exchange(URL+API, HttpMethod.POST,httpEntity,new ParameterizedTypeReference<ResponseApi<?>>() {
		});
		return responseEntity.getBody();
	}

	public ResponseApi<?> sendPasswordResetLink(String email) {
		String API="/sendPasswordResetLink";
		HttpHeaders headers=new HttpHeaders();
		HttpEntity<String> httpEntity=new HttpEntity<>(email,headers);
		System.out.println(email+" client");
		ResponseEntity<ResponseApi<?>>responseEntity=restTemplate.exchange(URL+API, HttpMethod.POST,httpEntity,new ParameterizedTypeReference<ResponseApi<?>>() {
		});
		return responseEntity.getBody();
	}

	public ResponseApi<?> verifyUser(String token) {
		String API="/verifyUser";
		HttpHeaders headers=new HttpHeaders();
		headers.set("Authorization", "Bearer "+token);
		HttpEntity<?>httpEntity=new HttpEntity<>(headers);
		ResponseEntity<ResponseApi<?>>responseEntity=restTemplate.exchange(URL+API, HttpMethod.GET,httpEntity,new ParameterizedTypeReference<ResponseApi<?>>() {
		});
		return responseEntity.getBody();
	}

	public ResponseApi<?> resetPassword(String token, String password) {
		String API="/resetPassword";
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> body = new HashMap<>();
		body.put("token", token);
		body.put("password", password);

		HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
		ResponseEntity<ResponseApi<?>>responseEntity=restTemplate.exchange(URL+API, HttpMethod.POST,entity,new ParameterizedTypeReference<ResponseApi<?>>() {
		});
		return responseEntity.getBody();
	}

	
}
