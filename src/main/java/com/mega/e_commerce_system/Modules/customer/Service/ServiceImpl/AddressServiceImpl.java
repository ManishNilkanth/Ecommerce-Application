package com.mega.e_commerce_system.Modules.customer.Service.ServiceImpl;

import com.mega.e_commerce_system.Exceptions.AddressNotFoundException;
import com.mega.e_commerce_system.Exceptions.CustomerNotFoundException;
import com.mega.e_commerce_system.Modules.customer.Entities.Customer;
import com.mega.e_commerce_system.Modules.customer.Payload.AddressResponse;
import com.mega.e_commerce_system.Modules.customer.Repository.CustomerRepository;
import com.mega.e_commerce_system.Modules.customer.Entities.Address;
import com.mega.e_commerce_system.Modules.customer.Payload.AddressRequest;
import com.mega.e_commerce_system.Modules.customer.Repository.AddressRepository;
import com.mega.e_commerce_system.Modules.customer.Service.AddressService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public AddressResponse saveAddress(AddressRequest request, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("Customer","customerId",customerId));

        Address address = modelMapper.map(request, Address.class);
        address.setCustomer(customer);

        List<Address> addresses = customer.getAddress();
        if(addresses == null)
        {
            addresses = new ArrayList<>();
        }
        addresses.add(address);
        customer.setAddress(addresses);
        Address savedAddress = addressRepository.save(address);
        customerRepository.save(customer);
        return modelMapper.map(savedAddress, AddressResponse.class);
    }
    @Override
    public List<AddressResponse> getAddressesByCustomer(Long customerId) {
        List<Address> addresses = addressRepository.findByCustomerId(customerId);
        return addresses.stream()
                .map(address -> modelMapper.map(address, AddressResponse.class))
                .collect(Collectors.toList());
    }
    @Override
    public AddressResponse getAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("Address","addressId",addressId));

        return modelMapper.map(address, AddressResponse.class);
    }

    @Override
    public AddressResponse updateAddress(Long addressId, AddressRequest addressDTO) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("Address","addressId",addressId));
        modifyAddress(address,addressDTO);
        Address updatedAddress = addressRepository.save(address);
        return modelMapper.map(updatedAddress, AddressResponse.class);
    }

    public void modifyAddress(Address address, AddressRequest request) {
        if (StringUtils.isNotBlank(request.getAddressLine1())) {
            address.setAddressLine1(request.getAddressLine1());
        }
        if (StringUtils.isNotBlank(request.getAddressLine2())) {
            address.setAddressLine2(request.getAddressLine2());
        }
        if (StringUtils.isNotBlank(request.getCity())) {
            address.setCity(request.getCity());
        }
        if (StringUtils.isNotBlank(request.getState())) {
            address.setState(request.getState());
        }
        if (StringUtils.isNotBlank(request.getPostalCode())) {
            address.setPostalCode(request.getPostalCode());
        }
        if (StringUtils.isNotBlank(request.getCountry())) {
            address.setCountry(request.getCountry());
        }
        if (StringUtils.isNotBlank(request.getPhoneNumber())) {
            address.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getIsDefault() != null) {
            address.setIsDefault(request.getIsDefault());
        }
    }

    @Override
    public void deleteAddress(Long addressId) {
        if (!addressRepository.existsById(addressId)) {
            throw new AddressNotFoundException("Address","addressId",addressId);
        }
        addressRepository.deleteById(addressId);
    }
}

