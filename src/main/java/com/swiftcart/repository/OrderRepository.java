package com.swiftcart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.swiftcart.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	//Returns List of Orders of a Specific User in Descending Order of Date--->
	
	public List<Order> findByUserIdOrderByOrderDateDesc(Long userId);
	
	//Returns Orders of a Specific User based on Pagination--->
	public Page<Order> findByUserId(Long userid, Pageable pageble);
	
	//Returns List of Orders based on Status--->
	public List<Order> findByStatus(String status);
	
	//Returns Order with items on the basis orderId--->
	@Query("Select o From Order o " + 
		   "LEFT JOIN FETCH o.orderItems oi " +
		   "LEFT JOIN FETCH oi.product " +
		   "Where o.id = :orderId "
		  )
	public Optional<Order> findByIdWithItems(@Param("orderId") Long orderId);
	
	//Returns Order with items on the basis orderNumber--->
	@Query("Select o From Order o " + 
		   "LEFT JOIN FETCH o.orderItems oi " +
		   "LEFT JOIN FETCH oi.product " +
		   "Where o.orderNumber = :orderNumber "
		  )
	public Optional<Order> findByOrderNumberWithItems(@Param("orderNumber") String orderNumber);
		
	//Search orders by order number, customer name, or email --->
	@Query("""
			SELECT o
			FROM Order o
			WHERE o.orderNumber LIKE CONCAT('%', :keyword, '%')
			   OR o.user.fullName LIKE CONCAT('%', :keyword, '%')
			   OR o.user.email LIKE CONCAT('%', :keyword, '%')
			""")
	public Page<Order> searchOrders(@Param("keyword") String keyword, Pageable pageble);
}
