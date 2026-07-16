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

	@Override
	public CartResponseDto getCartByUserId(Long userId) {
		
		Cart cart= getCart(userId);
		return mapToCartResponseDto(cart);
	}

	@Override
	public CartResponseDto addItemToCart(Long userId, AddToCartRequestDto dto) {
		//Get Cart
		Cart cart= getCart(userId);
		
		//Fetch the Product
		Product product= findProductById(dto.getProductId());
		
		//Validate the Qty
		validateProductAvailability(product, dto.getQuantity());
		Optional<CartItem> opt= cartItemRepo.findByCartIdAndProductId(cart.getId(), product.getId());
		
		if(opt.isPresent()) {
			CartItem cartItem= opt.get();
			int newQty= cartItem.getQuantity() + dto.getQuantity();
			validateProductAvailability(product, newQty);
			cartItem.setQuantity(newQty);
			cartItem.calculateSubtotal();
			cartItemRepo.save(cartItem);
		}
		else {
			CartItem cartItem= CartItem.builder()
					.product(product)
					.quantity(dto.getQuantity())
					.unitPrice(product.getPrice())
					.build();
			
			cartItem.calculateSubtotal();
			cart.addCartItem(cartItem);
		}
		cartRepo.save(cart);
		return mapToCartResponseDto(cart);
	}

	@Override
	public CartResponseDto updateCartItem(Long userId, Long cartItemId, UpdateCartItemRequestDto dto) {
		
		Cart cart= getCart(userId);
		
		CartItem cartItem= findCartItemById(cartItemId);
		
		if(! cartItem.getCart().getId().equals(cart.getId())) {
			throw new ResourceNotFoundException("CartItem", "id", cartItem);
		}
		validateProductAvailability(cartItem.getProduct(), dto.getQuantity());
		cartItem.setQuantity(dto.getQuantity());
		cartItem.calculateSubtotal();
		cart.reCalculateTotals();
		cartRepo.save(cart);
		
		return mapToCartResponseDto(cart);
	}

	@Override
	public CartResponseDto removeItemFromCart(Long userId, Long cartItemId) {

		Cart cart= getCart(userId);
		CartItem cartItem= findCartItemById(cartItemId);
		
		if(! cartItem.getCart().getId().equals(cart.getId())) {
			throw new ResourceNotFoundException("CartItem", "id", cartItem);
		}
		
		cart.removeCartItems(cartItem);
		cart.reCalculateTotals();
		cartRepo.save(cart);
		
		return mapToCartResponseDto(cart);
	}

	@Override
	public CartResponseDto clearCart(Long userId) {

		Cart cart= getCart(userId);
		cart.clearCart();
		cartRepo.save(cart);
		return mapToCartResponseDto(cart);
	}

	@Override
	public CartItemResponseDto getCartItem(Long userId, Long cartItemId) {
		
		Cart cart= getCart(userId);
		CartItem cartItem= findCartItemById(cartItemId);
		
		
		if(! cartItem.getCart().getId().equals(cart.getId())) {
			throw new ResourceNotFoundException("CartItem", "id", cartItem);
		}
		
		return mapToCartItemResponseDto(cartItem);
	}

	@Override
	public boolean isProductInCart(Long userId, Long productId) {
		
		Optional<Cart> opt= cartRepo.findByUserId(userId);
		
		if(! opt.isPresent()) {
			return false;
		}
		Cart cart= opt.get();
		return cartItemRepo.existsByCartIdAndProductId(cart.getId(), productId);
	}

	@Override
	public CartResponseDto incrementItemQuantity(Long userId, Long productId, int quantity) {
		
		if(quantity <=0) { 
			throw new IllegalArgumentException("Quantity must be Positive."); 
		} 
		Cart cart= getCart(userId); 
		Optional<CartItem> opt= cartItemRepo.findByCartIdAndProductId(cart.getId(), productId);
		
		if(! opt.isPresent()) { 
			throw new ResourceNotFoundException("CartItem", "productId", productId); 
		}
		
		CartItem cartItem= opt.get(); 
		int newQty= cartItem.getQuantity() + quantity; 
		validateProductAvailability(cartItem.getProduct(), newQty); 
		cartItem.setQuantity(newQty); 
		cartItem.calculateSubtotal(); 
		cart.reCalculateTotals(); 
		cartRepo.save(cart); 
		return mapToCartResponseDto(cart);
	}

	@Override
	public CartResponseDto decrementItemQuantity(Long userId, Long productId, int quantity) {
		
		if(quantity <=0) { 
			throw new IllegalArgumentException("Quantity must be Positive."); 
		} 
		
		Cart cart= getCart(userId); 
		Optional<CartItem> opt= cartItemRepo.findByCartIdAndProductId(cart.getId(), productId);
		
		if(! opt.isPresent()) { 
			throw new ResourceNotFoundException("CartItem", "productid", productId); 
		}
		
		CartItem cartItem= opt.get(); 
		int newQty= cartItem.getQuantity() - quantity; 
		
		if(newQty <=0) {
			cart.removeCartItems(cartItem);
		}
		else {
			cartItem.setQuantity(newQty); 
			cartItem.calculateSubtotal(); 
		}
		
		cart.reCalculateTotals(); 
		cartRepo.save(cart); 
		return mapToCartResponseDto(cart);
	}

	
	
}
