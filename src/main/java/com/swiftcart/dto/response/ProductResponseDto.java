package com.swiftcart.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Response object containing product details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {

    @Schema(
        description = "Unique identifier of the product",
        example = "101"
    )
	private Long id;
    
    @Schema(
            description = "Product name",
            example = "Samsung Galaxy S25"
        )
	private String name;
    
    @Schema(
            description = "Product description",
            example = "Latest Android flagship smartphone"
        )
	private String description;
    
    @Schema(
            description = "Product price",
            example = "79999.99"
        )
	private double price;
    
    @Schema(
            description = "Available stock quantity",
            example = "50"
        )
	private Integer stockQuantity;
    
    @Schema(
            description = "Product category",
            example = "Electronics"
        )
	private String category;
    
    @Schema(
            description = "Product brand",
            example = "Samsung"
        )
	private String brand;
    
    @Schema(
            description = "Product image URL",
            example = "https://example.com/images/s25.jpg"
        )
	private String imageUrl;
    
    @Schema(
            description = "Stock Keeping Unit (SKU)",
            example = "SAM-S25-256-BLK"
        )
	private String sku;
    
    @Schema(
            description = "Indicates whether the product is available for purchase",
            example = "true"
        )
	private Boolean isAvailable;
    
    @Schema(
            description = "Indicates whether the product is currently in stock",
            example = "true"
        )
	private Boolean inStock;
	
	
}
