package com.swiftcart.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Request object for updating an existing product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductRequestDto {
	
    @Schema(
            description = "Updated product name",
            example = "Samsung Galaxy S25 Ultra"
        )
	@Size(min=2, max=200, message="Name must be between 2 to 200 Character")
	private String name;
	
    @Schema(
            description = "Updated product description",
            example = "Latest Android flagship smartphone with AI features"
        )
	@Size(max=1000, message="Description must not exceed 1000 Character")
	private String description;
	
    @Schema(
            description = "Updated product price",
            example = "89999.99"
        )
	@DecimalMin(value = "0.0", inclusive = true, message = "Price must be greater than or equal to 0")
	private Double price;

    @Schema(
            description = "Updated available stock quantity",
            example = "75"
        )
	@Min(value=0, message="Quantity must be greater than or equal to 0")
	private Integer stockQuantity;
	
    @Schema(
            description = "Updated product category",
            example = "Electronics"
        )
	@Size(max=100, message="Category must not exceed 100 Characters")
	private String category;
	
    @Schema(
            description = "Updated product brand",
            example = "Samsung"
        )
	@Size(max=100, message="Brand must not excced 100 Characters")
	private String brand;
	
    @Schema(
            description = "Updated product image URL",
            example = "https://example.com/images/s25-ultra.jpg"
        )
	@Size(max=500, message="Image URL must not exceed 500 Characters")
	private String imageUrl;
	
    @Schema(
            description = "Updated Stock Keeping Unit (SKU)",
            example = "SAM-S25U-512-BLK"
        )
	@Size(max=50, message="Sku must not excced 50 Characters")
	private String sku;
	
    @Schema(
            description = "Indicates whether the product is available for purchase",
            example = "true"
        )
	private Boolean isAvailable;
}
