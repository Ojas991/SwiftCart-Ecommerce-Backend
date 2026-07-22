package com.swiftcart.service;

import java.util.List;

import com.swiftcart.dto.request.PlaceOrderRequestDto;
import com.swiftcart.dto.request.UpdateOrderStatusRequestDto;
import com.swiftcart.dto.response.OrderResponseDto;
import com.swiftcart.dto.response.PageResponseDto;

public interface OrderService {


    OrderResponseDto placeOrder(PlaceOrderRequestDto placeOrderRequestDto);

    OrderResponseDto getOrderById(Long orderId);

    OrderResponseDto getOrderByOrderNumber(String orderNumber);

    List<OrderResponseDto> getOrdersByUserId(Long userId);

    PageResponseDto<OrderResponseDto> getAllOrdersPaginated(int page, int size, String sortBy, String sortDir);

    List<OrderResponseDto> getOrdersByStatus(String status);

    OrderResponseDto updateOrderStatus(Long orderId, UpdateOrderStatusRequestDto updateOrderStatusRequestDto);

    OrderResponseDto cancelOrder(Long orderId, String reason);

    PageResponseDto<OrderResponseDto> searchOrders(String keyword, int page, int size);
    
    
}
