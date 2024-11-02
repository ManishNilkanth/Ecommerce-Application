package com.mega.e_commerce_system.Modules.product.Payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {
    private Integer id;

    private String name;

    private String description;
}
