package com.swiftcart.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swiftcart.dto.request.PlaceOrderRequestDto;
import com.swiftcart.dto.request.UpdateOrderStatusRequestDto;
import com.swiftcart.dto.response.ApiResponseDto;
import com.swiftcart.dto.response.OrderResponseDto;
import com.swiftcart.dto.response.PageResponseDTO;
import com.swiftcart.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    // Place a new order
    @PostMapping
    public ResponseEntity<ApiResponseDto<OrderResponseDto>> placeOrder(
            @Valid @RequestBody PlaceOrderRequestDto placeOrderRequestDto) {
        OrderResponseDto order = orderService.placeOrder(placeOrderRequestDto);
        return new ResponseEntity<>(
                ApiResponseDto.success("Order placed successfully", order),
                HttpStatus.CREATED);
    }

     //Get order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponseDto<OrderResponseDto>> getOrderById(@PathVariable Long orderId) {
        OrderResponseDto order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(ApiResponseDto.success(order));
    }

    //Get order by order number
    @GetMapping("/order-number/{orderNumber}")
    public ResponseEntity<ApiResponseDto<OrderResponseDto>> getOrderByOrderNumber(
            @PathVariable String orderNumber) {
        OrderResponseDto order = orderService.getOrderByOrderNumber(orderNumber);
        return ResponseEntity.ok(ApiResponseDto.success(order));
    }

    // GET /api/orders/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDto<List<OrderResponseDto>>> getOrdersByUserId(
            @PathVariable Long userId) {
        List<OrderResponseDto> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(ApiResponseDto.success("Fetched " + orders.size() + " orders", orders));
    }

    //Get all orders (paginated)
    @GetMapping
    public ResponseEntity<ApiResponseDto<PageResponseDTO<OrderResponseDto>>> getAllOrdersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "orderDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        PageResponseDTO<OrderResponseDto> orders = orderService.getAllOrdersPaginated(page, size, sortBy, sortDir);
        return ResponseEntity.ok(ApiResponseDto.success(orders));
    }

    //Get orders by status
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponseDto<List<OrderResponseDto>>> getOrdersByStatus(
            @PathVariable String status) {
        List<OrderResponseDto> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(ApiResponseDto.success(orders));
    }

    //Update order status
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<ApiResponseDto<OrderResponseDto>> updateOrderStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody UpdateOrderStatusRequestDto updateOrderStatusRequestDto) {
        OrderResponseDto order = orderService.updateOrderStatus(orderId, updateOrderStatusRequestDto);
        return ResponseEntity.ok(ApiResponseDto.success("Order status updated successfully", order));
    }

    //Cancel order
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponseDto<OrderResponseDto>> cancelOrder(
            @PathVariable Long orderId,
            @RequestParam(required = false, defaultValue = "Customer requested cancellation") String reason) {
        OrderResponseDto order = orderService.cancelOrder(orderId, reason);
        return ResponseEntity.ok(ApiResponseDto.success("Order cancelled successfully", order));
    }

    // Search orders
    @GetMapping("/search")
    public ResponseEntity<ApiResponseDto<PageResponseDTO<OrderResponseDto>>> searchOrders(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponseDTO<OrderResponseDto> orders = orderService.searchOrders(keyword, page, size);
        return ResponseEntity.ok(ApiResponseDto.success(orders));
    }
}
