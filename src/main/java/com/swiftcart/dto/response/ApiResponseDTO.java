package com.swiftcart.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDTO<T> {

	private boolean success;
	private String message;
	private LocalDateTime timeStamp;
	private T data;
	
	
	//<-----Static Methods----->
	
	
	/**
	 * Use when you want to return both a success message and response data.
	 * Example:
	 * "User created successfully" + UserResponseDTO
	 */
	public static <T> ApiResponseDTO<T> success(String message, T data) {
		return ApiResponseDTO.<T>builder()
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
	public static <T> ApiResponseDTO<T> success(String message) {
		return ApiResponseDTO.<T>builder()
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
	public static <T> ApiResponseDTO<T> success(T data) {
		return ApiResponseDTO.<T>builder()
				.success(true)
				.data(data)
				.timeStamp(LocalDateTime.now())
				.build();
	}
}
