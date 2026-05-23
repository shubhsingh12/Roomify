package com.roomify.service;

import java.io.IOException;
import java.util.List;

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

import com.roomify.dto.RoomDto;
import com.roomify.response.ResponseApi;
import com.roomify.response.RoomResponse;

@Service
public class RoomService {

	RestTemplate restTemplate=new RestTemplate();
	String URL="http://localhost:2002/room";
	public ResponseApi<RoomResponse> addRoom(RoomDto roomDto, MultipartFile img,String token) throws IOException {
		String API="/addRoom";
		HttpHeaders headers=new HttpHeaders();
		headers.set("Authorization","Bearer "+ token);
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		MultiValueMap<String, Object> valueMap=new LinkedMultiValueMap<String, Object>();
		valueMap.add("type", roomDto.getType());
		valueMap.add("price", roomDto.getPrice());
		valueMap.add("hotelId", roomDto.getHotelId());
		valueMap.add("capacity", roomDto.getCapacity());
		
		  ByteArrayResource resource = new ByteArrayResource(img.getBytes()) {
		        @Override
		        public String getFilename() {
		            return img.getOriginalFilename();
		        }
		    };
		valueMap.add("img", resource);
		HttpEntity<MultiValueMap<String, Object>> httpEntity=new HttpEntity<MultiValueMap<String,Object>>(valueMap,headers);
		
		ResponseEntity<ResponseApi> responseEntity=restTemplate.exchange(URL+API, HttpMethod.POST,httpEntity,ResponseApi.class);
		
		return responseEntity.getBody();
	}
	public List<RoomDto> getAllRoom(String token) {
		String API="/getAllRoom";
		 HttpHeaders headers=new HttpHeaders();
		 headers.set("Authorization", "Bearer " + token);
		 
		 HttpEntity<?> httpEntity=new HttpEntity<>(headers);
		 
		ResponseEntity<List<RoomDto>>responseEntity=restTemplate.exchange(URL+API, HttpMethod.GET,httpEntity,new ParameterizedTypeReference<List<RoomDto>>() {
		}); 
		return responseEntity.getBody();
	}
	public RoomDto editRoomDetails(Long id,String token) {
		 String API="/editRoomDetails/"+id;
		 HttpHeaders headers=new HttpHeaders();
		 headers.set("Authorization", "Bearer " + token);
		 HttpEntity<?> httpEntity=new HttpEntity<>(headers);
		 ResponseEntity<RoomDto> responseEntity=restTemplate.exchange(URL+API, HttpMethod.GET,httpEntity,RoomDto.class);
		 
		return responseEntity.getBody();
	}
	public boolean updateRoom(RoomDto roomDto,String token, MultipartFile img) throws IOException {
		String API="/updateRoom";
		
		 HttpHeaders headers=new HttpHeaders();
		 headers.set("Authorization", "Bearer " + token);
		 headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		 MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		 body.add("type", roomDto.getType());
		 body.add("price", roomDto.getPrice());
		 body.add("hotelId", roomDto.getHotelId());
		 body.add("id", roomDto.getId());
		 body.add("capacity", roomDto.getCapacity());
		 
		 ByteArrayResource resource = new ByteArrayResource(img.getBytes()) {
		        @Override
		        public String getFilename() {
		            return img.getOriginalFilename();
		        }
		    };

		    body.add("img", resource);
		 HttpEntity< MultiValueMap<String, Object>>httpEntity=new HttpEntity<>(body,headers);
		 
		 ResponseEntity<Boolean> responseEntity=restTemplate.exchange(URL+API, HttpMethod.PUT,httpEntity,Boolean.class);
		 
		 
		return responseEntity.getBody();
	}
	public boolean deleteRoom(Long id, String token) {
		String API="/deleteRoom";
		HttpHeaders headers=new HttpHeaders();
		headers.set("Authorization", "Bearer "+token);
		HttpEntity<Long>httpEntity=new HttpEntity<Long>(id,headers);
		ResponseEntity<Boolean>responseEntity=restTemplate.exchange(URL+API, HttpMethod.DELETE,httpEntity,Boolean.class);
		
		return responseEntity.getBody();
	}
	
	
}
