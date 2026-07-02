package com.swiftcart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.swiftcart.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{
	
	//Find By User Id--->
	public Optional<Cart> findByUserId(Long userId);
	
	//Exists By User Id --->
	public boolean existsByUserId(Long userId);
	
	//Delete By User Id--->
	public void deleteByUserId(Long userId);
	
	//Find By UserId With Items--->
	@Query("Select c From Cart c " + 
		   "LEFT JOIN FETCH c.cartItems ci " +
		   "LEFT JOIN FETCH ci.product " +
		   "Where c.user.id = :userId"
		   )
	public Optional<Cart> findByUserIdWithItems(@Param("userId")Long userId);
	
}
