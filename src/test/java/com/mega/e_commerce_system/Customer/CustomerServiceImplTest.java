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
    private CustomerRequest request;
    private Customer customer;
    private Long invalidId = 34534L;


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
          request = CustomerRequest.builder()
                .id(1L)
                .firstName("Ram")
                .lastName("Sudan")
                .role(Role.CUSTOMER)
                .gender("male")
                .email("ram@gmail.com")
                .password("sudan@321")
                .dateOfBirth(LocalDate.parse("2002-07-07"))
                .phoneNumber("9770879341")
                .build();
    }


    @Test
    void shouldUpdateCustomerTest() {
        // Mock the repository behavior
        when(customerRepository.findById(request.getId())).thenReturn(Optional.of(customer));

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
        Long customerId = underTest.updateCustomer(request.getId(), request);

        // Assert
        assertThat(customerId).isEqualTo(request.getId()); // Ensure the ID returned is correct
        verify(customerRepository, times(1)).findById(request.getId());
        verify(customerRepository, times(1)).save(customer);


    }
    @Test
    void updateCustomer_throwsExceptionIfCustomerNotFoundTest() {
        // Arrange
        CustomerRequest request = new CustomerRequest();
        when(customerRepository.findById(invalidId)).thenReturn(Optional.empty());
        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () -> underTest.updateCustomer(invalidId, request));
        verify(customerRepository).findById(invalidId);
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
        //when
        when(customerRepository.existsById(request.getId())).thenReturn(true);
        underTest.isCustomerExists(request.getId());
        //then
        verify(customerRepository).existsById(request.getId());
    }

    @Test
    void shouldGetCustomerByIdTest() {
        //when
        when(customerRepository.findById(request.getId())).thenReturn(Optional.of(customer));
        underTest.getCustomerById(request.getId());
        //then
        verify(customerRepository).findById(request.getId());
    }
    @Test
    void shouldGetCustomerById_ThrowsExceptionCustomerNotFoundTest() {
        //when
        when(customerRepository.findById(invalidId)).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class,()-> underTest.getCustomerById(invalidId));
        //then
        verify(customerRepository).findById(invalidId);
    }

    @Test
    void deleteCustomerByIdTest() {
        //when
        when(customerRepository.existsById(request.getId())).thenReturn(true);
        underTest.deleteCustomerById(request.getId());
        //then
        verify(customerRepository).existsById(request.getId());
        verify(customerRepository).deleteById(request.getId());
    }

    @Test
    void deleteCustomer_throwsExceptionIfCustomerNotFoundTest() {
        // Arrange
        when(customerRepository.existsById(request.getId())).thenReturn(false);
        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () -> underTest.deleteCustomerById(request.getId()));
        verify(customerRepository).existsById(request.getId());
        verifyNoMoreInteractions(customerRepository);
    }
}