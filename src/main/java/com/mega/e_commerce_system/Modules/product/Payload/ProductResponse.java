package com.mega.e_commerce_system.Modules.product.Payload;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private Integer id;

    private String name;

    private String description;

    private Double availableQuantity;

    private BigDecimal price;

    private LocalDateTime createdDate;

    private LocalDateTime lastUpdatedDate;

    private CategoryResponse categoryResponse;


}
