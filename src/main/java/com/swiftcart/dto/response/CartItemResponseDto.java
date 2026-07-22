package com.swiftcart.dto.response;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Response object containing shopping cart item details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponseDto {

    @Schema(
        description = "Unique identifier of the cart item",
        example = "1"
    )
	private Long id;
    
    @Schema(
            description = "Unique identifier of the product",
            example = "101"
    )
	private Long productId;
    
    @Schema(
            description = "Name of the product",
            example = "Samsung Galaxy S25"
    )
	private String productName;
    
    @Schema(
            description = "URL of the product image",
            example = "https://example.com/images/s25.jpg"
    )
	private String productImage;
    
    @Schema(
            description = "Stock Keeping Unit (SKU) of the product",
            example = "SAM-S25-256-BLK"
    )
	private String productSku;
    
    @Schema(
            description = "Price of a single product unit",
            example = "79999.99"
    )
	private double unitPrice;
    
    @Schema(
            description = "Quantity of the product in the cart",
            example = "2"
    )
	private Integer quantity;
    
    @Schema(
            description = "Subtotal amount for this cart item",
            example = "159999.98"
    )
	private double subtotal;
    
    @Schema(
            description = "Available stock of the product",
            example = "25"
    )
	private Integer availableStock;
    
    @Schema(
            description = "Date and time when the item was added to the cart",
            example = "2026-07-23T10:30:00"
    )
	private LocalDateTime addedAt;
}
