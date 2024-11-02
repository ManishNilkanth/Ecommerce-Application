package com.mega.e_commerce_system.Authentication;

import com.mega.e_commerce_system.Exceptions.CustomerNotFoundException;
import com.mega.e_commerce_system.Modules.customer.Entities.Customer;
import com.mega.e_commerce_system.Modules.customer.Entities.Role;
import com.mega.e_commerce_system.Modules.customer.Payload.CustomerRequest;
import com.mega.e_commerce_system.Modules.customer.Repository.CustomerRepository;
import com.mega.e_commerce_system.Secutiry.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final CustomerRepository customerRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    public AuthenticationResponse registerCustomer(CustomerRequest customerRequest)
    {
        Customer customer =  Customer.builder()
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .email(customerRequest.getEmail())
                .password(passwordEncoder.encode(customerRequest.getPassword()))
                .phoneNumber(customerRequest.getPhoneNumber())
                .role(Role.CUSTOMER)
                .isActive(true)
                .dateOfBirth(customerRequest.getDateOfBirth())
                .gender(customerRequest.getGender())
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        var jwtToken = jwtService.generateToken(customer);
        return AuthenticationResponse.builder().accessToken(jwtToken).build();
    }

    public AuthenticationResponse authenticateCustomer(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Customer customer = customerRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new CustomerNotFoundException("Customer","Customer email",Long.parseLong(request.getEmail())));

        var jwt = jwtService.generateToken(customer);
        return AuthenticationResponse.builder().accessToken(jwt).build();
    }
}
