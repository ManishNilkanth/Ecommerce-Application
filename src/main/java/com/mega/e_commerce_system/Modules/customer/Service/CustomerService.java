package com.mega.e_commerce_system.Modules.customer.Service;

import com.mega.e_commerce_system.Modules.customer.Payload.CustomerRequest;
import com.mega.e_commerce_system.Modules.customer.Payload.CustomerResponse;

import java.util.List;

public interface CustomerService {

    public Long  updateCustomer(Long id, CustomerRequest customerRequest);

    List<CustomerResponse> getAllCustomers();

    Boolean isCustomerExists(Long customerId);

    CustomerResponse getCustomerById(Long customerId);

    void deleteCustomerById(Long customerId);
}
