package com.swiftcart.dto.response;

import java.time.LocalDateTime;
import java.util.List;

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
public class OrderResponseDto {

	private Long id;
	private String orderNumber;
	private Long userId;
	private String userName;
	private String userEmail;
	private List<OrderItemResponseDto> items;
	private Integer totalItems;
	private double totalAmount;
	private String status;
	private String notes;
	private LocalDateTime orderDate;
	
}
