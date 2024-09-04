package com.user.management.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.user.management.exception.ResourceAlreadyExistException;
import com.user.management.model.RoleName;
import com.user.management.model.Roles;
import com.user.management.model.User;
import com.user.management.repo.RolesRepository;
import com.user.management.service.UserService;

@Component
public class StartupDataLoader implements CommandLineRunner {

	@Autowired
	private UserService userService;

	@Autowired
	private RolesRepository rolesRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		List<Roles> roles = List.of(
				// inserting agency roles				
				Roles.builder().roleName(RoleName.ADMIN).build(),
				Roles.builder().roleName(RoleName.USER).build());
			if(!rolesRepository.existsByRoleName(RoleName.ADMIN)) {
				rolesRepository.saveAll(roles);
			}
						
		User user = new User();
		user.setUsername("Bonoboz");
		user.setPassword("123456");
		Roles role = new Roles(RoleName.ADMIN);
		user.setRoleId(role);
		try {
		userService.createUser(user);
		}
		catch(ResourceAlreadyExistException e) {
			
		}
	}
	
}
