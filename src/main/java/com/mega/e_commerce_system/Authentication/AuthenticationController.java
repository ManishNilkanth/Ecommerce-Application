package com.mega.e_commerce_system.Authentication;

import com.mega.e_commerce_system.Modules.customer.Payload.CustomerRequest;
import com.mega.e_commerce_system.Modules.customer.Repository.CustomerRepository;
import com.mega.e_commerce_system.Modules.customer.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerCustomer(@RequestBody @Valid CustomerRequest request)
    {
        AuthenticationResponse response =  authenticationService.registerCustomer(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateCustomer(@RequestBody @Valid AuthenticationRequest request)
    {
        AuthenticationResponse response =  authenticationService.authenticateCustomer(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
