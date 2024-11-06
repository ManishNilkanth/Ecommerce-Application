package com.mega.e_commerce_system.Modules.customer.Payload;

import com.mega.e_commerce_system.Modules.customer.Entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Getter
@Service
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {

    private Long id;

    @NotNull(message = "First name is null :: customer's firstname must be present")
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name is null :: customer's lastname must be present")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Email is null :: customer's email must be present")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email id : customer's email id must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be a 10-digit number")
    private String phoneNumber;

    private LocalDate dateOfBirth;

    private String gender;

    @NotNull(message = "role is null:: customer's role must be present")
    private Role role;

}
