package com.swiftcart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swiftcart.dto.request.ProductRequestDTO;
import com.swiftcart.dto.response.ApiResponseDTO;
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
	public ResponseEntity<ApiResponseDTO<ProductResponseDTO>> createProduct(@Valid @RequestBody ProductRequestDTO dto){
		ProductResponseDTO respDTO= serv.createProduct(dto);
		
		return new ResponseEntity<>(ApiResponseDTO.success("Product Created Successfully", respDTO), HttpStatus.CREATED);
	}
	
	//Get Product By Id--->
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseDTO<ProductResponseDTO>> getProductById(@PathVariable Long id){
		
		ProductResponseDTO respDTO = serv.getProductById(id);
		
		return ResponseEntity.ok(ApiResponseDTO.success(respDTO));
	}
	
	//Get Product By Sku--->
	@GetMapping("/sku/{sku}")
	public ResponseEntity<ApiResponseDTO<ProductResponseDTO>> getProductBySku(@PathVariable String sku){
		
		ProductResponseDTO respDTO = serv.getProductBySku(sku);
		
		return ResponseEntity.ok(ApiResponseDTO.success(respDTO));
	}

}
