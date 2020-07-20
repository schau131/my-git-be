package com.hom.vcs.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hom.vcs.model.User;
import com.hom.vcs.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
		
	private final UserService userService;
	
	@PostMapping(value="/{userId}", consumes = { "multipart/form-data" })
	public ResponseEntity<String> uploadUserFiles(@PathVariable("userId") String userId,
			@RequestParam("files") MultipartFile[] files) throws IOException 
	{
		userService.uploadUserFiles(userId, files);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body("Successfully Uploaded");
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUser(@PathVariable("userId") String userId) 
	{
		User user = userService.getUser(userId);
		
		return ResponseEntity
				.ok()
				.body(user);
	}
	
}