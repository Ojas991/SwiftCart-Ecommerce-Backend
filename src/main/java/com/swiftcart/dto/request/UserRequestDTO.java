package com.swiftcart.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
	
	@NotBlank(message= "Full Name is Required")
	@Size(min=2, max=100, message="Full Name Must be between 2 to 100 Characters")
	private String fullName;
	
	@NotBlank(message= "Email is Required")
	@Email(message= "Please Provide a Valid Email Address")
	@Size(max=150, message="Email Must Not Exceed 150 Characters")
	private String email;
	
	@NotBlank(message= "Password is Required")
	@Size(min=6, max=100, message="Password Must be between 6 to 100 Characters")
	private String password;
	
	@Pattern(regexp= "^[0-9]{10}$", message="Phone Number Must be exact 10 Digits only")
	private String phone;
	
	@Size(max=255, message="Address Must Not Exceed 255 Characters")
	private String address;
	
	
}
