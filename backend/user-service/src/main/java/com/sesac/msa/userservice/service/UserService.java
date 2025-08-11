package com.sesac.msa.userservice.service;

import com.sesac.msa.userservice.dto.LoginRequest;
import com.sesac.msa.userservice.dto.LoginResponse;
import com.sesac.msa.userservice.entity.User;

public interface UserService {
	User findById(Long id);

	LoginResponse login(LoginRequest loginRequest);
}
