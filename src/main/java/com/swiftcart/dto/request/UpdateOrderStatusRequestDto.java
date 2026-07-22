package com.swiftcart.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Request object for updating the status of an order")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateOrderStatusRequestDto {
	
	@Schema(
		        description = "New status of the order",
		        example = "SHIPPED",
		        requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotBlank(message = "Order status is required")
    private String orderStatus;

	@Schema(
		        description = "Additional notes regarding the status update",
		        example = "Order dispatched via BlueDart.",
		        maxLength = 500
	)
    private String notes;
	
}
