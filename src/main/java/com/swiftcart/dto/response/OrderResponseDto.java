package com.swiftcart.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Response object containing order details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {

	@Schema(
		        description = "Unique identifier of the order",
		        example = "1"
	)
    private Long id;
	
	@Schema(
	        description = "Unique order number",
	        example = "ORD-20260723-0001"
	)
    private String orderNumber;
	
    @Schema(
            description = "Unique identifier of the customer",
            example = "101"
    )
    private Long userId;
    
    @Schema(
            description = "Full name of the customer",
            example = "Ojas Bisen"
        )
    private String userName;
    
    @Schema(
            description = "Email address of the customer",
            example = "ojas@example.com"
        )
    private String userEmail;
    
    @Schema(
            description = "List of items included in the order"
        )
    private List<OrderItemResponseDto> items;
    
    @Schema(
            description = "Total number of items in the order",
            example = "3"
        )
    private Integer totalItems;
    
    @Schema(
            description = "Total payable amount for the order",
            example = "2499.97"
        )
    private double totalAmount;
    
    @Schema(
            description = "Current status of the order",
            example = "CONFIRMED"
        )
    private String status;
    
    @Schema(
            description = "Additional notes related to the order",
            example = "Please deliver after 6 PM."
        )
    private String notes;
    
    @Schema(
            description = "Date and time when the order was placed",
            example = "2026-07-23T10:30:00"
        )
    private LocalDateTime orderDate;
	
}
