package com.swiftcart.service;

import java.util.List;

import com.swiftcart.dto.request.ProductRequestDto;
import com.swiftcart.dto.request.UpdateProductRequestDto;
import com.swiftcart.dto.response.PageResponseDto;
import com.swiftcart.dto.response.ProductResponseDto;

public interface ProductService {
	ProductResponseDto createProduct(ProductRequestDto productRequestDTO);
	
	ProductResponseDto getProductById(Long id);
	
	ProductResponseDto getProductBySku(String sku);
	
	List<ProductResponseDto> getAllProducts();
	
	PageResponseDto<ProductResponseDto> getAllProductsPaginated(
	            int page,
	            int size,
	            String sortBy,
	            String sortDir
	    );
	
	List<ProductResponseDto> getAvailableProducts();
	
	List<ProductResponseDto> getProductsByCategory(String category);
	
    List<ProductResponseDto> getProductsByPriceRange(
            double minPrice,
            double maxPrice
    );
    
    PageResponseDto<ProductResponseDto> searchProducts(
            String keyword,
            int page,
            int size
    );

    ProductResponseDto updateProduct(
            Long id,
            UpdateProductRequestDto productRequestDTO
    );
    
    void updateStock(
            Long productId,
            Integer quantity
    );

    List<ProductResponseDto> getLowStockProducts(
            Integer threshold
    );

    boolean existsBySku(String sku);
}
