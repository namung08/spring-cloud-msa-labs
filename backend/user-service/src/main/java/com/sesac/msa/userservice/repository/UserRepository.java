package com.sesac.msa.userservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sesac.msa.userservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findById(Long id);
}
