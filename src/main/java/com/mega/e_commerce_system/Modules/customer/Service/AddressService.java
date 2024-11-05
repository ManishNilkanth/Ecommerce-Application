package com.mega.e_commerce_system.Modules.customer.Service;

import com.mega.e_commerce_system.Modules.customer.Payload.AddressResponse;
import com.mega.e_commerce_system.Modules.customer.Payload.AddressRequest;

import java.util.List;

public interface AddressService {
    AddressResponse saveAddress(AddressRequest addressDTO, Long customerId);

    List<AddressResponse> getAddressesByCustomer(Long customerId);

    AddressResponse getAddressById(Long addressId);

    AddressResponse updateAddress(Long addressId, AddressRequest addressDTO);

    void deleteAddress(Long addressId);
}
