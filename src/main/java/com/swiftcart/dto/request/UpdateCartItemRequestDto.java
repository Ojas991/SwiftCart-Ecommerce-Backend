package com.swiftcart.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class UpdateCartItemRequestDto {
	
	@NotNull(message="Quantity is Required")
	@Min(value=1, message="Minimun Quantity must be 1")
	@Max(value=100, message="Maximun Quantity can be 100")
	private Integer quantity;
	
	
}
