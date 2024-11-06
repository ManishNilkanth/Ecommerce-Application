package com.mega.e_commerce_system.Customer;

import com.mega.e_commerce_system.Exceptions.CustomerNotFoundException;
import com.mega.e_commerce_system.Modules.customer.Entities.Customer;
import com.mega.e_commerce_system.Modules.customer.Entities.Role;
import com.mega.e_commerce_system.Modules.customer.Payload.CustomerRequest;
import com.mega.e_commerce_system.Modules.customer.Repository.CustomerRepository;
import com.mega.e_commerce_system.Modules.customer.Service.ServiceImpl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private CustomerServiceImpl underTest;

    private Customer customer;

    @BeforeEach
    void setUp()
    {
          customer = Customer.builder()
                .id(1L)
                .firstName("Manish")
                .lastName("Nilkanth")
                .role(Role.CUSTOMER)
                .gender("male")
                .email("manish@gmail.com")
                .password("manish@321")
                .dateOfBirth(LocalDate.parse("2002-07-07"))
                .phoneNumber("9770879341")
                .isActive(true)
                  .address(new ArrayList<>())
                .build();
    }


    @Test
    void shouldUpdateCustomerTest() {
        // Arrange
        Long id = 1L;
        CustomerRequest request = CustomerRequest.builder()
                .id(id)
                .firstName("Ram")
                .lastName("Sudan")
                .role(Role.CUSTOMER)
                .gender("male")
                .email("ram@gmail.com")
                .password("sudan@321")
                .dateOfBirth(LocalDate.parse("2002-07-07"))
                .phoneNumber("9770879341")
                .build();

        // Mock the repository behavior
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        // updated customer based on the request data
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setGender(request.getGender());
        customer.setDateOfBirth(request.getDateOfBirth());

        // Simulate saving the updated customer
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // Act
        Long customerId = underTest.updateCustomer(id, request);

        // Assert
        assertThat(customerId).isEqualTo(id); // Ensure the ID returned is correct
        verify(customerRepository, times(1)).findById(id);
        verify(customerRepository, times(1)).save(customer);


    }
    @Test
    void updateCustomer_throwsExceptionIfCustomerNotFoundTest() {
        // Arrange
        Long id = 1L;
        CustomerRequest request = new CustomerRequest();
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () -> underTest.updateCustomer(id, request));
        verify(customerRepository).findById(id);
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void shouldGetAllCustomersTest() {
        //when
        underTest.getAllCustomers();
        //then
        verify(customerRepository).findAll();
    }

    @Test
    void shouldCustomerExistsTest() {
        //get
        Long id = 1L;
        //when
        when(customerRepository.existsById(id)).thenReturn(true);
        underTest.isCustomerExists(id);
        //then
        verify(customerRepository).existsById(id);
    }

    @Test
    void shouldGetCustomerByIdTest() {
        //get
        Long id = 1L;
        //when
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        underTest.getCustomerById(id);
        //then
        verify(customerRepository).findById(id);
    }

    @Test
    void deleteCustomerByIdTest() {
        //get
        Long id = 1L;
        //when
        when(customerRepository.existsById(id)).thenReturn(true);
        underTest.deleteCustomerById(id);
        //then
        verify(customerRepository).existsById(id);
        verify(customerRepository).deleteById(id);
    }

    @Test
    void deleteCustomer_throwsExceptionIfCustomerNotFoundTest() {
        // Arrange
        Long id = 1L;
        when(customerRepository.existsById(id)).thenReturn(false);
        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () -> underTest.deleteCustomerById(id));
        verify(customerRepository).existsById(id);
        verifyNoMoreInteractions(customerRepository);
    }
}