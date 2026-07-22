package com.swiftcart.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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
    name = "Update User Request",
    description = "Request DTO used to update an existing user's details."
)
public class UpdateUserRequestDto {

    @Size(min = 2, max = 100, message = "Full Name must be between 2 and 100 characters")
    @Schema(
        description = "Updated full name of the user",
        example = "Ojas Bisen"
    )
    private String fullName;

    @Email(message = "Please provide a valid email address")
    @Size(max = 150, message = "Email must not exceed 150 characters")
    @Schema(
        description = "Updated email address",
        example = "ojas@gmail.com"
    )
    private String email;

    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    @Schema(
        description = "Updated password",
        example = "Password@123",
        format = "password",
        accessMode = Schema.AccessMode.WRITE_ONLY
    )
    private String password;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must contain exactly 10 digits")
    @Schema(
        description = "Updated 10-digit mobile number",
        example = "9876543210"
    )
    private String phone;

    @Size(max = 255, message = "Address must not exceed 255 characters")
    @Schema(
        description = "Updated residential address",
        example = "Bengaluru, Karnataka, India"
    )
    private String address;
}