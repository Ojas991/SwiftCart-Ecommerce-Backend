package com.swiftcart.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Request object for placing a new order")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderRequestDto {
	
	 @Schema(
		     description = "Unique identifier of the user placing the order",
		     example = "1"
	)
	@NotNull(message= "User ID is required")
	private Long userId;
	
	 @Schema(
		     description = "Additional notes or special delivery instructions",
		     example = "Please deliver after 6 PM."
	)
	@Size(max= 500, message= "Notes must be exceed 500 Characters")
	private String notes;
	
}
