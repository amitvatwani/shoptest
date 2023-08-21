package com.example.scms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.scms.model.User;
import com.example.scms.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping(value="/signup", consumes = {"application/json"})
	public ResponseEntity<?> userSignup(@jakarta.validation.Valid @RequestBody User user,BindingResult br, HttpSession session){
		
		if (br.hasErrors()) {
			List<String> errors = br.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
	        return new ResponseEntity<List<String>>(errors, HttpStatus.BAD_REQUEST);
        }
		System.out.println(user);
		HashMap<String, String> map = new HashMap<>();
		map.put("error", "User Already Exists");
		if(userService.checkIfUserExists(user.getUserEmail()))
			return new ResponseEntity<HashMap<String, String>>(map, HttpStatus.BAD_REQUEST);
		User savedUser = userService.saveUser(user);
		if(savedUser !=null)
			return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
		else
			return new ResponseEntity<String>("Not Able Signup User", HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> userLogin(@RequestBody User user, HttpSession session){
		if(userService.checkIfUserExists(user.getUserEmail())) {
			if(userService.validatePassword(user)) {
				User existingUser = userService.getUserDetails(user.getUserEmail());
				session.setAttribute("user", existingUser);
				return new ResponseEntity<User>(existingUser, HttpStatus.OK);
			}
			else
				return new ResponseEntity<String>("Invalid Credentials Provided", HttpStatus.BAD_REQUEST);
		}
		else
			return new ResponseEntity<String>("User Does Not Exists", HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> userLogout(HttpSession session){
		session.invalidate();
		return new ResponseEntity<String>("User Logged Out Successfully", HttpStatus.OK);
	}
	
	@GetMapping("/admin/getCustomersToApprove")
	public ResponseEntity<?> getCustomersToApprove(HttpSession session){
		User user = (User) session.getAttribute("user");
		if(user!=null && user.getUserRole().equals("admin"))
			return new ResponseEntity<List<User>>(userService.getCustomersToApprove(), HttpStatus.OK);
		return new ResponseEntity<String>("Error in processing request", HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/admin/approveUser/{userId}")
	public ResponseEntity<?> approveUser(@PathVariable int userId, HttpSession session){
		User user = (User) session.getAttribute("user");
		if(user!=null && user.getUserRole().equals("admin")) {
			if(userService.updateUserToApproved(userId)) {
				return new ResponseEntity<String>("User Approved", HttpStatus.OK);
			}
			else
				return new ResponseEntity<String>("Unable to Approve user", HttpStatus.BAD_REQUEST);
		}
		else {
			return new ResponseEntity<String>("Only Admin Can Approve User. Please Login as Admin", HttpStatus.BAD_REQUEST);
		}
			
//			return new ResponseEntity<List<User>>(userService.getCustomersToApprove(), HttpStatus.OK);
//		return new ResponseEntity<String>("Error in processing request", HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/forgotPassword")
	public ResponseEntity<?> forgotPassword(@RequestBody User user){
		if(userService.checkIfUserExists(user.getUserEmail())) {
			if(userService.sendEmail(user.getUserEmail())) {
				return new ResponseEntity<String>("Email Sent", HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("Unable to send email", HttpStatus.OK);
			}
			
		}
		else {
			return new ResponseEntity<String>("user Does not exists", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
}
