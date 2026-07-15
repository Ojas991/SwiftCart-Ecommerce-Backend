package com.swiftcart.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductRequestDTO {

	@Size(min=2, max=200, message="Name must be between 2 to 200 Character")
	private String name;
	
	@Size(max=1000, message="Description must not exceed 1000 Character")
	private String description;
	

	@DecimalMin(value = "0.0", inclusive = true, message = "Price must be greater than or equal to 0")
	private Double price;

	@Min(value=0, message="Quantity must be greater than or equal to 0")
	private Integer stockQuantity;
	
	@Size(max=100, message="Category must not exceed 100 Characters")
	private String category;
	
	@Size(max=100, message="Brand must not excced 100 Characters")
	private String brand;
	
	@Size(max=500, message="Image URL must not exceed 500 Characters")
	private String imageUrl;
	
	@Size(max=50, message="Sku must not excced 50 Characters")
	private String sku;
	
	private Boolean isAvailable;
}
