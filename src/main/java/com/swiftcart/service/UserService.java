package com.swiftcart.service;

import java.util.List;

import com.swiftcart.dto.request.UpdateUserRequestDTO;
import com.swiftcart.dto.request.UserRequestDTO;
import com.swiftcart.dto.response.UserResponseDTO;

public interface UserService {

	public UserResponseDTO createUser(UserRequestDTO userRequestDto);
	
	public UserResponseDTO getUserById(Long id);
	
	public UserResponseDTO getUserByEmail(String email);
	
	public List<UserResponseDTO> getAllUsers();
	
	public List<UserResponseDTO> getActiveUsers();
	
	public UserResponseDTO updateUser(Long id, UpdateUserRequestDTO userRequest);
	
	public void activateUser(Long id);
	
	public void deActivateUser(Long id);
	
	public List<UserResponseDTO> searchUser(String keyword);
	
	public boolean existsByEmail(String email);
}
