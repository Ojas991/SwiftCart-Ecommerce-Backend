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

import com.swiftcart.dto.request.UpdateUserRequestDTO;
import com.swiftcart.dto.request.UserRequestDTO;
import com.swiftcart.dto.response.ApiResponseDto;
import com.swiftcart.dto.response.UserResponseDTO;
import com.swiftcart.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class UserController {
	
	private UserService serv;

	@Autowired
	public UserController(UserService serv) {
		this.serv = serv;
	}
	
	//Create A User--->
	@PostMapping
	public ResponseEntity<ApiResponseDto<UserResponseDTO>> createUser(@Valid @RequestBody UserRequestDTO userRequest) {
		
		UserResponseDTO dto = serv.createUser(userRequest);
		
		ApiResponseDto<UserResponseDTO>  obj = ApiResponseDto.success("User Created Successfully", dto);
		
		return new ResponseEntity<>(obj, HttpStatus.CREATED);
	}
	
	//Get User By Id--->
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseDto<UserResponseDTO>> getUserById(@PathVariable Long id) {
		
		UserResponseDTO dto = serv.getUserById(id);
		
		ApiResponseDto<UserResponseDTO>  obj = ApiResponseDto.success(dto);
		
		return ResponseEntity.ok(obj);
	}
	
	//Get User By Email--->
	@GetMapping("/email/{email}")
	public ResponseEntity<ApiResponseDto<UserResponseDTO>> getUserByEmail(@PathVariable String email) {
		
		UserResponseDTO dto = serv.getUserByEmail(email);
		
		ApiResponseDto<UserResponseDTO>  obj = ApiResponseDto.success(dto);
		
		return ResponseEntity.ok(obj);
	}
	
	//Get All Users--->
	@GetMapping
	public ResponseEntity<ApiResponseDto<List<UserResponseDTO>>> getAllUsers() {
		
		List<UserResponseDTO> users = serv.getAllUsers();
		
		ApiResponseDto<List<UserResponseDTO>>  obj = ApiResponseDto.success(users);
		
		return ResponseEntity.ok(obj);
	}
	
	//Get Active Users--->
	@GetMapping("/active")
	public ResponseEntity<ApiResponseDto<List<UserResponseDTO>>> getActiveUsers() {
		
		List<UserResponseDTO> users = serv.getActiveUsers();
		
		ApiResponseDto<List<UserResponseDTO>>  obj = ApiResponseDto.success(users);
		
		return ResponseEntity.ok(obj);
	}
	
	//Update A User--->
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponseDto<UserResponseDTO>> updateUser(@PathVariable Long id,@Valid @RequestBody UpdateUserRequestDTO userRequest) {
		
		UserResponseDTO dto = serv.updateUser(id, userRequest);
		
		ApiResponseDto<UserResponseDTO>  obj = ApiResponseDto.success("User updated Successfully", dto);
		
		return ResponseEntity.ok(obj);
	}
	
	//Activate User--->
	@PatchMapping("/{id}/activate")
	public ResponseEntity<ApiResponseDto<Void>> activateUser(@PathVariable Long id) {
		
	    serv.activateUser(id);
		
		return ResponseEntity.ok(ApiResponseDto.success("User activated Successfully"));
	}
	
	//De-Activate User--->
	@PatchMapping("/{id}/deactivate")
	public ResponseEntity<ApiResponseDto<Void>> deActivateUser(@PathVariable Long id) {
		
	    serv.deActivateUser(id);
		
		return ResponseEntity.ok(ApiResponseDto.success("User de-activated Successfully"));
	}
	
	//Search User--->
	@GetMapping("/search")
	public ResponseEntity<ApiResponseDto<List<UserResponseDTO>>> searchUsers(@RequestParam String keyword) {
		
		List<UserResponseDTO> users = serv.searchUser(keyword);
		
		ApiResponseDto<List<UserResponseDTO>>  obj = ApiResponseDto.success(users);
		
		return ResponseEntity.ok(obj);
	}
	
	//Check Email Exists or Not--->
	@GetMapping("/check-email")
	public ResponseEntity<ApiResponseDto<Boolean>> checkEmailExists(@RequestParam String email) {
		
		Boolean res= serv.existsByEmail(email);
		
		ApiResponseDto<Boolean> obj = ApiResponseDto.success(res ? "Email Already Exists" : "Email is Available for use");
		
		return ResponseEntity.ok(obj);
	}
	
}
