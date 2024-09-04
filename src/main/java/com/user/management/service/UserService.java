package com.user.management.service;

import java.util.List;

import com.user.management.model.User;

public interface UserService {

 	User createUser(User user);

	void deleteUser(String id);

	List<User> getAllUsers();

	String login(User user);

}
