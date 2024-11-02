package com.mega.e_commerce_system.Authentication;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

  @NotNull(message = "email is null:: email must be present")
  private String email;
  @NotNull(message = "password is null:: password must be present")
  String password;
}
