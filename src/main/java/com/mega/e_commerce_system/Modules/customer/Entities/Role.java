package com.mega.e_commerce_system.Modules.customer.Entities;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static com.mega.e_commerce_system.Modules.customer.Entities.Permission.*;
@RequiredArgsConstructor
public enum Role {
    ADMIN(Set.of(
            CUSTOMER_VIEW, CUSTOMER_CREATE, CUSTOMER_UPDATE, CUSTOMER_DELETE,
            PRODUCT_VIEW, PRODUCT_CREATE, PRODUCT_UPDATE, PRODUCT_DELETE,
            ORDER_VIEW, ORDER_CREATE, ORDER_UPDATE, ORDER_DELETE,ORDER_LINE_VIEW,
            PAYMENT_VIEW, PAYMENT_CREATE,
            ADDRESS_VIEW, ADDRESS_CREATE, ADDRESS_UPDATE, ADDRESS_DELETE
    )),

    CUSTOMER(Set.of(
            CUSTOMER_VIEW, CUSTOMER_UPDATE,
            PRODUCT_VIEW,                                             // Customers can view products but cannot manage them
            ORDER_VIEW, ORDER_CREATE, ORDER_UPDATE,                   // Customer can manage own orders
            PAYMENT_VIEW, PAYMENT_CREATE,                             // Customer can manage own payments
            ADDRESS_VIEW, ADDRESS_CREATE, ADDRESS_UPDATE              // Customer can manage own addresses
    ));

    @Getter
    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
