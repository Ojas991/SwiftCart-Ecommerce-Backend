package com.swiftcart.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderRequestDto {
	
	@NotNull(message= "User ID is required")
	private Long userId;
	
	@Size(max= 500, message= "Notes must be exceed 500 Characters")
	private String notes;
	
}
