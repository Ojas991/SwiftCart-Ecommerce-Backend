package com.swiftcart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.swiftcart.dto.request.AddToCartRequestDto;
import com.swiftcart.dto.request.UpdateCartItemRequestDto;
import com.swiftcart.dto.response.CartItemResponseDto;
import com.swiftcart.dto.response.CartResponseDto;
import com.swiftcart.entity.Cart;
import com.swiftcart.entity.CartItem;
import com.swiftcart.entity.Product;
import com.swiftcart.exception.InsufficientStockException;
import com.swiftcart.exception.ResourceNotFoundException;
import com.swiftcart.repository.CartItemRepository;
import com.swiftcart.repository.CartRepository;
import com.swiftcart.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartServiceImpl implements CartService{

	private CartRepository cartRepo;
	
	private ProductRepository prodRepo;
	
	private CartItemRepository cartItemRepo;

	public CartServiceImpl(CartRepository cartRepo, ProductRepository prodRepo, CartItemRepository cartItemRepo) {
		super();
		this.cartRepo = cartRepo;
		this.prodRepo = prodRepo;
		this.cartItemRepo = cartItemRepo;
	}
	
	//<----Helper Method---->
	
	private Cart getCart(Long userId) {
		
		Optional<Cart> opt= cartRepo.findByUserId(userId);
		
		if(opt.isEmpty()) {
			throw new ResourceNotFoundException("Cart", "userId", userId);
		}
		return opt.get();
	}
	
	private Product findProductById(Long prodId) {
		Optional<Product> opt= prodRepo.findById(prodId);
		
		if(opt.isEmpty()) {
			throw new ResourceNotFoundException("Product", "prodId", prodId);
		}
		return opt.get();
	}
	
	private CartItem findCartItemById(Long cartItemId) {
		
		Optional<CartItem> opt= cartItemRepo.findById(cartItemId);
		
		if(opt.isEmpty()) {
			throw new ResourceNotFoundException("CartItem", "cartItemId", cartItemId);
		}
		return opt.get();
	}
	
	private void validateProductAvailability(Product product, int requestQty) {
		
		if(! product.getIsAvailable()) {
			throw new InsufficientStockException(product.getName() + " is not Available.");
		}
		
		if(product.getStockQuantity() < requestQty) {
			throw new InsufficientStockException("Insufficient Stock for " + product.getName() + 
													". Available : " + product.getStockQuantity() + 
														", Requested : " + requestQty);
		}
	}
	
	private CartResponseDto mapToCartResponseDto(Cart cart) {
		
		List<CartItemResponseDto> items= new ArrayList<CartItemResponseDto>();
		
		for(CartItem item : cart.getCartItems()) {
			items.add(mapToCartItemResponseDto(item));
		}
		
		return CartResponseDto.builder()
				.id(cart.getId())
				.userId(cart.getUser().getId())
				.userName(cart.getUser().getFullName())
				.items(items)
				.totalItems(cart.getTotalItems())
				.totalAmount(cart.getTotalAmmount())
				.createdAt(cart.getCreatedAt())
				.updatedAt(cart.getUpdatedAt())
				.build();
	}

	private CartItemResponseDto mapToCartItemResponseDto(CartItem item) {
			
		Product product= item.getProduct();
		
		return CartItemResponseDto.builder()
				.id(item.getId())
				.productId(product.getId())
				.productName(product.getName())
				.productImage(product.getImageUrl())
				.productSku(product.getSku())
				.unitPrice(product.getPrice())
				.quantity(item.getQuantity())
				.subtotal(item.getSubTotal())
				.availableStock(product.getStockQuantity())
				.addedAt(item.getAddedAt())
				.build();
	}

}
