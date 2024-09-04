package com.user.management.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.management.model.User;

public interface UserRepository extends JpaRepository<User, String> {

	Optional<User> findByUsername(String username);

}
