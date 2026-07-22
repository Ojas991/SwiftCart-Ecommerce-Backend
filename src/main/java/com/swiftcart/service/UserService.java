package com.swiftcart.service;

import java.util.List;

import com.swiftcart.dto.request.UpdateUserRequestDto;
import com.swiftcart.dto.request.UserRequestDto;
import com.swiftcart.dto.response.UserResponseDto;

public interface UserService {

	public UserResponseDto createUser(UserRequestDto userRequestDto);
	
	public UserResponseDto getUserById(Long id);
	
	public UserResponseDto getUserByEmail(String email);
	
	public List<UserResponseDto> getAllUsers();
	
	public List<UserResponseDto> getActiveUsers();
	
	public UserResponseDto updateUser(Long id, UpdateUserRequestDto userRequest);
	
	public void activateUser(Long id);
	
	public void deActivateUser(Long id);
	
	public List<UserResponseDto> searchUser(String keyword);
	
	public boolean existsByEmail(String email);
}
