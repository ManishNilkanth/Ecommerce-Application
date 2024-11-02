package com.mega.e_commerce_system.Modules.product.Payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

    @NotNull(message = "product name is required")
    @NotBlank(message = "product name is black:: product name is required")
    private String name;

    @NotNull(message = "product description is required")
    @NotBlank(message = "product description is black:: product description is required")
    private String description;

    @NotNull(message = "product available Quantity is required")
    @Positive(message = "available quantity of product must be positive")
    private Double availableQuantity;

    @NotNull(message = "product price is required")
    @Positive(message = "price of product must be positive")
    private BigDecimal price;

    @NotNull(message = "product category id is required")
    private Integer categoryId;
}
