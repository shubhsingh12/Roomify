package com.roomify.service;

import java.io.IOException;
import java.time.LocalDate;
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

import com.roomify.dto.HotelDto;
import com.roomify.dto.HotelSearchDto;
import com.roomify.response.HotelResponse;
import com.roomify.response.HotelSearchResponseDto;
import com.roomify.response.ResponseApi;

@Service
public class HotelService {

	
	RestTemplate restTemplate=new RestTemplate();
	
	String URL= "http://roomifyhotel-production.up.railway.app/hotel";

	public ResponseApi<HotelResponse> addHotel(HotelDto hotelDto, String token, MultipartFile img) throws IOException {

	    String API = "/addHotel";
	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", "Bearer " + token);
	    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
	    body.add("name", hotelDto.getName());
	    body.add("city", hotelDto.getCity());
	    body.add("country", hotelDto.getCountry());
	    body.add("address", hotelDto.getAddress());
	   
	    ByteArrayResource resource = new ByteArrayResource(img.getBytes()) {
	        @Override
	        public String getFilename() {
	            return img.getOriginalFilename();
	        }
	    };

	    body.add("img", resource);
	    HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
	    ResponseEntity<ResponseApi> responseEntity =
	            restTemplate.exchange(URL + API, HttpMethod.POST, entity, ResponseApi.class);

	    return responseEntity.getBody();
	}

	public List<HotelDto> getAllHotel(String token) {
		String API="/getAllHotel";
		HttpHeaders headers=new HttpHeaders();
		 headers.set("Authorization", "Bearer " + token);
		 HttpEntity<?>httpEntity=new HttpEntity<>(headers);
		 
		 ResponseEntity<List<HotelDto>> responseEntity=restTemplate.exchange(URL+API, HttpMethod.GET,httpEntity,new ParameterizedTypeReference<List<HotelDto>>() {
		});
		return responseEntity.getBody();
	}

	public HotelDto getHotelDetails(Long id,String token) {
		String API="/getHotelDetails/"+id;
		HttpHeaders headers=new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);
		HttpEntity<?> httpEntity=new HttpEntity(headers);
		ResponseEntity<HotelDto> responseEntity=restTemplate.exchange(URL+API, HttpMethod.GET,httpEntity,HotelDto.class);
		
		return responseEntity.getBody();
	}

	public boolean updateHotel(HotelDto hotelDto, String token,MultipartFile img) throws IOException {
	
	     String API="/update";
		 HttpHeaders headers = new HttpHeaders();
		    headers.set("Authorization", "Bearer " + token);
		    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		    body.add("id", hotelDto.getId());
		    body.add("name", hotelDto.getName());
		    body.add("city", hotelDto.getCity());
		    body.add("country", hotelDto.getCountry());
		    body.add("address", hotelDto.getAddress());
		   
		    ByteArrayResource resource = new ByteArrayResource(img.getBytes()) {
		        @Override
		        public String getFilename() {
		            return img.getOriginalFilename();
		        }
		    };

		    body.add("img", resource);
		    HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
		    
		    ResponseEntity<Boolean> responseEntity=restTemplate.exchange(URL+API, HttpMethod.PUT,entity,Boolean.class);
		    return responseEntity.getBody();
	}

	public boolean deleteHotel(Long id, String token) {
		
		String API="/deleteHotel";
		HttpHeaders headers=new HttpHeaders();
		  headers.set("Authorization", "Bearer " + token);
		  HttpEntity<Long> httpEntity=new HttpEntity<Long>(id,headers);
		  ResponseEntity<Boolean>  responseEntity=restTemplate.exchange(URL+API, HttpMethod.DELETE,httpEntity,Boolean.class);
		  
		return responseEntity.getBody();
	}

	public List<HotelSearchResponseDto> searchHotels(HotelSearchDto searchDto, String token) {
		String API="/searchHotels";
		HttpHeaders headers=new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);
		HttpEntity<HotelSearchDto>httpEntity=new HttpEntity<HotelSearchDto>(searchDto,headers);
		ResponseEntity<List<HotelSearchResponseDto>> responseEntity=restTemplate.exchange(URL+API, HttpMethod.POST,httpEntity,new ParameterizedTypeReference<List<HotelSearchResponseDto>>() {
		});
		return responseEntity.getBody();
	}

	public HotelSearchResponseDto getHotel(Long hotelId,LocalDate checkIn,LocalDate checkOut, String token) {
		
		String API="/getHotel/"+hotelId+"/"+checkIn+"/"+checkOut;
		HttpHeaders headers=new HttpHeaders();
		headers.set("Authorization", "Bearer "+token);
		HttpEntity<?>httpEntity=new HttpEntity<>(headers);
		ResponseEntity<HotelSearchResponseDto>responseEntity=restTemplate.exchange(URL+API, HttpMethod.GET,httpEntity,HotelSearchResponseDto.class);
		
		
		return responseEntity.getBody();
	}

	public HotelSearchResponseDto viewHotelDetails(Long id, String token) {
		String API="/viewHotelDetails/"+id;
		HttpHeaders headers =new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);
		HttpEntity<?>httpEntity=new HttpEntity<>(headers);
		ResponseEntity<HotelSearchResponseDto>responseEntity=restTemplate.exchange(URL+API, HttpMethod.GET,httpEntity,HotelSearchResponseDto.class);
		
		return responseEntity.getBody();
	}
	
	
	
}
