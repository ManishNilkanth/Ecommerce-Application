package com.mega.e_commerce_system.Modules.customer.Controller;

import com.mega.e_commerce_system.Modules.customer.Payload.CustomerRequest;
import com.mega.e_commerce_system.Modules.customer.Payload.CustomerResponse;
import com.mega.e_commerce_system.Modules.customer.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerRequest request)
    {
        Long customerId = customerService.updateCustomer(id,request);
        return new ResponseEntity<>(customerId, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers()
    {
        List<CustomerResponse> customerResponses = customerService.getAllCustomers();
        return new ResponseEntity<>(customerResponses,HttpStatus.OK);
    }

    @GetMapping("/exists/{customerId}")
    public ResponseEntity<Boolean> isCustomerExists(@PathVariable Long customerId)
    {
        Boolean isExist = customerService.isCustomerExists(customerId);
        return new ResponseEntity<>(isExist,HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long customerId)
    {
        CustomerResponse  response = customerService.getCustomerById(customerId);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable Long customerId)
    {
         customerService.deleteCustomerById(customerId);
         return ResponseEntity.accepted().build();
    }
}
