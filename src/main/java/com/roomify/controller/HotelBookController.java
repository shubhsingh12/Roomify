package com.roomify.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.roomify.dto.BookingResponseDto;
import com.roomify.dto.LogedUserDTO;
import com.roomify.dto.RoomBookingDto;
import com.roomify.exceptionHandler.RoomNotAvailableException;
import com.roomify.response.ResponseApi;
import com.roomify.service.BookingService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
@RequiredArgsConstructor
@RequestMapping("/booking")
public class HotelBookController {

	private final BookingService bookingService;
	
	@PostMapping("/create")
	public String bookRoom(@ModelAttribute RoomBookingDto bookingDto, HttpSession session, Model m) {
		
		LogedUserDTO logedUserDTO=(LogedUserDTO)session.getAttribute("user");
		if(logedUserDTO==null) {
			return "redirect:/login";
		}
		try {
			ResponseApi<BookingResponseDto> bookingDetails= bookingService.bookMyRoom(bookingDto,logedUserDTO.getToken());
	        return "redirect:/booking/confirmation/" + bookingDetails.getData().getBookingId();

	    } catch (RoomNotAvailableException ex) {
	        m.addAttribute("error", ex.getMessage());
	        return "viewHotel"; 
	    }
		
	}
	@GetMapping("/confirmation/{id}")
	public String bookingConfirmation(@PathVariable Long id, Model m , HttpSession session) {
		
		LogedUserDTO logedUserDTO=(LogedUserDTO)session.getAttribute("user");
		if(logedUserDTO==null) {
			return "redirect:/login";
		}
		
		BookingResponseDto booking = bookingService.getBookingById(id, logedUserDTO.getToken());

	    m.addAttribute("booking", booking);

	    return "booking-confirmation";
	}
	@GetMapping("/myBooking")
	public String getMyBooking(HttpSession session,Model m) {
		LogedUserDTO logedUserDTO=(LogedUserDTO)session.getAttribute("user");
		if(logedUserDTO==null) {
			return "redirect:/login";
		}
		List<BookingResponseDto>bookings=bookingService.getMyBooking(logedUserDTO.getToken(),logedUserDTO.getId());
		m.addAttribute("bookings", bookings);
		return "myBooking";
	}
	@GetMapping("/complete/{bookingId}")
	public String checkOut(@PathVariable Long bookingId,HttpSession session) {
		LogedUserDTO logedUserDTO=(LogedUserDTO)session.getAttribute("user");
		if(logedUserDTO==null) {
			return "redirect:/login";
		}
		ResponseApi<?> result=bookingService.checkOutMyBooking(bookingId,logedUserDTO.getToken());
		
		return "redirect:/dashboard";
	}
	
	
	@PostMapping("/cancel")
	public String cancleBooking(@RequestParam Long bookingId,HttpSession session) {
		LogedUserDTO logedUserDTO=(LogedUserDTO)session.getAttribute("user");
		if(logedUserDTO==null) {
			return "redirect:/login";
		}
		
		bookingService.cancleBooking(bookingId,logedUserDTO.getToken());
		
		return "redirect:/booking/myBooking";
	}
	
}
