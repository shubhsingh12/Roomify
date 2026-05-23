package com.roomify.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.roomify.dto.BookingResponseDto;
import com.roomify.dto.LogedUserDTO;
import com.roomify.response.ResponseApi;
import com.roomify.service.PaymentService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

	private final PaymentService paymentService;
	
	@PostMapping("/pay")
	public String makePayment(@RequestParam Long bookingId,HttpSession session) {
		
		LogedUserDTO logedUserDTO=(LogedUserDTO)session.getAttribute("user");
		if(logedUserDTO==null) {
			return "redirect:/login";
		}
		
		ResponseApi<BookingResponseDto> bookingResponseDto=	paymentService.makePayment(bookingId,logedUserDTO.getToken());
		
		return "redirect:/booking/myBooking";
	}
	
}
