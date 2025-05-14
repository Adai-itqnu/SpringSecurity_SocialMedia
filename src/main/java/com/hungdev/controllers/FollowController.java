package com.hungdev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hungdev.entities.User;
import com.hungdev.services.FollowService;
import com.hungdev.services.UserService;

@Controller
@RequestMapping("/follow")
public class FollowController {
	@Autowired
	private FollowService followService;

	@Autowired
	private UserService userService;

	@PostMapping("/")
	public String followUser(@RequestParam("userId") int userId) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			String username = ((UserDetails) principal).getUsername();
			User currentUser = userService.findByUsername(username).orElse(null);
			if (currentUser != null) {
				followService.followUser(currentUser.getId(), userId);
			}
		}
		return "redirect:/home";
	}

	@PostMapping("/unfollow/")
	public String unfollowUser(@RequestParam("userId") int userId) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			String username = ((UserDetails) principal).getUsername();
			User currentUser = userService.findByUsername(username).orElse(null);
			if (currentUser != null) {
				followService.unfollowUser(currentUser.getId(), userId);
			}
		}
		return "redirect:/home";
	}
}
