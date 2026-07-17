package com.swiftcart.dto.response;

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
public class OrderItemResponseDto {

	private Long id;
	private Long productId;
	private String productName;
	private String productSku;
	private String productImage;
	private Integer quantity;
	private double unitPrice;
	private double subTotal;
	
}
