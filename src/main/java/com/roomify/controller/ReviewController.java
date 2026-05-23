package com.roomify.controller;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.roomify.dto.LogedUserDTO;
import com.roomify.dto.ReviewDto;
import com.roomify.service.ReviewService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

	private final ReviewService reviewService;
	
	@PostMapping("/add")
	public String addReview(@ModelAttribute ReviewDto reviewDto, HttpSession session,RedirectAttributes redirectAttributes) {
		LogedUserDTO logedUserDTO=(LogedUserDTO)session.getAttribute("user");
		if(logedUserDTO==null) {
			return "redirect:/login";
		}
		reviewDto.setUserName(logedUserDTO.getName());
		
		reviewService.addReview(reviewDto,logedUserDTO.getToken());
		
		// success message
	    redirectAttributes.addFlashAttribute("msg", "Review added successfully!");

	    return "redirect:/hotel/details?hotelId=" + reviewDto.getHotelId()
        + "&checkIn=" + reviewDto.getCheckIn()
        + "&checkOut=" + reviewDto.getCheckOut();
	}
	
}
