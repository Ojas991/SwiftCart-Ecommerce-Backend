package com.swiftcart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swiftcart.dto.request.ProductRequestDto;
import com.swiftcart.dto.request.UpdateProductRequestDto;
import com.swiftcart.dto.response.ApiResponseDto;
import com.swiftcart.dto.response.PageResponseDto;
import com.swiftcart.dto.response.ProductResponseDto;
import com.swiftcart.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class ProductController {
	
	private ProductService serv;

	@Autowired
	public ProductController(ProductService serv) {
		this.serv = serv;
	}
	
	//Create Product--->
	@PostMapping
	public ResponseEntity<ApiResponseDto<ProductResponseDto>> createProduct(@Valid @RequestBody ProductRequestDto dto){
		ProductResponseDto respDTO= serv.createProduct(dto);
		
		return new ResponseEntity<>(ApiResponseDto.success("Product Created Successfully", respDTO), HttpStatus.CREATED);
	}
	
	//Get Product By Id--->
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseDto<ProductResponseDto>> getProductById(@PathVariable Long id){
		
		ProductResponseDto respDTO = serv.getProductById(id);
		
		return ResponseEntity.ok(ApiResponseDto.success(respDTO));
	}
	
	//Get Product By Sku--->
	@GetMapping("/sku/{sku}")
	public ResponseEntity<ApiResponseDto<ProductResponseDto>> getProductBySku(@PathVariable String sku){
		
		ProductResponseDto respDTO = serv.getProductBySku(sku);
		
		return ResponseEntity.ok(ApiResponseDto.success(respDTO));
	}
	
	//Get All Products--->
	@GetMapping("/all")
	public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> getAllProducts(){
		List<ProductResponseDto> pList= serv.getAllProducts();
		
		return ResponseEntity.ok(ApiResponseDto.success("fetched : " + pList.size() + " products", pList));
	}
	
	//Get All Products Paginated--->
	@GetMapping
	public ResponseEntity<ApiResponseDto<PageResponseDto<ProductResponseDto>>> getAllProductsPaginated(@RequestParam(defaultValue="0") int page, 
			                @RequestParam(defaultValue="10") int size, 
			                @RequestParam(defaultValue="id") String sortBy, @RequestParam(defaultValue="asc") String sortDir){
		
		PageResponseDto<ProductResponseDto> products= serv.getAllProductsPaginated(page, size, sortBy, sortDir);
		
		return ResponseEntity.ok(ApiResponseDto.success(products));
		
	}
	
	//Get Available Products--->
	@GetMapping("/available")
	public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> getAvailableProducts(){
		List<ProductResponseDto> pList= serv.getAvailableProducts();
		
		return ResponseEntity.ok(ApiResponseDto.success(pList));
	}

	//Get Products By Category--->
	@GetMapping("/category/{category}")
	public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> getProductsByCategory(@PathVariable String category){
		List<ProductResponseDto> pList= serv.getProductsByCategory(category);
		
		return ResponseEntity.ok(ApiResponseDto.success(pList));
	}
	
	//Get Products By Price Range--->
	@GetMapping("/price-range")
	public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> getProductsByPriceRange(@RequestParam double min, @RequestParam double max){
		List<ProductResponseDto> pList= serv.getProductsByPriceRange(min, max);
		
		return ResponseEntity.ok(ApiResponseDto.success(pList));
	}
	
	//Search Products--->
	@GetMapping("/search")
	public ResponseEntity<ApiResponseDto<PageResponseDto<ProductResponseDto>>> searchProducts(@RequestParam String keyword, 
			       @RequestParam(defaultValue="0") int page, 
			       @RequestParam(defaultValue="10") int size){
		
		PageResponseDto<ProductResponseDto> products= serv.searchProducts(keyword, page, size);
		
		return ResponseEntity.ok(ApiResponseDto.success(products));
	}
	
	//Update Product--->
	@PutMapping("/update/{id}")
	public ResponseEntity<ApiResponseDto<ProductResponseDto>> updateProduct(@PathVariable Long id, 
			                                        @Valid @RequestBody UpdateProductRequestDto dto){
		ProductResponseDto respDTO= serv.updateProduct(id, dto);
		
		return ResponseEntity.ok(ApiResponseDto.success("Product Updated Successfully "+respDTO));
	}
	
	//Update Stock--->
	@PatchMapping("/{id}/stock")
	public ResponseEntity<ApiResponseDto<Void>> updateStock(@PathVariable Long id, @RequestParam Integer quantity){
		serv.updateStock(id, quantity);
		
		return ResponseEntity.ok(ApiResponseDto.success("Stock Updated Successfully"));
	}
	
	//Get Low Stock Products--->
	@GetMapping("/low-stock")
	public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> getLowStockProducts(@RequestParam(defaultValue= "10") Integer threshold){
		List<ProductResponseDto> pList= serv.getLowStockProducts(threshold);
		
		return ResponseEntity.ok(ApiResponseDto.success(pList));
	}
	
	//Check sku Exists--->
	@GetMapping("/check-sku/{sku}")
	public ResponseEntity<ApiResponseDto<Boolean>> checkSkuExists(@PathVariable String sku){
		boolean exists= serv.existsBySku(sku);
		
		return ResponseEntity.ok(ApiResponseDto.success(exists ? "sku Already Exists" : "Sku is Available", exists));
	}
}
