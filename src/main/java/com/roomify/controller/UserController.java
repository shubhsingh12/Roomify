package com.roomify.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.roomify.dto.HotelDto;
import com.roomify.dto.LogedUserDTO;
import com.roomify.dto.LoginDTO;
import com.roomify.dto.RoomDto;
import com.roomify.dto.UpdateProfileDto;
import com.roomify.dto.UserDTO;
import com.roomify.response.ResponseApi;
import com.roomify.service.AuthService;
import com.roomify.service.BookingService;
import com.roomify.service.HotelService;
import com.roomify.service.RoomService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestBody;






@Controller
@RequiredArgsConstructor
public class UserController {

	private final AuthService authService;
	private final HotelService hotelService;
	private final RoomService roomService;	
	private final BookingService bookingService;
	@RequestMapping("/")
	public String welcomePage() {
		return "index";
	}
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	@GetMapping("/signup")
	public String registerPage() {
		return "signup";
	}
	@GetMapping("/logout")
	public String logout(HttpSession session,RedirectAttributes redirectAttributes) {
		session.invalidate();
		redirectAttributes.addFlashAttribute("msg","Thanku for visiting my Page!");
		return "redirect:/login";
	}
	@GetMapping("/forgot-password")
	public String forgotPassword() {
		return "forgetPassword";
	}
	@GetMapping("/reset_password")
	public String reset_password(@RequestParam String token,RedirectAttributes redirectAttributes,Model m) {
		ResponseApi<?> result=authService.verifyUser(token);
		redirectAttributes.addFlashAttribute("msg", result.getMessage());
		if(result.isSuccess()) {
			m.addAttribute("token", token);
			return "reset_password";
		}
	
		return "redirect:/login ";
	}
	
	
	@GetMapping("/dashboard")
	public String dashboard(RedirectAttributes redirectAttributes,Model m,HttpSession session ) {
		LogedUserDTO logedUserDTO=(LogedUserDTO)session.getAttribute("user");
		if(logedUserDTO==null) {
			return "redirect:/login";
		}
		
		if(logedUserDTO.getRole().equals("ROLE_USER")) {
			UserDTO userDTO=authService.getUser(logedUserDTO.getId(),logedUserDTO.getToken());
			m.addAttribute("user", userDTO);
			System.out.println(logedUserDTO.getToken());
			int totalBookings=bookingService.getTotalBooking(logedUserDTO.getToken());
			m.addAttribute("totalBooking", totalBookings);
			return "userDashboard";
			
		}else {
			List<HotelDto> hotleList= hotelService.getAllHotel(logedUserDTO.getToken());
			m.addAttribute("hotels", hotleList);
			m.addAttribute("hotelCount", hotleList.size());
			List<RoomDto> roomList= roomService.getAllRoom(logedUserDTO.getToken());
			m.addAttribute("rooms", roomList);
			m.addAttribute("roomCount", roomList.size());
			return "adminDashboard";
		}
		
		
	}
	
	@GetMapping("/ourProfile")
	public String getOurProfile(HttpSession session,Model m) {
		LogedUserDTO logedUserDTO=(LogedUserDTO)session.getAttribute("user");
		if(logedUserDTO==null) {
			return "redirect:/login";
		}
		UserDTO userDTO=authService.getUser(logedUserDTO.getId(), logedUserDTO.getToken());
		System.out.println(userDTO);
		m.addAttribute("user", userDTO);
		return "userProfile";
	}
	
	
	
	@PostMapping("/register")
	public String register(@ModelAttribute UserDTO userDTO,RedirectAttributes redirectAttributes) {
		
		ResponseApi responeApi=authService.registerUser(userDTO);
		if(responeApi.isSuccess()) {
			redirectAttributes.addFlashAttribute("msg", responeApi.getMessage());
			redirectAttributes.addFlashAttribute("success",true);
		}
		redirectAttributes.addFlashAttribute("msg", responeApi.getMessage());
		redirectAttributes.addFlashAttribute("success",false);
		return "redirect:/login";
	}
	
	@PostMapping("/login")
	public String loginRequest(@ModelAttribute LoginDTO loginDTO,RedirectAttributes redirectAttributes,Model m,HttpSession session) {
		
		
		ResponseApi responeApi=authService.loginRequest(loginDTO);
		if(responeApi.isSuccess()) {
			session.setAttribute("user", responeApi.getData());
			redirectAttributes.addFlashAttribute("msg",responeApi.getMessage());
			redirectAttributes.addFlashAttribute("success",true);
			
			
			return "redirect:/dashboard";
		}
		
		return "redirect:/login";
	}
	
	@PostMapping("/updateProfile")
	public String updateProfile(@ModelAttribute UpdateProfileDto updateDTO,
								RedirectAttributes redirectAttributes, 
								@RequestPart MultipartFile image,
								HttpSession session) throws IOException {
		
		LogedUserDTO logedUserDTO=(LogedUserDTO)session.getAttribute("user");
		if(logedUserDTO==null) {
			return "redirect:/login";
		}
		System.out.println("aaya  aaya cotrl");
		ResponseApi<?> responseApi= authService.updateProfile(updateDTO,logedUserDTO.getToken(), image);
		redirectAttributes.addFlashAttribute("msg",responseApi.getMessage());
		
		return "redirect:/ourProfile";
	}
	
	@PostMapping("/changePassword")
	public String changePassword(@RequestParam String email,RedirectAttributes redirectAttributes) {
		ResponseApi<?> result=authService.sendPasswordResetLink(email);
		
		
			redirectAttributes.addFlashAttribute("msg", result.getMessage());
		
		
		return "redirect:/login";
	}
	
	@PostMapping("/updatePassword")
	public String updatePassword(@RequestParam String token,@RequestParam String password,@RequestParam String confirmPassword,RedirectAttributes redirectAttributes) {
		if(!password.equals(confirmPassword)) {
			return "redirect:/reset_password/?token";
		}
		ResponseApi<?> result=authService.resetPassword(token,password);
		redirectAttributes.addFlashAttribute("msg", result.getMessage());
		
			return "redirect:/login";
		
	}
	
}
