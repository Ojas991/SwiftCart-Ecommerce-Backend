package com.swiftcart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swiftcart.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
	
	//Find By CartId And ProductId
	public Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);
	
	//Exists By CartId And ProductId
	public boolean existsByCartIdAndProductId(Long cartId, Long productId);
}
