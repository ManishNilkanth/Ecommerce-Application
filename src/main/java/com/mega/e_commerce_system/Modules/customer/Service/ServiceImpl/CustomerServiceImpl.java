package com.mega.e_commerce_system.Modules.customer.Service.ServiceImpl;

import com.mega.e_commerce_system.Exceptions.CustomerNotFoundException;
import com.mega.e_commerce_system.Modules.customer.Entities.Customer;
import com.mega.e_commerce_system.Modules.customer.Payload.CustomerRequest;
import com.mega.e_commerce_system.Modules.customer.Payload.CustomerResponse;
import com.mega.e_commerce_system.Modules.customer.Repository.CustomerRepository;
import com.mega.e_commerce_system.Modules.customer.Service.CustomerService;
import com.mega.e_commerce_system.Modules.customer.Entities.Address;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final ModelMapper modelMapper;

    @Override
    public Long updateCustomer(Long id, CustomerRequest customerRequest) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(()-> new CustomerNotFoundException("customer","customerId",id));

        modifyCustomer(customer,customerRequest);

        Customer savedCustomer = customerRepository.save(customer);

        return savedCustomer.getId();
    }

    private void modifyCustomer(Customer customer, CustomerRequest request) {
        if (request.getFirstName() != null) {
            customer.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            customer.setLastName(request.getLastName());
        }
        if (request.getEmail() != null) {
            customer.setEmail(request.getEmail());
        }
        if (request.getPhoneNumber() != null) {
            customer.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getDateOfBirth() != null) {
            customer.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getGender() != null) {
            customer.setGender(request.getGender());
        }
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customer ->{
                    List<Address> deFaultAddress = customer.getAddress()
                            .stream()
                            .filter(Address::getIsDefault)
                            .toList();
                    customer.setAddress(deFaultAddress);
                   return modelMapper.map(customer,CustomerResponse.class);
                })
                .toList();
    }

    @Override
    public Boolean isCustomerExists(Long customerId) {
        return customerRepository.findById(customerId).isPresent();
    }

    @Override
    public CustomerResponse getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("customer","customerId",customerId));

        List<Address> deFaultAddress = customer.getAddress()
                .stream()
                .filter(address -> address.getIsDefault())
                .toList();

        customer.setAddress(deFaultAddress);
        return modelMapper.map(customer,CustomerResponse.class);
    }

    @Override
    public void deleteCustomerById(Long customerId)
    {
        if (!customerRepository.existsById(customerId)) {
            throw new CustomerNotFoundException("Customer","customerId",customerId);
        }
        customerRepository.deleteById(customerId);
    }


}
