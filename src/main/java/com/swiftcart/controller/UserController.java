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

import com.swiftcart.dto.request.UpdateUserRequestDto;
import com.swiftcart.dto.request.UserRequestDto;
import com.swiftcart.dto.response.ApiResponseDto;
import com.swiftcart.dto.response.UserResponseDto;
import com.swiftcart.service.UserService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(
		name= "User Controller",
		description= "APIs for managing Users."
)
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
	@Operation(
		    summary = "Create User",
		    description = "Creates a new user in the SwiftCart system."
	)
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "201", description = "User created successfully"),
		    @ApiResponse(responseCode = "400", description = "Invalid request data"),
		    @ApiResponse(responseCode = "409", description = "Email already exists"),
		    @ApiResponse(responseCode = "500", description = "Internal server error")
		})
	@PostMapping
	public ResponseEntity<ApiResponseDto<UserResponseDto>> createUser(@Valid @RequestBody UserRequestDto userRequest) {
		
		UserResponseDto dto = serv.createUser(userRequest);
		
		ApiResponseDto<UserResponseDto>  obj = ApiResponseDto.success("User Created Successfully", dto);
		
		return new ResponseEntity<>(obj, HttpStatus.CREATED);
	}
	
	//Get User By Id--->
	@Operation(
		    summary = "Get User By ID",
		    description = "Retrieves a user's details using the unique user ID."
	)
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
		    @ApiResponse(responseCode = "404", description = "User not found"),
		    @ApiResponse(responseCode = "500", description = "Internal server error")
		})
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseDto<UserResponseDto>> getUserById(@PathVariable Long id) {
		
		UserResponseDto dto = serv.getUserById(id);
		
		ApiResponseDto<UserResponseDto>  obj = ApiResponseDto.success(dto);
		
		return ResponseEntity.ok(obj);
	}
	
	//Get User By Email--->
	@Operation(
		    summary = "Get User By Email",
		    description = "Retrieves a user's details using the registered email address."
	)
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
		    @ApiResponse(responseCode = "404", description = "User not found"),
		    @ApiResponse(responseCode = "500", description = "Internal server error")
		})
	@GetMapping("/email/{email}")
	public ResponseEntity<ApiResponseDto<UserResponseDto>> getUserByEmail(@PathVariable String email) {
		
		UserResponseDto dto = serv.getUserByEmail(email);
		
		ApiResponseDto<UserResponseDto>  obj = ApiResponseDto.success(dto);
		
		return ResponseEntity.ok(obj);
	}
	
	//Get All Users--->
	@Operation(
		    summary = "Get All Users",
		    description = "Returns a list of all registered users in the system."
	)
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
		    @ApiResponse(responseCode = "500", description = "Internal server error")
		})
	@GetMapping
	public ResponseEntity<ApiResponseDto<List<UserResponseDto>>> getAllUsers() {
		
		List<UserResponseDto> users = serv.getAllUsers();
		
		ApiResponseDto<List<UserResponseDto>>  obj = ApiResponseDto.success(users);
		
		return ResponseEntity.ok(obj);
	}
	
	//Get Active Users--->
	@Operation(
		    summary = "Get Active Users",
		    description = "Returns a list of all active users."
	)
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "Active users retrieved successfully"),
		    @ApiResponse(responseCode = "500", description = "Internal server error")
		})
	@GetMapping("/active")
	public ResponseEntity<ApiResponseDto<List<UserResponseDto>>> getActiveUsers() {
		
		List<UserResponseDto> users = serv.getActiveUsers();
		
		ApiResponseDto<List<UserResponseDto>>  obj = ApiResponseDto.success(users);
		
		return ResponseEntity.ok(obj);
	}
	
	//Update A User--->
	@Operation(
		    summary = "Update User",
		    description = "Updates the information of an existing user using the user ID."
	)
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "User updated successfully"),
		    @ApiResponse(responseCode = "400", description = "Invalid request data"),
		    @ApiResponse(responseCode = "404", description = "User not found"),
		    @ApiResponse(responseCode = "500", description = "Internal server error")
		})
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponseDto<UserResponseDto>> updateUser(@PathVariable Long id,@Valid @RequestBody UpdateUserRequestDto userRequest) {
		
		UserResponseDto dto = serv.updateUser(id, userRequest);
		
		ApiResponseDto<UserResponseDto>  obj = ApiResponseDto.success("User updated Successfully", dto);
		
		return ResponseEntity.ok(obj);
	}
	
	//Activate User--->
	@Operation(
		    summary = "Activate User",
		    description = "Activates a user account by changing its status to active."
	)
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "User activated successfully"),
		    @ApiResponse(responseCode = "404", description = "User not found"),
		    @ApiResponse(responseCode = "500", description = "Internal server error")
		})
	@PatchMapping("/{id}/activate")
	public ResponseEntity<ApiResponseDto<Void>> activateUser(@PathVariable Long id) {
		
	    serv.activateUser(id);
		
		return ResponseEntity.ok(ApiResponseDto.success("User activated Successfully"));
	}
	
	//De-Activate User--->
	@Operation(
		    summary = "Deactivate User",
		    description = "Deactivates a user account by changing its status to inactive."
	)
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "User deactivated successfully"),
		    @ApiResponse(responseCode = "404", description = "User not found"),
		    @ApiResponse(responseCode = "500", description = "Internal server error")
		})
	@PatchMapping("/{id}/deactivate")
	public ResponseEntity<ApiResponseDto<Void>> deActivateUser(@PathVariable Long id) {
		
	    serv.deActivateUser(id);
		
		return ResponseEntity.ok(ApiResponseDto.success("User de-activated Successfully"));
	}
	
	//Search User--->
	
	@Operation(
		    summary = "Search Users",
		    description = "Searches users based on the provided keyword such as name or email."
	)
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "Search completed successfully"),
		    @ApiResponse(responseCode = "500", description = "Internal server error")
		})
	@GetMapping("/search")
	public ResponseEntity<ApiResponseDto<List<UserResponseDto>>> searchUsers(@RequestParam String keyword) {
		
		List<UserResponseDto> users = serv.searchUser(keyword);
		
		ApiResponseDto<List<UserResponseDto>>  obj = ApiResponseDto.success(users);
		
		return ResponseEntity.ok(obj);
	}
	
	//Check Email Exists or Not--->
	
	@GetMapping("/check-email")
	@Operation(
		    summary = "Check Email Availability",
		    description = "Checks whether the provided email address is already registered in the system."
	)
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "Email availability checked successfully"),
		    @ApiResponse(responseCode = "500", description = "Internal server error")
		})
	public ResponseEntity<ApiResponseDto<Boolean>> checkEmailExists(@RequestParam String email) {
		
		Boolean res= serv.existsByEmail(email);
		
		ApiResponseDto<Boolean> obj = ApiResponseDto.success(res ? "Email Already Exists" : "Email is Available for use");
		
		return ResponseEntity.ok(obj);
	}
	
}
