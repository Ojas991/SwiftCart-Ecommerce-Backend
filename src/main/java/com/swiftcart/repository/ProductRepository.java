package com.swiftcart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.swiftcart.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
	//Find Product by Sku--->
	public Optional<Product> findBySku(String sku);
	
	//Exists Product by Sku--->
	public boolean existsBySku(String sku);
	
	//Find Product By IsAvailable--->
	public List<Product> findByIsAvailableTrue();
	
	//Find Product By Category--->
	public List<Product> findByCategoryIgnoreCase(String category);
	
	//Find Product By Price Between--->
	public List<Product> findByPriceBetween(double minPrice, double maxPrice);
	
	//Search Product--->
	@Query("SELECT p FROM Product p WHERE (LOWER(p.name) like LOWER(CONCAT('%', :keyword, '%'))" +
			"OR LOWER(p.description) like LOWER(CONCAT('%', :keyword, '%'))) AND p.isAvailable= true" )
	public List<Product> searchProducts(@Param("keyword") String keyword);
	
	//Increase Stock--->
	@Query("update Product p SET p.stockQuantity = p.stockQuantity + :quantity where p.id = :productId")
	@Modifying
	public int increaseStock(@Param("productId") Long productId, @Param("quantity") Integer quantity);
	
	//Decrease Stock--->
	@Query("update Product p SET p.stockQuantity = p.stockQuantity - :quantity where p.id = :productId AND p.stockQuantity >= :quantity")
	@Modifying
	public int decreaseStock(@Param("productId") Long productId, @Param("quantity") Integer quantity);
	
	//Find Low Stock Products--->
	@Query("SELECT p FROM Product p where p.stockQuantity <= :threshold AND p.isAvailable = true")
	public List<Product> findLowStockProducts(@Param("threshold") Integer threshold);
	
}
