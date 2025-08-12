package com.sesac.msa.userservice.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sesac.msa.userservice.dto.LoginRequest;
import com.sesac.msa.userservice.dto.LoginResponse;
import com.sesac.msa.userservice.entity.User;
import com.sesac.msa.userservice.repository.UserRepository;
import com.sesac.msa.userservice.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository repository;
	private final JwtTokenProvider jwtTokenProvider;
	private final BCryptPasswordEncoder passwordEncoder;


	@Override
	public User findById(Long id) {
		return repository.findById(id).orElseThrow(
			() -> new RuntimeException("User not found!: " + id)
		);
	}

	@Override
	public LoginResponse login(LoginRequest loginRequest) {
		// 1. 이메일로 사용자 조회
		User user = repository.findByEmail(loginRequest.email())
			.orElseThrow(() -> new RuntimeException("Invalid email or password"));
		// 2. 패스워드 검증
		if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
			throw new RuntimeException("Invalid email or password");
		}
		// 3. JWT 토큰 생성
		String token = jwtTokenProvider.generateToken(user.getEmail(), user.getId());

		// 4. 응답 생성
		return LoginResponse.builder()
			.token(token)
			.userId(user.getId())
			.email(user.getEmail())
			.name(user.getName())
			.build();
	}
}
