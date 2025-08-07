package com.sesac.msa.userservice.service;

import org.springframework.stereotype.Service;

import com.sesac.msa.userservice.entity.User;
import com.sesac.msa.userservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository repository;

	@Override
	public User findById(Long id) {
		return repository.findById(id).orElseThrow(
			() -> new RuntimeException("User not found!: " + id)
		);
	}
}
