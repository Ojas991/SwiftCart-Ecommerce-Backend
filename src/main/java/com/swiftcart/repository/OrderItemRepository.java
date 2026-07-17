package com.swiftcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swiftcart.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
	
	
	
}
