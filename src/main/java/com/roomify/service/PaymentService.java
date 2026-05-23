package com.roomify.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.roomify.dto.BookingResponseDto;
import com.roomify.response.ResponseApi;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
	
	RestTemplate restTemplate=new RestTemplate();
	
	String URL= "http://roomifypayment-production.up.railway.app/booking";
	
	public ResponseApi<BookingResponseDto> makePayment(Long bookingId,String token) {
		
		String API="/makePayment/"+bookingId;
		HttpHeaders headers=new HttpHeaders();
		headers.set("Authorization", "Bearer "+token);
		HttpEntity<?> httpEntity=new HttpEntity<>(headers);
		ResponseEntity<ResponseApi<BookingResponseDto>> responseEntity=restTemplate.exchange(URL+API, HttpMethod.GET,httpEntity,new ParameterizedTypeReference<ResponseApi<BookingResponseDto>>() {
		});
		return responseEntity.getBody();
		
	}

}
