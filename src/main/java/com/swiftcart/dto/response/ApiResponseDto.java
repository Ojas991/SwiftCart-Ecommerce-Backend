package com.swiftcart.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Standard API response wrapper")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDto<T> {

    @Schema(
            description = "Indicates whether the request was successful",
            example = "true"
    )
	private boolean success;
    
    @Schema(
            description = "Response message",
            example = "Order placed successfully"
    )
	private String message;
    
    @Schema(
            description = "Timestamp when the response was generated",
            example = "2026-07-23T10:30:00"
    )
	private LocalDateTime timeStamp;
    

    @Schema(
        description = "Response payload"
    )
	private T data;

    @Schema(
        description = "Response payload"
    )
	private Object errors;
	
	//<-----Static Methods----->
	
	
	/**
	 * Use when you want to return both a success message and response data.
	 * Example:
	 * "User created successfully" + UserResponseDTO
	 */
	public static <T> ApiResponseDto<T> success(String message, T data) {
		return ApiResponseDto.<T>builder()
				.success(true)
				.message(message)
				.data(data)
				.timeStamp(LocalDateTime.now())
				.build();
	}
	
	/**
	 * Use when you want to return both a success message and response data.
	 * Example:
	 * "User created successfully" + UserResponseDTO
	 */
	public static <T> ApiResponseDto<T> success(String message) {
		return ApiResponseDto.<T>builder()
				.success(true)
				.message(message)
				.timeStamp(LocalDateTime.now())
				.build();
	}
	
	/**
	 * Use when you only want to return response data without any message.
	 * Example:
	 * Returning a list of users or a single user object.
	 */
	public static <T> ApiResponseDto<T> success(T data) {
		return ApiResponseDto.<T>builder()
				.success(true)
				.data(data)
				.timeStamp(LocalDateTime.now())
				.build();
	}
	
	/**
	 * Use when returning an error response.
	 * Example:
	 * "Validation failed" + validation errors
	 */
    public static <T> ApiResponseDto<T> error(String message, Object errors) {
        return ApiResponseDto.<T>builder()
                .success(false)
                .message(message)
                .errors(errors)
                .timeStamp(LocalDateTime.now())
                .build();
    }
}
