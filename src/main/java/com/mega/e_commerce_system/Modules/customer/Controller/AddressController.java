package com.mega.e_commerce_system.Modules.customer.Controller;

import com.mega.e_commerce_system.Modules.customer.Payload.AddressRequest;
import com.mega.e_commerce_system.Modules.customer.Payload.AddressResponse;
import com.mega.e_commerce_system.Modules.customer.Service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<AddressResponse> createAddress(@RequestBody AddressRequest request, @PathVariable Long customerId) {
        AddressResponse savedAddress = addressService.saveAddress(request, customerId);

        return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
    }
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<AddressResponse>> getAddressesByCustomer(@PathVariable Long customerId) {
        List<AddressResponse> addresses = addressService.getAddressesByCustomer(customerId);

        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable Long addressId) {
        AddressResponse address = addressService.getAddressById(addressId);

        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable Long addressId, @RequestBody AddressRequest request) {
        AddressResponse updatedAddress = addressService.updateAddress(addressId, request);

        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.accepted().build();
    }


}
