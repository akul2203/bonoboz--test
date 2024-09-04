package com.user.management.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.management.model.User;
import com.user.management.service.UserService;
import com.user.management.util.AppConstant;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/admin/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<User>(userService.createUser(user),HttpStatus.CREATED );
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
    	userService.deleteUser(id);
        return new ResponseEntity<String>(AppConstant.USER_DELETED,HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<List<User>>(userService.getAllUsers(),HttpStatus.OK );
    }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> loginUser(@RequestBody User user){
    	Map<String, Object> map = new HashMap<>();
    	map.put("token", userService.login(user));
    	return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
    }
}

