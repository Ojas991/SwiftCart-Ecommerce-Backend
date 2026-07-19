package com.swiftcart.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
	    name = "User Response",
	    description = "Response DTO containing user details."
)
public class UserResponseDTO {
	@Schema(
	        description = "Unique identifier of the user",
	        example = "1"
	)
	private Long id;
	
	@Schema(
	        description = "Full name of the user",
	        example = "Ojas Bisen"
	)
	private String fullName;
	
	@Schema(
		    description = "Email address of the user",
		    example = "ojas@gmail.com"
	)
	private String email;
	 
	@Schema(
		        description = "10-digit mobile number",
		        example = "9876543210"
	)
	private String phone;

	  @Schema(
	        description = "Residential address of the user",
	        example = "Bengaluru, Karnataka, India"
	    )
	private String address;
	  
	@Schema(
		    description = "Indicates whether the user account is active",
		    example = "true"
	)
	private boolean isActive;
	

}
