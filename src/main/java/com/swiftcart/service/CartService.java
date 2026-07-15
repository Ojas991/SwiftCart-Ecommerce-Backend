package com.swiftcart.service;

import com.swiftcart.dto.request.AddToCartRequestDto;
import com.swiftcart.dto.request.UpdateCartItemRequestDto;
import com.swiftcart.dto.response.CartItemResponseDto;
import com.swiftcart.dto.response.CartResponseDto;

public interface CartService {

	public CartResponseDto getCartByUserId(Long userId);
	
	public CartResponseDto addItemToCart(Long userId, AddToCartRequestDto dto);
	
	public CartResponseDto updateCartItem(Long userId, Long cartItemId, UpdateCartItemRequestDto dto);
	
	public CartResponseDto removeItemFromCart(Long userId,Long cartItemId);
	
	public CartResponseDto clearCart(Long userId);
	
	public CartItemResponseDto getCartItem(Long userId, Long cartItemId);
	
	public boolean isProductInCart(Long userId, Long productId);
	
	public CartItemResponseDto incrementItemQuantity(Long userId, AddToCartRequestDto dto, int quantity);
	
	public CartItemResponseDto decrementItemQuantity(Long userId, AddToCartRequestDto dto, int quantity);
	
	
}
