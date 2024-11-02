package com.mega.e_commerce_system.Modules.product.Payload;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductPurchaseResponse
{
    private Integer productId;

    private String name;

    private String description;

    private Double quantity;

    private BigDecimal price;
}
