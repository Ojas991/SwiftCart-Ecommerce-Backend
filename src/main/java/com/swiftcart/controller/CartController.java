package com.swiftcart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swiftcart.dto.request.AddToCartRequestDto;
import com.swiftcart.dto.request.UpdateCartItemRequestDto;
import com.swiftcart.dto.response.ApiResponseDTO;
import com.swiftcart.dto.response.CartItemResponseDto;
import com.swiftcart.dto.response.CartResponseDto;
import com.swiftcart.service.CartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins= "*")
public class CartController {
	
	private CartService serv;

	@Autowired
	public CartController(CartService serv) {
		this.serv = serv;
	}
	
	// Retrieve the cart details for a specific user.--->
	@GetMapping("/{userId}")
	public ResponseEntity<ApiResponseDTO<CartResponseDto>> getCart(@PathVariable Long userId){
		
		CartResponseDto dto= serv.getCartByUserId(userId);
		
		return ResponseEntity.ok(ApiResponseDTO.success(dto));
	}
	
	// Add a product to the user's cart.--->
	@PostMapping("/{userId}/items")
	public ResponseEntity<ApiResponseDTO<CartResponseDto>> addItemToCart(@PathVariable Long userId, @Valid @RequestBody AddToCartRequestDto dto){
		
		CartResponseDto cartDto= serv.addItemToCart(userId, dto);
		
		return ResponseEntity.ok(ApiResponseDTO.success("Item added Successfully", cartDto));
	}
	
	// Update the quantity of a specific cart item.--->
	@PutMapping("/{userId}/items/{cartItemId}")
	public ResponseEntity<ApiResponseDTO<CartResponseDto>> updateCartItem(@PathVariable Long userId, @PathVariable Long cartItemId, @Valid @RequestBody UpdateCartItemRequestDto dto){
		
		CartResponseDto cartDto= serv.updateCartItem(userId, cartItemId, dto);
		
		return ResponseEntity.ok(ApiResponseDTO.success("Item updated Successfully", cartDto));
	}
	
	// Remove a specific item from the user's cart.--->
	@DeleteMapping("/{userId}/items/{cartItemId}")
	public ResponseEntity<ApiResponseDTO<CartResponseDto>> removeItemFromCart(@PathVariable Long userId, @PathVariable Long cartItemId){
		
		CartResponseDto cartDto= serv.removeItemFromCart(userId, cartItemId);
		
		return ResponseEntity.ok(ApiResponseDTO.success("Item removed Successfully", cartDto));
	}
	
	// Remove all items from the user's cart.--->
	@DeleteMapping("/{userId}/clear")
	public ResponseEntity<ApiResponseDTO<CartResponseDto>> clearCart(@PathVariable Long userId){
		
		CartResponseDto cartDto= serv.clearCart(userId);
		
		return ResponseEntity.ok(ApiResponseDTO.success("Cart Cleared Successfully", cartDto));
	}
	
	// Retrieve details of a specific cart item.--->
	@GetMapping("/{userId}/items/{cartItemId}")
	public ResponseEntity<ApiResponseDTO<CartItemResponseDto>> getCartItem(@PathVariable Long userId, @PathVariable Long cartItemId){
		
		CartItemResponseDto dto= serv.getCartItem(userId, cartItemId);
		
		return ResponseEntity.ok(ApiResponseDTO.success(dto));
	}
	
	// Check whether a product exists in the user's cart.--->
	@GetMapping("/{userId}/check-product/{productId}")
	public ResponseEntity<ApiResponseDTO<Boolean>> isProductInCart(@PathVariable Long userId, @PathVariable Long productId){
		
		boolean inCart= serv.isProductInCart(userId, productId);
		
		return ResponseEntity.ok(ApiResponseDTO.success(inCart ? "Product is in the Cart" : "Product is not in the Cart", inCart));
	}
	
	// Increase the quantity of a specific product in the user's cart.--->
	@PatchMapping("/{userId}/products/{productId}/increment")
	public ResponseEntity<ApiResponseDTO<CartResponseDto>> incrementItemQuantity(@PathVariable Long userId, @PathVariable Long productId, @RequestParam(defaultValue= "1") int quantity){
		
		CartResponseDto cartDto= serv.incrementItemQuantity(userId, productId, quantity);
		
		return ResponseEntity.ok(ApiResponseDTO.success("Quantity incremented Successfully", cartDto));
	}
	
	// Decrease the quantity of a specific product in the user's cart.--->
	@PatchMapping("/{userId}/products/{productId}/decrement")
	public ResponseEntity<ApiResponseDTO<CartResponseDto>> decrementItemQuantity(@PathVariable Long userId, @PathVariable Long productId, @RequestParam(defaultValue= "1") int quantity){
		
		CartResponseDto cartDto= serv.decrementItemQuantity(userId, productId, quantity);
		
		return ResponseEntity.ok(ApiResponseDTO.success("Quantity decrement Successfully", cartDto));
	}

}
