package com.mega.e_commerce_system.Modules.product.Payload;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    @NotNull(message = "category name is null:: category name must be present")
    private String name;

    @NotNull(message = "category description is null:: category name must be present")
    private String description;
}
