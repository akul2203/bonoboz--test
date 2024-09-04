package com.user.management.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.management.exception.ResourceAlreadyExistException;
import com.user.management.exception.ResourceNotFoundException;
import com.user.management.exception.UnauthorizedException;
import com.user.management.model.Roles;
import com.user.management.model.User;
import com.user.management.repo.RolesRepository;
import com.user.management.repo.UserRepository;
import com.user.management.service.UserService;
import com.user.management.util.AppConstant;
import com.user.management.util.JwtUtil;

@Service
public class UserServiceImpl implements UserService,UserDetailsService {

	    @Autowired
	    private UserRepository userRepository;
	    
	    @Autowired
	    private RolesRepository rolesRepository;

	    @Autowired
	    private BCryptPasswordEncoder encoder;
	    
	    @Autowired
	    private JwtUtil jwtUtil;
	    
	    @Override
	    public User createUser(User user) {
	    	Optional<User> username = userRepository.findByUsername(user.getUsername());
	    	if(username.isEmpty()) {
	        user.setPassword(encoder.encode(user.getPassword()));
	        Roles role = rolesRepository.findByRoleName(user.getRoleId().getRoleName()).orElseThrow(() -> new ResourceNotFoundException(AppConstant.ROLE_NOT_FOUND));
	        user.setRoleId(role);
	        return userRepository.save(user);
	    }
	    	else {
				throw new ResourceAlreadyExistException(AppConstant.USER_FOUND);
	    	}
	    }
	    @Override
	    public void deleteUser(String id) {
	    	Optional<User> username = userRepository.findById(id);
	    	if(username.isPresent())
	        userRepository.deleteById(id);
	    	else
				throw new ResourceNotFoundException(AppConstant.USER_NOT_FOUND);

	    }

	    @Override
	    public List<User> getAllUsers() {
	        return userRepository.findAll();
	    }

		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		     User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(AppConstant.USER_NOT_FOUND));		     
		 	List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(user.getRoleId().getRoleName().name()));
			return new org.springframework.security.core.userdetails.User(username, username, authorities);

			}


		@Override
		public String login(User user) {
			System.out.println(user.getUsername()+" "+user.getPassword());
			Optional<User> user1 = userRepository
					.findByUsername(user.getUsername());
			if (user1.isPresent()) {
					if (encoder.matches(user.getPassword().trim(), user1.get().getPassword())) {
						
					String accessToken = jwtUtil.generateToken(user1.get().getUsername());
					return accessToken;
				}
					else {
						throw new UnauthorizedException(AppConstant.INVALID_CREDENTIALS);
					}
			}
			throw new ResourceNotFoundException(AppConstant.USER_NOT_FOUND);
		}

}
