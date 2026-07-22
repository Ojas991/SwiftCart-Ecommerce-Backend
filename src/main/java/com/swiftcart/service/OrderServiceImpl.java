package com.swiftcart.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.swiftcart.dto.request.PlaceOrderRequestDto;
import com.swiftcart.dto.request.UpdateOrderStatusRequestDto;
import com.swiftcart.dto.response.OrderItemResponseDto;
import com.swiftcart.dto.response.OrderResponseDto;
import com.swiftcart.dto.response.PageResponseDto;
import com.swiftcart.entity.Cart;
import com.swiftcart.entity.CartItem;
import com.swiftcart.entity.Order;
import com.swiftcart.entity.OrderItem;
import com.swiftcart.entity.Product;
import com.swiftcart.entity.User;
import com.swiftcart.exception.EmptyCartException;
import com.swiftcart.exception.InsufficientStockException;
import com.swiftcart.exception.InvalidOperationException;
import com.swiftcart.exception.ResourceNotFoundException;
import com.swiftcart.repository.CartRepository;
import com.swiftcart.repository.OrderItemRepository;
import com.swiftcart.repository.OrderRepository;
import com.swiftcart.repository.ProductRepository;
import com.swiftcart.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
	
	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	private final CartRepository cartRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	

	//<------Helper Method------>
	
    //find order by ID--->
    private Order findOrderById(Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            return orderOptional.get();
        } else {
            throw new ResourceNotFoundException("Order", "id", orderId);
        }
    }

    //validate status transition--->
    private void validateStatusTransition(String currentStatus, String newStatus) {
        if (Order.STATUS_CONFIRMED.equals(currentStatus)) {
            if (!Order.STATUS_CANCELLED.equals(newStatus)) {
                throw new InvalidOperationException("Cannot transition from CONFIRMED to " + newStatus);
            }
        } else if (Order.STATUS_CANCELLED.equals(currentStatus)) {
            throw new InvalidOperationException("Cannot change status of " + currentStatus + " order");
        }
    }

    //restore stock when order is cancelled--->
    private void restoreStock(Order order) {
        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();
            if (product != null) {
                product.setStockQuantity(product.getStockQuantity() + orderItem.getQuantity());
            }
        }
    }

    //map Order to OrderResponseDTO--->
    private OrderResponseDto mapToOrderResponseDto(Order order) {
        List<OrderItemResponseDto> items = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            items.add(mapToOrderItemResponseDto(orderItem));
        }

        int totalItems = 0;
        for (OrderItem orderItem : order.getOrderItems()) {
            totalItems = totalItems + orderItem.getQuantity();
        }

        return OrderResponseDto.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .userId(order.getUser().getId())
                .userName(order.getUser().getFullName())
                .userEmail(order.getUser().getEmail())
                .items(items)
                .totalItems(totalItems)
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .notes(order.getNotes())
                .orderDate(order.getOrderDate())
                .build();
    }

    //map OrderItem to OrderItemResponseDTO--->
    private OrderItemResponseDto mapToOrderItemResponseDto(OrderItem orderItem) {
        return OrderItemResponseDto.builder()
                .id(orderItem.getId())
                .productId(orderItem.getProduct().getId())
                .productName(orderItem.getProductName())
                .productSku(orderItem.getProductSku())
                .productImage(orderItem.getProduct().getImageUrl())
                .quantity(orderItem.getQuantity())
                .unitPrice(orderItem.getUnitPrice())
                .subTotal(orderItem.getSubTotal())
                .build();
    }

    //map Page to PageResponseDTO--->
    private PageResponseDto<OrderResponseDto> mapToPageResponse(Page<Order> orderPage) {
        List<OrderResponseDto> orders = new ArrayList<>();
        for (Order order : orderPage.getContent()) {
            orders.add(mapToOrderResponseDto(order));
        }

        return PageResponseDto.<OrderResponseDto>builder()
                .content(orders)
                .pageNumber(orderPage.getNumber())
                .pageSize(orderPage.getSize())
                .totalElements(orderPage.getTotalElements())
                .totalPages(orderPage.getTotalPages())
                .first(orderPage.isFirst())
                .last(orderPage.isLast())
                .hasNext(orderPage.hasNext())
                .hasPrevious(orderPage.hasPrevious())
                .build();
    }
	
	
	
	@Override
	public OrderResponseDto placeOrder(PlaceOrderRequestDto placeOrderRequestDto) {
		
		//Fetch User--->
		Optional<User> userOptional= userRepository.findById(placeOrderRequestDto.getUserId());
		
		if(! userOptional.isPresent()) {
			throw new ResourceNotFoundException("User", "id", placeOrderRequestDto.getUserId());
		}
		
		User user= userOptional.get();
		
		//Fetch Cart with item--->
		Optional<Cart> cartOptional = cartRepository.findByUserIdWithItems(placeOrderRequestDto.getUserId());
        if (!cartOptional.isPresent()) {
            throw new ResourceNotFoundException("Cart", "userId", placeOrderRequestDto.getUserId());
        }
        Cart cart = cartOptional.get();

        //Validate cart is not empty--->
        if (cart.getCartItems().isEmpty()) {
            throw new EmptyCartException("Cannot place order with an empty cart");
        }

        //Copy cart items to avoid concurrent modification issues--->
        List<CartItem> cartItemsCopy = new ArrayList<>(cart.getCartItems());

        //Validate stock availability for all items--->
        for (CartItem cartItem : cartItemsCopy) {
            Product product = cartItem.getProduct();
            if (!product.getIsAvailable()) {
                throw new InsufficientStockException(product.getName() + " is no longer available");
            }
            if (product.getStockQuantity() < cartItem.getQuantity()) {
                throw new InsufficientStockException(
                        "Insufficient stock for " + product.getName() +
                                ". Available: " + product.getStockQuantity() +
                                ", Requested: " + cartItem.getQuantity());
            }
        }

        //Calculate total amount as the sum of all cart item subTotals--->
        double totalAmount = 0.0;
        for (CartItem cartItem : cartItemsCopy) {
            totalAmount = totalAmount + cartItem.getSubTotal();
        }

        //Create order--->
        Order order = Order.builder()
                .user(user)
                .totalAmount(totalAmount)
                .notes(placeOrderRequestDto.getNotes())
                .status(Order.STATUS_CONFIRMED)
                .build();

        Order savedOrder = orderRepository.save(order);

        //Create order items from cart items--->
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItemsCopy) {
            OrderItem orderItem = OrderItem.fromCartItem(cartItem);
            orderItem.setOrder(savedOrder);
            orderItems.add(orderItem);

            //Decrease product stock--->
            productRepository.decreaseStock(cartItem.getProduct().getId(), cartItem.getQuantity());
        }
        orderItemRepository.saveAll(orderItems);
        savedOrder.setOrderItems(orderItems);

        //Clear the cart after order is placed--->
        cart.getCartItems().clear();
        cart.setTotalItems(0);
        cart.setTotalAmmount(0.0);
        cartRepository.save(cart);

        return mapToOrderResponseDto(savedOrder);
	}
	@Override
    public OrderResponseDto getOrderById(Long orderId) {
        Optional<Order> orderOptional = orderRepository.findByIdWithItems(orderId);
        if (orderOptional.isPresent()) {
            return mapToOrderResponseDto(orderOptional.get());
        } else {
            throw new ResourceNotFoundException("Order", "id", orderId);
        }
    }

    @Override
    public OrderResponseDto getOrderByOrderNumber(String orderNumber) {
        Optional<Order> orderOptional = orderRepository.findByOrderNumberWithItems(orderNumber);
        if (orderOptional.isPresent()) {
            return mapToOrderResponseDto(orderOptional.get());
        } else {
            throw new ResourceNotFoundException("Order", "orderNumber", orderNumber);
        }
    }

    @Override
    public List<OrderResponseDto> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserIdOrderByOrderDateDesc(userId);
        List<OrderResponseDto> responseList = new ArrayList<>();
        for (Order order : orders) {
            responseList.add(mapToOrderResponseDto(order));
        }
        return responseList;
    }

    @Override
    public PageResponseDto<OrderResponseDto> getAllOrdersPaginated(int page, int size, String sortBy, String sortDir) {
        Sort sort;
        if (sortDir.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Order> orderPage = orderRepository.findAll(pageable);
        return mapToPageResponse(orderPage);
    }

    @Override
    public List<OrderResponseDto> getOrdersByStatus(String status) {
        List<Order> orders = orderRepository.findByStatus(status);
        List<OrderResponseDto> responseList = new ArrayList<>();
        for (Order order : orders) {
            responseList.add(mapToOrderResponseDto(order));
        }
        return responseList;
    }

    @Override
    public OrderResponseDto updateOrderStatus(Long orderId, UpdateOrderStatusRequestDto updateOrderStatusRequestDto) {
        Order order = findOrderById(orderId);

        validateStatusTransition(order.getStatus(), updateOrderStatusRequestDto.getOrderStatus().toUpperCase());

        order.setStatus(updateOrderStatusRequestDto.getOrderStatus().toUpperCase());

        if (Order.STATUS_CANCELLED.equals(updateOrderStatusRequestDto.getOrderStatus().toUpperCase())) {
            restoreStock(order);
        }

        if (updateOrderStatusRequestDto.getNotes() != null && !updateOrderStatusRequestDto.getNotes().isEmpty()) {
            String existingNotes = order.getNotes() != null ? order.getNotes() + "\n" : "";
            order.setNotes(existingNotes + "[" + LocalDateTime.now() + "] " + updateOrderStatusRequestDto.getNotes());
        }

        Order updatedOrder = orderRepository.save(order);
        return mapToOrderResponseDto(updatedOrder);
    }

    @Override
    public OrderResponseDto cancelOrder(Long orderId, String reason) {
        Order order = orderRepository.findByIdWithItems(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        if (!Order.STATUS_CONFIRMED.equalsIgnoreCase(order.getStatus())) {
            throw new InvalidOperationException("Cannot cancel order with status: " + order.getStatus());
        }

        order.setStatus(Order.STATUS_CANCELLED);

        String notes = order.getNotes() != null ? order.getNotes() + "\n" : "";
        order.setNotes(notes +" Cancelled: " + reason);

        restoreStock(order);

        Order cancelledOrder = orderRepository.save(order);
        return mapToOrderResponseDto(cancelledOrder);
    }

    @Override
    public PageResponseDto<OrderResponseDto> searchOrders(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orderPage = orderRepository.searchOrders(keyword, pageable);
        return mapToPageResponse(orderPage);
    }
 
}
