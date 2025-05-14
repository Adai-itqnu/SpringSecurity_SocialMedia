package com.hungdev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hungdev.dtos.SignUpRequest;
import com.hungdev.entities.User;
import com.hungdev.entities.UserRole;
import com.hungdev.repositories.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {
	private PasswordEncoder passwordEncoder;
	private UserRepository userRepository;

	@Autowired
	public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}

	public void signUp(SignUpRequest signUpRequest) {
		userRepository.findByUsername(signUpRequest.getUsername()).ifPresent(existingUser -> {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"User with username " + signUpRequest.getUsername() + " is already exists.");
		});

		String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
		String username = signUpRequest.getUsername();

		User user = new User(username, encodedPassword);
		user.setRole(UserRole.ROLE_USER);
		userRepository.save(user);
	}
}
