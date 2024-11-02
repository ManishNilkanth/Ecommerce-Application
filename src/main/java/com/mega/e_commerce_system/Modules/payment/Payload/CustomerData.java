package com.mega.e_commerce_system.Modules.payment.Payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerData {

    @NotNull(message = "customer id is null:: customer id must be present")
    private Long id;

    @NotNull(message = "First name is null :: customer's firstname must be present")
    private String firstName;

    @NotNull(message = "Last name is null :: customer's firstname must be present")
    private String lastName;

    @NotNull(message = "Email is null :: customer's firstname must be present")
    @Email(message = "Invalid email id : customer's email id must be valid")
    private String email;
}
