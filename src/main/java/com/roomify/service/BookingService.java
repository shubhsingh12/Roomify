package com.roomify.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.roomify.dto.BookingResponseDto;
import com.roomify.dto.RoomBookingDto;
import com.roomify.exceptionHandler.RoomNotAvailableException;
import com.roomify.response.ResponseApi;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {
 
			RestTemplate restTemplate=new RestTemplate();
			
			String URL= "http://roomifybooking-production.up.railway.app/booking";

			public ResponseApi<BookingResponseDto> bookMyRoom(RoomBookingDto bookingDto,String token) {
			try {
				String API ="/bookMyRoom";
				HttpHeaders headers=new HttpHeaders();
				headers.set("Authorization","Bearer "+token);
				HttpEntity<RoomBookingDto> httpEntity=new HttpEntity<>(bookingDto,headers);
				
				ResponseEntity<ResponseApi<BookingResponseDto>> responseEntity=restTemplate.exchange(URL+API, HttpMethod.POST,httpEntity,new ParameterizedTypeReference<ResponseApi<BookingResponseDto>>() {
				});
				
				return responseEntity.getBody();
			}catch (HttpClientErrorException ex) {
				 String responseBody = ex.getResponseBodyAsString();

				    
				    System.out.println(responseBody);

				    throw new RoomNotAvailableException("Room not available for selected dates");
			}
			}

			public BookingResponseDto getBookingById(Long id,String token) {
				String API="/getBookingDetails/"+id;
				
				HttpHeaders headers=new HttpHeaders();
				headers.set("Authorization","Bearer "+token);
				HttpEntity<?>httpEntity =new HttpEntity<>(headers);
				ResponseEntity<BookingResponseDto> responseEntity=restTemplate.exchange(URL+API, HttpMethod.GET,httpEntity,BookingResponseDto.class);
				
					return responseEntity.getBody();
			}

			public List<BookingResponseDto> getMyBooking(String token, Long id) {
				String API="/getMyBooking/"+id;
				HttpHeaders headers=new HttpHeaders();
				headers.set("Authorization","Bearer "+token);
				HttpEntity<?> httpEntity=new HttpEntity<>(headers);
				
				ResponseEntity<List> responseEntity=restTemplate.exchange(URL+API, HttpMethod.GET,httpEntity,List.class);
				
				return responseEntity.getBody();
			}

			public void cancleBooking(Long bookingId, String token) {
				String API="/cancleBooking/"+bookingId;
				HttpHeaders headers=new HttpHeaders();
				headers.set("Authorization","Bearer "+token);
				HttpEntity<?>httpEntity =new HttpEntity<>(headers);
				ResponseEntity<Void> responseEntity=restTemplate.exchange(URL+API, HttpMethod.GET,httpEntity,Void.class);
				
				
			}

			public ResponseApi<?> checkOutMyBooking(Long bookingId, String token) {
				String API="/checkOutMyBooking/"+bookingId;
				HttpHeaders headers=new HttpHeaders();
				headers.set("Authorization", "Bearer "+token);
				HttpEntity<?> httpEntity=new HttpEntity<>(headers);
				ResponseEntity<ResponseApi<?>> responseEntity=restTemplate.exchange(URL+API, HttpMethod.GET,httpEntity,new ParameterizedTypeReference<ResponseApi<?>>() {
				});
				return  responseEntity.getBody();
			}

			public int getTotalBooking(String token) {
				String API="/getTotalBooking";
				HttpHeaders headers=new HttpHeaders();
				headers.set("Authorization", "Bearer "+token);
				HttpEntity<?> httpEntity=new HttpEntity<>(headers);
				ResponseEntity<Integer> responseEntity=restTemplate.exchange(URL+API, HttpMethod.GET,httpEntity,Integer.class);
				return responseEntity.getBody();
			}
}
