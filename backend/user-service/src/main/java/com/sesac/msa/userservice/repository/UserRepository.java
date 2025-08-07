package com.sesac.msa.userservice.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.sesac.msa.userservice.entity.User;

public interface UserRepository extends Repository<User, Long> {
	Optional<User> findById(Long id);
}
