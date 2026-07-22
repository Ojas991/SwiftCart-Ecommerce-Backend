package com.swiftcart.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Response object containing complete shopping cart details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponseDto {

	@Schema(
	        description = "Unique identifier of the shopping cart",
	        example = "1"
	)
	private Long id;

    @Schema(
        description = "Unique identifier of the user",
        example = "101"
    )
	private Long userId;

    @Schema(
        description = "Name of the cart owner",
        example = "Stiven Doe"
    )
	private String userName;

    @Schema(
        description = "List of items available in the shopping cart"
    )
	private List<CartItemResponseDto> items;
    
    @Schema(
            description = "Total number of items in the cart",
            example = "5"
    )
	private Integer totalItems;

    @Schema(
        description = "Total amount of all items in the cart",
        example = "189999.98"
    )
	private double totalAmount;

    @Schema(
        description = "Date and time when the cart was created",
        example = "2026-07-23T10:00:00"
    )
	private LocalDateTime createdAt;

    @Schema(
        description = "Date and time when the cart was last updated",
        example = "2026-07-23T10:45:30"
    )
	private LocalDateTime updatedAt;
}
