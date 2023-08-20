package com.example.scms.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.scms.model.User;
import com.example.scms.repo.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private JavaMailSender javaMailSender;


	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return userRepo.save(user);
	}
	
	public List<User> getAllUsers(){
		return userRepo.findAll();
	}

	public boolean checkIfUserExists(String userEmail) {
		User user = userRepo.findByUserEmail(userEmail);
		if(user!=null) return true;
		return false;
	}

	public boolean validatePassword(User user) {
		User existingUser = userRepo.findByUserEmail(user.getUserEmail());
		if(user.getUserPassword().equals(existingUser.getUserPassword()))
			return true;
		return false;
	}

	public User getUserDetails(String userEmail) {
		return userRepo.findByUserEmail(userEmail);
	}

	public List<User> getCustomersToApprove() {
		return userRepo.getCustomersToApprove();
	}
	
	public boolean updateUserToApproved(int userId) {
		if(userRepo.updateUser(userId)>0)
			return true;
		return false;
	}

	public boolean sendEmail(String userEmail) {
		String password = generatePassword(8).toString();
		System.out.println(password);
		try {
            // Creating a simple mail message
            SimpleMailMessage mailMessage
                = new SimpleMailMessage();
            System.out.println("in try");
            // Setting up necessary details
            mailMessage.setFrom("themovieticketbooking@gmail.com");
            mailMessage.setTo(userEmail);
            mailMessage.setText(password);
            mailMessage.setSubject("Your Password");
 
            javaMailSender.send(mailMessage);
            userRepo.updateUserPassword(userEmail, password);
        }
        catch (Exception e) {
        	System.out.println("in catch" + e);
             return false;
        }
		return true;
	}
	
	public String generatePassword(int length) {
	      String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	      String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
	      String specialCharacters = "!@#$";
	      String numbers = "1234567890";
	      String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
	      Random random = new Random();
	      char[] password = new char[length];

	      password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
	      password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
	      password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
	      password[3] = numbers.charAt(random.nextInt(numbers.length()));
	   
	      for(int i = 4; i< length ; i++) {
	         password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
	      }
	      String ans = "";
	      for(int i=0; i<password.length; i++)
	    	  ans+=password[i];
	      return ans;
	   }
	
	
}
