package com.roomify.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.roomify.dto.ReviewDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	
	RestTemplate restTemplate=new RestTemplate();
	
	String URL= "http://localhost:2005/review";
	
	
	public void addReview(ReviewDto reviewDto, String token) {
		
		String API="/addReview";
		HttpHeaders headers=new HttpHeaders();
		headers.set("Authorization", "Bearer "+token);
		System.out.println(token);
		
		HttpEntity<ReviewDto> httpEntity=new HttpEntity<>(reviewDto,headers);
		
		ResponseEntity<Void>responseEntity=restTemplate.exchange(URL+API, HttpMethod.POST,httpEntity,Void.class);
		
	}

}
