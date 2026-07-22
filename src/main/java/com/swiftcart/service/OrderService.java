package com.swiftcart.service;

import java.util.List;

import com.swiftcart.dto.request.PlaceOrderRequestDto;
import com.swiftcart.dto.request.UpdateOrderStatusRequestDto;
import com.swiftcart.dto.response.OrderResponseDto;
import com.swiftcart.dto.response.PageResponseDto;

public interface OrderService {

	public OrderResponseDto placeOrder(PlaceOrderRequestDto placeOrderRequestDto);
	
	public OrderResponseDto getOrderById(Long orderId);
	
	public OrderResponseDto getOrderByOrderNumber(String orderNumber);

	public List<OrderResponseDto> getOrdersByUserId(Long userId);

	public PageResponseDto<OrderResponseDto> getAllOrdersPaginated(int page, int size, String sortBy, String sortDir);

	public List<OrderResponseDto> getOrdersByStatus(String status);

	public OrderResponseDto updateOrderStatus(Long orderId, UpdateOrderStatusRequestDto updateOrderStatusRequestDTO);

	public OrderResponseDto cancelOrder(Long orderId, String reason);

	public PageResponseDto<OrderResponseDto> searchOrders(String keyword, int page, int size);
}
