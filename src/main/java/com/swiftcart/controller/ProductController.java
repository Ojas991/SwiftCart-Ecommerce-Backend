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

import com.swiftcart.dto.request.ProductRequestDTO;
import com.swiftcart.dto.request.UpdateProductRequestDTO;
import com.swiftcart.dto.response.ApiResponseDto;
import com.swiftcart.dto.response.PageResponseDTO;
import com.swiftcart.dto.response.ProductResponseDTO;
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
	public ResponseEntity<ApiResponseDto<ProductResponseDTO>> createProduct(@Valid @RequestBody ProductRequestDTO dto){
		ProductResponseDTO respDTO= serv.createProduct(dto);
		
		return new ResponseEntity<>(ApiResponseDto.success("Product Created Successfully", respDTO), HttpStatus.CREATED);
	}
	
	//Get Product By Id--->
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseDto<ProductResponseDTO>> getProductById(@PathVariable Long id){
		
		ProductResponseDTO respDTO = serv.getProductById(id);
		
		return ResponseEntity.ok(ApiResponseDto.success(respDTO));
	}
	
	//Get Product By Sku--->
	@GetMapping("/sku/{sku}")
	public ResponseEntity<ApiResponseDto<ProductResponseDTO>> getProductBySku(@PathVariable String sku){
		
		ProductResponseDTO respDTO = serv.getProductBySku(sku);
		
		return ResponseEntity.ok(ApiResponseDto.success(respDTO));
	}
	
	//Get All Products--->
	@GetMapping("/all")
	public ResponseEntity<ApiResponseDto<List<ProductResponseDTO>>> getAllProducts(){
		List<ProductResponseDTO> pList= serv.getAllProducts();
		
		return ResponseEntity.ok(ApiResponseDto.success("fetched : " + pList.size() + " products", pList));
	}
	
	//Get All Products Paginated--->
	@GetMapping
	public ResponseEntity<ApiResponseDto<PageResponseDTO<ProductResponseDTO>>> getAllProductsPaginated(@RequestParam(defaultValue="0") int page, 
			                @RequestParam(defaultValue="10") int size, 
			                @RequestParam(defaultValue="id") String sortBy, @RequestParam(defaultValue="asc") String sortDir){
		
		PageResponseDTO<ProductResponseDTO> products= serv.getAllProductsPaginated(page, size, sortBy, sortDir);
		
		return ResponseEntity.ok(ApiResponseDto.success(products));
		
	}
	
	//Get Available Products--->
	@GetMapping("/available")
	public ResponseEntity<ApiResponseDto<List<ProductResponseDTO>>> getAvailableProducts(){
		List<ProductResponseDTO> pList= serv.getAvailableProducts();
		
		return ResponseEntity.ok(ApiResponseDto.success(pList));
	}

	//Get Products By Category--->
	@GetMapping("/category/{category}")
	public ResponseEntity<ApiResponseDto<List<ProductResponseDTO>>> getProductsByCategory(@PathVariable String category){
		List<ProductResponseDTO> pList= serv.getProductsByCategory(category);
		
		return ResponseEntity.ok(ApiResponseDto.success(pList));
	}
	
	//Get Products By Price Range--->
	@GetMapping("/price-range")
	public ResponseEntity<ApiResponseDto<List<ProductResponseDTO>>> getProductsByPriceRange(@RequestParam double min, @RequestParam double max){
		List<ProductResponseDTO> pList= serv.getProductsByPriceRange(min, max);
		
		return ResponseEntity.ok(ApiResponseDto.success(pList));
	}
	
	//Search Products--->
	@GetMapping("/search")
	public ResponseEntity<ApiResponseDto<PageResponseDTO<ProductResponseDTO>>> searchProducts(@RequestParam String keyword, 
			       @RequestParam(defaultValue="0") int page, 
			       @RequestParam(defaultValue="10") int size){
		
		PageResponseDTO<ProductResponseDTO> products= serv.searchProducts(keyword, page, size);
		
		return ResponseEntity.ok(ApiResponseDto.success(products));
	}
	
	//Update Product--->
	@PutMapping("/update/{id}")
	public ResponseEntity<ApiResponseDto<ProductResponseDTO>> updateProduct(@PathVariable Long id, 
			                                        @Valid @RequestBody UpdateProductRequestDTO dto){
		ProductResponseDTO respDTO= serv.updateProduct(id, dto);
		
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
	public ResponseEntity<ApiResponseDto<List<ProductResponseDTO>>> getLowStockProducts(@RequestParam(defaultValue= "10") Integer threshold){
		List<ProductResponseDTO> pList= serv.getLowStockProducts(threshold);
		
		return ResponseEntity.ok(ApiResponseDto.success(pList));
	}
	
	//Check sku Exists--->
	@GetMapping("/check-sku/{sku}")
	public ResponseEntity<ApiResponseDto<Boolean>> checkSkuExists(@PathVariable String sku){
		boolean exists= serv.existsBySku(sku);
		
		return ResponseEntity.ok(ApiResponseDto.success(exists ? "sku Already Exists" : "Sku is Available", exists));
	}
}
