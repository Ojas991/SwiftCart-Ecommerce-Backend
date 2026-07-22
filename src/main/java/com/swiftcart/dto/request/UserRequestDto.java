package com.swiftcart.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(
	    name = "User Request",
	    description = "Request DTO used to register a new user in SwiftCart."
)
public class UserRequestDto {
	
	@NotBlank(message= "Full Name is Required")
	@Size(min=2, max=100, message="Full Name Must be between 2 to 100 Characters")
    @Schema(
            description = "Full name of the user",
            example = "Ojesh Bisen",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
	private String fullName;
	
	@NotBlank(message= "Email is Required")
	@Email(message= "Please Provide a Valid Email Address")
	@Size(max=150, message="Email Must Not Exceed 150 Characters")
    @Schema(
            description = "Email address of the user",
            example = "ojas@gmail.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
	private String email;
	
	
	@NotBlank(message= "Password is Required")
	@Size(min=6, max=100, message="Password Must be between 6 to 100 Characters")
	@Schema(
		        description = "User password (6-100 characters)",
		        example = "Password@123",
		        requiredMode = Schema.RequiredMode.REQUIRED
    )
	private String password;
	
	@Pattern(regexp= "^[0-9]{10}$", message="Phone Number Must be exact 10 Digits only")
	@Schema(
	        description = "10-digit mobile number",
	        example = "9876543210"
	)
	private String phone;
	
	@Size(max=255, message="Address Must Not Exceed 255 Characters")
	@Schema(
	        description = "Residential address of the user",
	        example = "Bengaluru, Karnataka, India"
	)
	private String address;
	
	
}
