package com.onlinecourse.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.onlinecourse.entity.Place;
import com.onlinecourse.entity.Role;
import com.onlinecourse.entity.User;
import com.onlinecourse.service.PlaceService;
import com.onlinecourse.service.RoleService;
import com.onlinecourse.utils.Log;

@Controller
public class LoginController {
	
	private final PlaceService placeService;
	private final RoleService roleService;
	
	public LoginController(PlaceService placeService, RoleService roleService) {
		this.placeService = placeService;
		this.roleService = roleService;
	}
	
	@GetMapping("/Login")
    public String showMyLoginPage(Principal principal) {
		
		return principal == null ? "login" : "redirect:/Home";
    }

	@GetMapping("/Logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}

		Log.info("LOGGING OUT USER: "+auth.getName());
		
		return "redirect:/Login";
	}
	
	@GetMapping("/Register")
    public String addUserForm(Model model) {
        
    	User user = new User();

    	List<Role> rolesList = roleService.findAll();
    	List<Place> placesList = placeService.findAll();
    	
    	user.getRoles().addAll(rolesList);
        
    	model.addAttribute("user", user);
    	model.addAttribute("userImageId", 0);
    	model.addAttribute("rolesList", rolesList);
    	model.addAttribute("placesList", placesList);
    	
    	return "registration-form";
    }
}
