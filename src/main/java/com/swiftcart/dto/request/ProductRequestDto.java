package com.swiftcart.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Request object for creating a new product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {
	
	@Schema(
	        description = "Product name",
	        example = "Samsung Galaxy S25"
	    )
	@NotBlank(message="Product Name is Required")
	@Size(min=2, max=200, message="Name must be between 2 to 200 Character")
	private String name;
	
	 @Schema(
		        description = "Product description",
		        example = "Latest Android flagship smartphone"
		    )
	@Size(max=1000, message="Description must not exceed 1000 Character")
	private String description;
	
	
	 @Schema(
	            description = "Product price",
	            example = "79999.99"
	        )
	@NotNull(message = "Product Price is Required")
	@Min(value = 0, message = "Price must be greater than or equal to 0")
	private Double price;
	
	 @Schema(
	            description = "Available stock quantity",
	            example = "50"
	        )
	@NotNull(message="Quantity is Required")
	@Min(value=0, message="Quantity must be greater than or equal to 0")
	private Integer stockQuantity;
	 
	
	 @Schema(
	            description = "Product category",
	            example = "Electronics"
	        )
	@Size(max=100, message="Category must not exceed 100 Characters")
	private String category;
	
	@Schema(
	            description = "Product brand",
	            example = "Samsung"
	        )	
	@Size(max=100, message="Brand must not excced 100 Characters")
	private String brand;
	
	
    @Schema(
            description = "Image URL of the product",
            example = "https://example.com/images/s25.jpg"
        )
	@Size(max=500, message="Image URL must not exceed 500 Characters")
	private String imageUrl;
	
    
    @Schema(
            description = "Stock Keeping Unit (SKU)",
            example = "SAM-S25-256-BLK"
        )
	@Size(max=50, message="Sku must not excced 50 Characters")
	private String sku;

    @Schema(
        description = "Whether the product is available for purchase",
        example = "true"
    )
	private Boolean isAvailable;
}
