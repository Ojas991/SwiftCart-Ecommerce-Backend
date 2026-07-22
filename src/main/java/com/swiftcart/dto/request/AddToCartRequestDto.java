package com.swiftcart.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Request object for adding a product to the shopping cart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddToCartRequestDto {
	
	@Schema(
	        description = "Unique identifier of the product to be added to the cart",
	        example = "1"
	)
	@NotNull(message="Product ID is Required")
	private Long productId;
	
	 @Schema(
		        description = "Quantity of the product to add to the cart",
		        example = "2"
	)
	@NotNull(message="Quantity is Required")
	@Min(value=1, message="Minimum Quantity must be 1")
	@Max(value=100, message="Maximum Quantity can be 100")
	private Integer quantity;
	
	
}
