package com.mega.e_commerce_system.Modules.order.Payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLineResponse {
    Integer id;
    double quantity;
}
