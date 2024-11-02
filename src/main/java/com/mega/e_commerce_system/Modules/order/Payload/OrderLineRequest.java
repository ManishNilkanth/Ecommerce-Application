package com.mega.e_commerce_system.Modules.order.Payload;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineRequest {

    private Integer id;

    private Integer orderId;

    private Integer productId;

    private double quantity;
}
