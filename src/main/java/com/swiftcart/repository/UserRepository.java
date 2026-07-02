package com.swiftcart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.swiftcart.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	//Find User by Email--->
	public Optional<User> findByEmail(String email);
	
	//Check Email Exists or Not--->
	public boolean existsByEmail(String email);
	
	//Find All Active Users--->
	public List<User> findByIsActiveTrue();
	
	//Search User By Name Or Email--->
	@Query("SELECT u FROM User u WHERE (LOWER(u.fullname) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
		       "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')))")
	public List<User> searchByNameOrEmail(@Param("keyword") String keyword);
	
}


