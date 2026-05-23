package com.roomify.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.roomify.dto.LogedUserDTO;
import com.roomify.dto.RoomDto;
import com.roomify.response.ResponseApi;
import com.roomify.response.RoomResponse;
import com.roomify.service.HotelService;
import com.roomify.service.RoomService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

	private final RoomService roomService;
	private final HotelService hotelService;
	
	
	@PostMapping("/addRoom")
	public String addRoom(@ModelAttribute RoomDto roomDto, @RequestPart MultipartFile image, HttpSession session) throws IOException {
		LogedUserDTO logedUserDTO =(LogedUserDTO)session.getAttribute("user");
		if(logedUserDTO==null) {
			return "redirect:/login";
		}
		ResponseApi<RoomResponse> responeApi=roomService.addRoom(roomDto, image, logedUserDTO.getToken());
		
		return "redirect:/dashboard";
	}
	
	@GetMapping("/edit/{id}")
	public String editRoomDetails(@PathVariable Long id,HttpSession session,Model m) {
		LogedUserDTO logedUserDTO=(LogedUserDTO)session.getAttribute("user");
		if(logedUserDTO==null) {
			return "redirect:/login";
		}
		RoomDto roomDto=roomService.editRoomDetails(id,logedUserDTO.getToken());
		m.addAttribute("room", roomDto);
		m.addAttribute("hotels", hotelService.getAllHotel(logedUserDTO.getToken()));
		return "editRoom";
	}
	
	@PostMapping("/update")
	public String updateRoom(@ModelAttribute RoomDto roomDto,@RequestPart MultipartFile image,HttpSession session,RedirectAttributes redirectAttributes) throws IOException {
		
		LogedUserDTO logedUserDTO=(LogedUserDTO)session.getAttribute("user");
		if(logedUserDTO==null) {
			return "redirect:/login";
		}
		boolean flag=roomService.updateRoom(roomDto,logedUserDTO.getToken(),image);
		
		return "redirect:/dashboard";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteRoom(@PathVariable Long id,HttpSession session,RedirectAttributes redirectAttributes) {
		
		LogedUserDTO logedUserDTO=(LogedUserDTO)session.getAttribute("user");
		if(logedUserDTO==null) {
			return "redirect:/login";
		}
		boolean flag=	roomService.deleteRoom(id,logedUserDTO.getToken());
		return "redirect:/dashboard";
	}
	
	@GetMapping("/book/{id}")
	public String bookPage(@PathVariable Long id, Model model) {
	    model.addAttribute("roomId", id);
	    return "roomBooking";
	}
	
}
