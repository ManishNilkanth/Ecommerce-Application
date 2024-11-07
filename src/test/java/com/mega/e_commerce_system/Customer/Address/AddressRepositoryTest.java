package com.mega.e_commerce_system.Customer.Address;

import com.mega.e_commerce_system.Modules.customer.Entities.Address;
import com.mega.e_commerce_system.Modules.customer.Entities.Customer;
import com.mega.e_commerce_system.Modules.customer.Entities.Role;
import com.mega.e_commerce_system.Modules.customer.Repository.AddressRepository;
import com.mega.e_commerce_system.Modules.customer.Repository.CustomerRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.*;
@DataJpaTest
class AddressRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    private Customer customer;
    private Address address;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
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
                .addressLine1("Gudi, near Shiv Temple")
                .addressLine2("Khandwa, Madhya Pradesh")
                .city("Khandwa")
                .country("India")
                .state("Madhya Pradesh")
                .isDefault(true)
                .postalCode("450881")
                .phoneNumber("9887849354")
                .customer(customer)
                .build();

        customer.setAddress(new ArrayList<>(List.of(address)));
        customerRepository.save(customer);
        addressRepository.save(address);
    }
    @AfterEach
    void cleanup()
    {
        customerRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Test
    void shouldFindByCustomerIdTest() {
        // Retrieve addresses from database
        List<Address> addressFromDB = addressRepository.findByCustomerId(customer.getId());
        // Verify the retrieved addresses match the expected data
        assertThat(addressFromDB).isNotNull();
        assertThat(1).isEqualTo(addressFromDB.size());
        assertThat(addressFromDB.contains(address)).isTrue();
        assertThat(addressFromDB.get(0).getAddressLine1()).isEqualTo("Gudi, near Shiv Temple");
    }
}
