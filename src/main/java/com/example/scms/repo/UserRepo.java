package com.example.scms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.scms.model.User;

import jakarta.transaction.Transactional;
@Transactional
public interface UserRepo extends JpaRepository<User, Integer> {
	User findByUserEmail(String email);
	@Query("from User u where u.isApproved=false")
	List<User> getCustomersToApprove();
	@Modifying
	@Query("update User user set user.isApproved=true where user.userId=?1")
	int updateUser(int userId);
	@Modifying
	@Query("update User user set user.userPassword=?2 where user.userEmail=?1")
	int updateUserPassword(String userEmail, String password);

}
