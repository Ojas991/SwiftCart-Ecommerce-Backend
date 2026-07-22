package com.swiftcart.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Response object representing an item in an order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponseDto {

	@Schema(
	        description = "Unique identifier of the order item",
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
        example = "Wireless Mouse"
    )
	private String productName;

    @Schema(
        description = "Stock Keeping Unit (SKU) of the product",
        example = "WM-1001"
    )
	private String productSku;
    @Schema(
            description = "URL of the product image",
            example = "https://example.com/images/wireless-mouse.jpg"
    )
	private String productImage;
    
    @Schema(
        description = "Quantity of the product ordered",
        example = "2"
    )
	private Integer quantity;

    @Schema(
        description = "Price of a single unit of the product",
        example = "799.99"
    )
	private double unitPrice;

    @Schema(
        description = "Subtotal amount for this order item (unit price × quantity)",
        example = "1599.98"
    )
	private double subTotal;
    
    
	
}
