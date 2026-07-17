package com.swiftcart.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateOrderStatusRequestDto {
	
	@NotBlank(message= "Oredr Status is required")
	private String orderStatus;
	
	private String notes;
}
