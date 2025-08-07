package com.sesac.msa.userservice.service;

import com.sesac.msa.userservice.entity.User;

public interface UserService {
	User findById(Long id);
}
