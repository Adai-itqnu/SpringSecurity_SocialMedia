
package com.hungdev.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hungdev.entities.Post;
import com.hungdev.entities.User;
import com.hungdev.entities.UserRole;
import com.hungdev.services.FollowService;
import com.hungdev.services.PostService;
import com.hungdev.services.UserService;

@Controller
public class HomeController {
	@Autowired
	private PostService postService;

	@Autowired
	private UserService userService;

	@Autowired
	private FollowService followService;

	@GetMapping("/home")
	public String home(@RequestParam(name = "keyword", defaultValue = "") String keyword, Model model,
			@AuthenticationPrincipal UserDetails userDetails) {
		if (userDetails == null) {
			return "redirect:/auth/login";
		}

		int userId = ((User) userDetails).getId();
		GrantedAuthority grantedAuthority = (GrantedAuthority) userDetails.getAuthorities().toArray()[0];
		String role = grantedAuthority.getAuthority();

		if (userId == -1) {
			return "redirect:/auth/login";
		}

		int pageIndex = 0;
		int pageSize = 10;

		List<Post> posts = null;

		if (!keyword.equals("")) {

			if (role.equals("ROLE_ADMIN")) {
				posts = postService.searchPost(UserRole.ROLE_ADMIN, keyword, userId);
			} else {
				posts = postService.searchPost(UserRole.ROLE_USER, keyword, userId);
			}
		} else {
			posts = postService.findPagedNewestByFollowings(userId, pageIndex, pageSize);
		}

		List<User> users = userService.findPaged(pageIndex, pageSize, userId);
		List<Integer> followingIds = followService.getFollowingIds(userId);

		model.addAttribute("posts", posts);
		model.addAttribute("users", users);
		model.addAttribute("followingIds", followingIds);
		return "home";
	}

	@GetMapping("/search")
	public String search(@RequestParam("keyword") String keyword, Model model,
			@AuthenticationPrincipal UserDetails userDetails) {
		GrantedAuthority grantedAuthority = (GrantedAuthority) userDetails.getAuthorities().toArray()[0];
		String role = grantedAuthority.getAuthority();
		System.out.println(role);

		int userId = ((User) userDetails).getId();
		List<Post> posts;

		if (role.equals("ROLE_ADMIN")) {
			posts = postService.searchPost(UserRole.ROLE_ADMIN, keyword, userId);
		} else {
			posts = postService.searchPost(UserRole.ROLE_USER, keyword, userId);
		}

		List<User> users = userService.findPaged(0, 10, userId);
		List<Integer> followingIds = followService.getFollowingIds(userId);

		model.addAttribute("posts", posts);
		model.addAttribute("users", users);
		model.addAttribute("followingIds", followingIds);

		return "home";
	}

	@GetMapping("/")
	public String redirectToLogin() {
		return "redirect:/auth/login";
	}

	@GetMapping("/auth")
	public String login() {
		return "redirect:/auth";
	}
}
