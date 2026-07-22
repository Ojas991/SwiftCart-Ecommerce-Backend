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
import com.swiftcart.dto.response.ApiResponseDto;
import com.swiftcart.dto.response.CartItemResponseDto;
import com.swiftcart.dto.response.CartResponseDto;
import com.swiftcart.service.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(
	    name = "Cart Management",
	    description = "APIs for managing user shopping carts and cart items"
)
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
	@Operation(
		    summary = "Get user cart",
		    description = "Retrieves the complete shopping cart for the specified user."
	)
	@GetMapping("/{userId}")
	public ResponseEntity<ApiResponseDto<CartResponseDto>> getCart(@PathVariable Long userId){
		
		CartResponseDto dto= serv.getCartByUserId(userId);
		
		return ResponseEntity.ok(ApiResponseDto.success(dto));
	}
	
	// Add a product to the user's cart.--->
	@Operation(
		    summary = "Add product to cart",
		    description = "Adds a product to the specified user's shopping cart. If the product already exists in the cart, its quantity is updated."
	)
	@PostMapping("/{userId}/items")
	public ResponseEntity<ApiResponseDto<CartResponseDto>> addItemToCart(@PathVariable Long userId, @Valid @RequestBody AddToCartRequestDto dto){
		
		CartResponseDto cartDto= serv.addItemToCart(userId, dto);
		
		return ResponseEntity.ok(ApiResponseDto.success("Item added Successfully", cartDto));
	}
	
	// Update the quantity of a specific cart item.--->
	@Operation(
		    summary = "Update cart item quantity",
		    description = "Updates the quantity of a specific cart item in the user's shopping cart."
	)
	@PutMapping("/{userId}/items/{cartItemId}")
	public ResponseEntity<ApiResponseDto<CartResponseDto>> updateCartItem(@PathVariable Long userId, @PathVariable Long cartItemId, @Valid @RequestBody UpdateCartItemRequestDto dto){
		
		CartResponseDto cartDto= serv.updateCartItem(userId, cartItemId, dto);
		
		return ResponseEntity.ok(ApiResponseDto.success("Item updated Successfully", cartDto));
	}
	
	// Remove a specific item from the user's cart.--->
	@Operation(
		    summary = "Remove item from cart",
		    description = "Removes a specific item from the user's shopping cart."
	)
	@DeleteMapping("/{userId}/items/{cartItemId}")
	public ResponseEntity<ApiResponseDto<CartResponseDto>> removeItemFromCart(@PathVariable Long userId, @PathVariable Long cartItemId){
		
		CartResponseDto cartDto= serv.removeItemFromCart(userId, cartItemId);
		
		return ResponseEntity.ok(ApiResponseDto.success("Item removed Successfully", cartDto));
	}
	
	// Remove all items from the user's cart.--->
	@Operation(
		    summary = "Clear shopping cart",
		    description = "Removes all items from the specified user's shopping cart."
	)
	@DeleteMapping("/{userId}/clear")
	public ResponseEntity<ApiResponseDto<CartResponseDto>> clearCart(@PathVariable Long userId){
		
		CartResponseDto cartDto= serv.clearCart(userId);
		
		return ResponseEntity.ok(ApiResponseDto.success("Cart Cleared Successfully", cartDto));
	}
	
	// Retrieve details of a specific cart item.--->
	@Operation(
		    summary = "Get cart item",
		    description = "Retrieves the details of a specific cart item from the user's shopping cart."
	)
	@GetMapping("/{userId}/items/{cartItemId}")
	public ResponseEntity<ApiResponseDto<CartItemResponseDto>> getCartItem(@PathVariable Long userId, @PathVariable Long cartItemId){
		
		CartItemResponseDto dto= serv.getCartItem(userId, cartItemId);
		
		return ResponseEntity.ok(ApiResponseDto.success(dto));
	}
	
	// Check whether a product exists in the user's cart.--->
	@Operation(
		    summary = "Check product in cart",
		    description = "Checks whether a specific product already exists in the user's shopping cart."
	)
	@GetMapping("/{userId}/check-product/{productId}")
	public ResponseEntity<ApiResponseDto<Boolean>> isProductInCart(@PathVariable Long userId, @PathVariable Long productId){
		
		boolean inCart= serv.isProductInCart(userId, productId);
		
		return ResponseEntity.ok(ApiResponseDto.success(inCart ? "Product is in the Cart" : "Product is not in the Cart", inCart));
	}
	
	// Increase the quantity of a specific product in the user's cart.--->
	@Operation(
		    summary = "Increase cart item quantity",
		    description = "Increases the quantity of a specific product in the user's shopping cart by the specified amount."
	)
	@PatchMapping("/{userId}/products/{productId}/increment")
	public ResponseEntity<ApiResponseDto<CartResponseDto>> incrementItemQuantity(@PathVariable Long userId, @PathVariable Long productId, @RequestParam(defaultValue= "1") int quantity){
		
		CartResponseDto cartDto= serv.incrementItemQuantity(userId, productId, quantity);
		
		return ResponseEntity.ok(ApiResponseDto.success("Quantity incremented Successfully", cartDto));
	}
	
	// Decrease the quantity of a specific product in the user's cart.--->
	@Operation(
		    summary = "Decrease cart item quantity",
		    description = "Decreases the quantity of a specific product in the user's shopping cart by the specified amount. If the quantity reaches zero, the item may be removed from the cart."
	)
	@PatchMapping("/{userId}/products/{productId}/decrement")
	public ResponseEntity<ApiResponseDto<CartResponseDto>> decrementItemQuantity(@PathVariable Long userId, @PathVariable Long productId, @RequestParam(defaultValue= "1") int quantity){
		
		CartResponseDto cartDto= serv.decrementItemQuantity(userId, productId, quantity);
		
		return ResponseEntity.ok(ApiResponseDto.success("Quantity decrement Successfully", cartDto));
	}

}
