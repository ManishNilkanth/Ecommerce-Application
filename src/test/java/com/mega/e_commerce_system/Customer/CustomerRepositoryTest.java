package com.mega.e_commerce_system.Customer;

import com.mega.e_commerce_system.Exceptions.CustomerNotFoundException;
import com.mega.e_commerce_system.Modules.customer.Entities.Customer;
import com.mega.e_commerce_system.Modules.customer.Entities.Role;
import com.mega.e_commerce_system.Modules.customer.Repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.time.LocalDate;
import java.util.Optional;


@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    void tearDown()
    {
        customerRepository.deleteAll();
    }

    @Test
    void itShouldCheckIfCustomerFindByEmail() {
        //given
        String email = "nilkanthmanish0@gmail.com";
        Customer actual = Customer.builder()
                .firstName("Manish")
                .lastName("Nilkanth")
                .role(Role.CUSTOMER)
                .gender("male")
                .email(email)
                .password("manish@321")
                .dateOfBirth(LocalDate.parse("2002-07-07"))
                .phoneNumber("9770879341")
                .isActive(true)
                .build();
        customerRepository.save(actual);
        //when
        Customer expected = customerRepository.findByEmail(email).orElseThrow(()-> new CustomerNotFoundException("Customer","Customer email",Long.parseLong(email)));

        //then
        assertThat(expected).isNotNull();
         assertThat(actual.getFirstName()).isEqualTo(expected.getFirstName());
         assertThat(actual.getLastName()).isEqualTo(expected.getLastName());
         assertThat(actual.getRole()).isEqualTo(expected.getRole());
         assertThat(actual.getGender()).isEqualTo(expected.getGender());
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        assertThat(actual.getPassword()).isEqualTo(expected.getPassword());
        assertThat(actual.getDateOfBirth()).isEqualTo(expected.getDateOfBirth());
        assertThat(actual.getPhoneNumber()).isEqualTo(expected.getPhoneNumber());
        assertThat(actual.getIsActive()).isEqualTo(expected.getIsActive());
    }
    @Test
    void itShouldCheckCustomerIsNotFoundByEmail() {
        //given
        String email = "nilkanthmanish0@gmail.com";
        //when
        Optional<Customer> expected = customerRepository.findByEmail(email);
        //then
        assertThat(expected).isNotPresent();
    }
}