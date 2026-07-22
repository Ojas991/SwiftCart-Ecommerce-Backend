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

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(
	    name = "Product Management",
	    description = "APIs for managing products including creation, retrieval, searching, updating, stock management, and SKU validation."
	)
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
	@Operation(
		    summary = "Create a new product",
		    description = "Creates a new product and stores it in the database."
	)
	@ApiResponses({
		    @ApiResponse(responseCode = "201", description = "Product created successfully"),
		    @ApiResponse(responseCode = "400", description = "Invalid request data"),
		    @ApiResponse(responseCode = "409", description = "SKU already exists"),
		    @ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PostMapping
	public ResponseEntity<ApiResponseDto<ProductResponseDto>> createProduct(@Valid @RequestBody ProductRequestDto dto){
		ProductResponseDto respDTO= serv.createProduct(dto);
		
		return new ResponseEntity<>(ApiResponseDto.success("Product Created Successfully", respDTO), HttpStatus.CREATED);
	}
	
	//Get Product By Id--->
	@Operation(
			summary = "Get product by ID",
		    description = "Retrieves a product using its unique identifier."
	)
	@ApiResponses({
		    @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
		    @ApiResponse(responseCode = "404", description = "Product not found")
	})
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseDto<ProductResponseDto>> getProductById(@PathVariable Long id){
		
		ProductResponseDto respDTO = serv.getProductById(id);
		
		return ResponseEntity.ok(ApiResponseDto.success(respDTO));
	}
	
	//Get Product By Sku--->
	@Operation(
		    summary = "Get product by SKU",
		    description = "Retrieves a product using its Stock Keeping Unit."
	)
	@ApiResponses({
		    @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
		    @ApiResponse(responseCode = "404", description = "Product not found")
	})
	@GetMapping("/sku/{sku}")
	public ResponseEntity<ApiResponseDto<ProductResponseDto>> getProductBySku(@PathVariable String sku){
		
		ProductResponseDto respDTO = serv.getProductBySku(sku);
		
		return ResponseEntity.ok(ApiResponseDto.success(respDTO));
	}
	
	//Get All Products--->
	@Operation(
		    summary = "Get all products",
		    description = "Retrieves all available products."
	)
	@ApiResponses({
		    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
	})
	@GetMapping("/all")
	public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> getAllProducts(){
		List<ProductResponseDto> pList= serv.getAllProducts();
		
		return ResponseEntity.ok(ApiResponseDto.success("fetched : " + pList.size() + " products", pList));
	}
	
	//Get All Products Paginated--->
	@Operation(
		    summary = "Get paginated products",
		    description = "Retrieves products with pagination, sorting, and ordering."
	)
	@ApiResponses({
		    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
	})
	@GetMapping
	public ResponseEntity<ApiResponseDto<PageResponseDto<ProductResponseDto>>> getAllProductsPaginated(@RequestParam(defaultValue="0") int page, 
			                @RequestParam(defaultValue="10") int size, 
			                @RequestParam(defaultValue="id") String sortBy, @RequestParam(defaultValue="asc") String sortDir){
		
		PageResponseDto<ProductResponseDto> products= serv.getAllProductsPaginated(page, size, sortBy, sortDir);
		
		return ResponseEntity.ok(ApiResponseDto.success(products));
		
	}
	
	//Get Available Products--->
	@Operation(
		    summary = "Get available products",
		    description = "Retrieves all products currently available for purchase."
	)
	@GetMapping("/available")
	public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> getAvailableProducts(){
		List<ProductResponseDto> pList= serv.getAvailableProducts();
		
		return ResponseEntity.ok(ApiResponseDto.success(pList));
	}

	//Get Products By Category--->
	@Operation(
		    summary = "Get products by category",
		    description = "Retrieves all products belonging to a specific category."
	)
	@GetMapping("/category/{category}")
	public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> getProductsByCategory(@PathVariable String category){
		List<ProductResponseDto> pList= serv.getProductsByCategory(category);
		
		return ResponseEntity.ok(ApiResponseDto.success(pList));
	}
	
	//Get Products By Price Range--->
	@Operation(
		    summary = "Get products by price range",
		    description = "Retrieves products within the specified minimum and maximum price range."
	)
	@GetMapping("/price-range")
	public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> getProductsByPriceRange(@RequestParam double min, @RequestParam double max){
		List<ProductResponseDto> pList= serv.getProductsByPriceRange(min, max);
		
		return ResponseEntity.ok(ApiResponseDto.success(pList));
	}
	
	//Search Products--->
	@Operation(
		    summary = "Search products",
		    description = "Searches products by keyword with pagination support."
	)
	@GetMapping("/search")
	public ResponseEntity<ApiResponseDto<PageResponseDto<ProductResponseDto>>> searchProducts(@RequestParam String keyword, 
			       @RequestParam(defaultValue="0") int page, 
			       @RequestParam(defaultValue="10") int size){
		
		PageResponseDto<ProductResponseDto> products= serv.searchProducts(keyword, page, size);
		
		return ResponseEntity.ok(ApiResponseDto.success(products));
	}
	
	//Update Product--->
	@Operation(
		    summary = "Update product",
		    description = "Updates the details of an existing product."
	)
	@ApiResponses({
		    @ApiResponse(responseCode = "200", description = "Product updated successfully"),
		    @ApiResponse(responseCode = "404", description = "Product not found"),
		    @ApiResponse(responseCode = "400", description = "Invalid request data")
	})
	@PutMapping("/update/{id}")
	public ResponseEntity<ApiResponseDto<ProductResponseDto>> updateProduct(@PathVariable Long id, 
			                                        @Valid @RequestBody UpdateProductRequestDto dto){
		ProductResponseDto respDTO= serv.updateProduct(id, dto);
		
		return ResponseEntity.ok(ApiResponseDto.success("Product Updated Successfully "+respDTO));
	}
	
	//Update Stock--->
	@Operation(
		    summary = "Update product stock",
		    description = "Updates the stock quantity of a product."
	)
	@PatchMapping("/{id}/stock")
	public ResponseEntity<ApiResponseDto<Void>> updateStock(@PathVariable Long id, @RequestParam Integer quantity){
		serv.updateStock(id, quantity);
		
		return ResponseEntity.ok(ApiResponseDto.success("Stock Updated Successfully"));
	}
	
	//Get Low Stock Products--->
	@Operation(
		    summary = "Get low stock products",
		    description = "Retrieves products whose stock is below the specified threshold."
	)
	@GetMapping("/low-stock")
	public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> getLowStockProducts(@RequestParam(defaultValue= "10") Integer threshold){
		List<ProductResponseDto> pList= serv.getLowStockProducts(threshold);
		
		return ResponseEntity.ok(ApiResponseDto.success(pList));
	}
	
	//Check sku Exists--->
	@Operation(
		    summary = "Check SKU availability",
		    description = "Checks whether the given SKU already exists."
	)
	@GetMapping("/check-sku/{sku}")
	public ResponseEntity<ApiResponseDto<Boolean>> checkSkuExists(@PathVariable String sku){
		boolean exists= serv.existsBySku(sku);
		
		return ResponseEntity.ok(ApiResponseDto.success(exists ? "sku Already Exists" : "Sku is Available", exists));
	}
}
