package com.roomify.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.roomify.dto.HotelDto;
import com.roomify.dto.HotelSearchDto;
import com.roomify.dto.LogedUserDTO;
import com.roomify.dto.RoomDto;
import com.roomify.response.HotelResponse;
import com.roomify.response.HotelSearchResponseDto;
import com.roomify.response.ResponseApi;
import com.roomify.service.HotelService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequiredArgsConstructor
@RequestMapping("/hotel")
public class HotelController {

	private final HotelService hotelService;
	
	@PostMapping("/addHotel")
	public String addHotel(@ModelAttribute HotelDto hotelDto, HttpSession session, @RequestPart MultipartFile image, RedirectAttributes redirectAttributes) throws IOException {
		LogedUserDTO logedUserDTO=(LogedUserDTO)session.getAttribute("user");
		if(logedUserDTO==null) {
			return "redirect:/login";
		}
		System.out.println(logedUserDTO.getToken()+" Token Check");
		ResponseApi<HotelResponse> responeApi=hotelService.addHotel(hotelDto,logedUserDTO.getToken(),image);
		redirectAttributes.addFlashAttribute("msg", responeApi.getMessage());
		redirectAttributes.addFlashAttribute("success",responeApi.isSuccess());
			return "redirect:/dashboard";
		
	}
	
	@GetMapping("/edit/{id}")
	public String getMethodName(@PathVariable Long id, HttpSession session,Model m) {
		
		LogedUserDTO logedUserDTO=(LogedUserDTO)session.getAttribute("user");
		if(logedUserDTO==null) {
			return "redirect:/login";
		}
		
		HotelDto hotelDto= hotelService.getHotelDetails(id,logedUserDTO.getToken());
		m.addAttribute("hotel", hotelDto);
		
		return "editHotel";
	}
	
	@PostMapping("/update")
	public String updateHotel(@ModelAttribute HotelDto hotelDto, @RequestPart MultipartFile image, HttpSession session, RedirectAttributes redirectAttributes) throws IOException {
		LogedUserDTO logedUserDTO=(LogedUserDTO)session.getAttribute("user");
		
		if(logedUserDTO==null) {
			return "redirect:/login";
		}
		hotelService.updateHotel(hotelDto,logedUserDTO.getToken(),image);
		
		return "redirect:/dashboard";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteHotel(@PathVariable Long id,HttpSession session, RedirectAttributes redirectAttributes) {
		LogedUserDTO logedUserDTO=(LogedUserDTO)session.getAttribute("user");
		
		if(logedUserDTO==null) {
			return "redirect:/login";
		}
		
		hotelService.deleteHotel(id, logedUserDTO.getToken());
		
		return "redirect:/dashboard";
	}
	
	@GetMapping("/search")
	public String searchHotelRequest(@ModelAttribute HotelSearchDto searchDto, HttpSession session, Model m) {
		LogedUserDTO logedUserDTO=(LogedUserDTO)session.getAttribute("user");
		
		if(logedUserDTO==null) {
			return "redirect:/login";
		}
		List<HotelSearchResponseDto>results=hotelService.searchHotels(searchDto, logedUserDTO.getToken());
		m.addAttribute("hotels", results);
		return "viewHotel";
	}
	
	@GetMapping("/searchPage")
	public String searchPage(HttpSession session) {
		LogedUserDTO logedUserDTO=(LogedUserDTO)session.getAttribute("user");
		
		if(logedUserDTO==null) {
			return "redirect:/login";
		}
		return "searchHotel";
	}
	
	@GetMapping("/details")
	public String getHotelDetails(@RequestParam Long hotelId,LocalDate checkIn,LocalDate checkOut,Model m,HttpSession session) {
		LogedUserDTO logedUserDTO=(LogedUserDTO)session.getAttribute("user");
		
		if(logedUserDTO==null) {
			return "redirect:/login";
		}
		
		HotelSearchResponseDto hotelSearchResponseDto=hotelService.getHotel(hotelId, checkIn,checkOut,logedUserDTO.getToken());
		m.addAttribute("hotel", hotelSearchResponseDto);
		return "hotelDetails";
	}
	
	@GetMapping("/viewHotelDetails/{id}")
	public String viewHotelDetails(@PathVariable Long id,HttpSession session, Model m) {
		LogedUserDTO logedUserDTO=(LogedUserDTO)session.getAttribute("user");
		if(logedUserDTO==null) {
			return "redirect:/login";
		}
		HotelSearchResponseDto hotelSearchResponseDto =hotelService.viewHotelDetails(id,logedUserDTO.getToken());
		m.addAttribute("hotel", hotelSearchResponseDto);
		return "viewHotelDetails";
	}
	
}
