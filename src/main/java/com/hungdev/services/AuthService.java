package com.hungdev.services;

import com.hungdev.dtos.SignUpRequest;

public interface AuthService {
	void signUp(SignUpRequest signUpRequestDTO);
}
