package com.mega.e_commerce_system.Modules.customer.Repository;

import com.mega.e_commerce_system.Modules.customer.Entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByCustomerId(Long customerId);
}

