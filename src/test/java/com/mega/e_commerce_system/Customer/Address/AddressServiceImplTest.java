package com.mega.e_commerce_system.Customer.Address;

import com.mega.e_commerce_system.Exceptions.AddressNotFoundException;
import com.mega.e_commerce_system.Modules.customer.Entities.Address;
import com.mega.e_commerce_system.Modules.customer.Entities.Customer;
import com.mega.e_commerce_system.Modules.customer.Entities.Role;
import com.mega.e_commerce_system.Modules.customer.Payload.AddressRequest;
import com.mega.e_commerce_system.Modules.customer.Repository.AddressRepository;
import com.mega.e_commerce_system.Modules.customer.Repository.CustomerRepository;
import com.mega.e_commerce_system.Modules.customer.Service.ServiceImpl.AddressServiceImpl;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AddressRepository addressRepository;
    @Spy
    private ModelMapper modelMapper;
    @InjectMocks
    private AddressServiceImpl underTest;
    private Address address;
    private AddressRequest request;
    private Customer customer;
    private Long invalidId = 534534L;
    @BeforeEach
    void setUp() {
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
                .build();
        address = Address.builder()
                .id(1L)
                .addressLine1("Gudi, near Shiv Temple")
                .addressLine2("Khandwa, Madhya Pradesh")
                .city("Khandwa")
                .country("India")
                .state("Madhya Pradesh")
                .isDefault(true)
                .postalCode("450881")
                .phoneNumber("9887849354")
                .build();
        request = AddressRequest.builder()
                .id(1L)
                .addressLine1("Gudi, near Shiv Temple")
                .addressLine2("Khandwa, Madhya Pradesh")
                .city("Khandwa")
                .country("India")
                .state("Madhya Pradesh")
                .isDefault(true)
                .postalCode("450881")
                .phoneNumber("9887849354")
                .build();

    }

    @Test
    void shouldSaveAddressTest() {
        //given
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.ofNullable(customer));
        address = modelMapper.map(request,Address.class);
        address.setCustomer(customer);
        customer.setAddress(new ArrayList<>(List.of(address)));
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        //when
        underTest.saveAddress(request,customer.getId());
        //then
        verify(customerRepository).findById(customer.getId());
        verify(addressRepository).save(any(Address.class));
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void shouldGetAddressesByCustomerIdTest() {
        //when
        underTest.getAddressesByCustomer(customer.getId());
        //then
        verify(addressRepository).findByCustomerId(customer.getId());
    }
    @Test
    void shouldGetAddressByIdTest() {
        //when
        when(addressRepository.findById(request.getId())).thenReturn(Optional.ofNullable(address));
        underTest.getAddressById(request.getId());
        //then
        verify(addressRepository).findById(request.getId());
        verifyNoMoreInteractions(addressRepository);
    }
    @Test
    void shouldGetAddressById_ThrowsExceptionAddressNotFoundTest() {
        //given
        when(addressRepository.findById(invalidId)).thenReturn(Optional.empty());
        //when
        assertThrows(AddressNotFoundException.class,()-> underTest.getAddressById(invalidId));
        //then
        verify(addressRepository).findById(invalidId);
        verifyNoMoreInteractions(addressRepository);
    }

    @Test
    void shouldUpdateAddressTest() {
        //given
        when(addressRepository.findById(request.getId())).thenReturn(Optional.ofNullable(address));
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        //when
        underTest.updateAddress(request.getId(),request);
        //then
        verify(addressRepository).findById(request.getId());
        verify(addressRepository).save(any(Address.class));
    }

    @Test
    void shouldUpdateAddressTest_ThrowsExceptionAddressNotFoundTest() {
        //given
        when(addressRepository.findById(invalidId)).thenReturn(Optional.empty());
        //when
        assertThrows(AddressNotFoundException.class,()-> underTest.updateAddress(invalidId,request));
        //then
        verify(addressRepository).findById(invalidId);
        verify(addressRepository,never()).save(any(Address.class));
    }

    @Test
    void deleteAddress() {
        //given
        when(addressRepository.existsById(request.getId())).thenReturn(true);
        //when
        underTest.deleteAddress(request.getId());
        //then
        verify(addressRepository).existsById(request.getId());
        verify(addressRepository).deleteById(request.getId());
    }
    @Test
    void deleteAddress_throwsExceptionIfAddressIsNotFoundTest() {
        // given
        when(addressRepository.existsById(invalidId)).thenReturn(false);
        // then
        assertThrows(AddressNotFoundException.class, () -> underTest.deleteAddress(invalidId));
        verify(addressRepository).existsById(invalidId);
        verifyNoMoreInteractions(addressRepository);
    }
}