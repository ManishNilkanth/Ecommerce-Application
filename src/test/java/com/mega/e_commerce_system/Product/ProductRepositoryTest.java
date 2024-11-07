package com.mega.e_commerce_system.Product;

import com.mega.e_commerce_system.Modules.product.Entities.Category;
import com.mega.e_commerce_system.Modules.product.Entities.Product;
import com.mega.e_commerce_system.Modules.product.Repository.CategoryRepository;
import com.mega.e_commerce_system.Modules.product.Repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    private Category category;

    private Product product;

    @BeforeEach
    void setUp() {
        category = Category.builder()
                .id(1)
                .name("Smart Phones")
                .description("all smart phones")
                .build();
        product = Product.builder()
                .id(1)
                .name("Realme C3")
                .description("Smart phone with android operating system")
                .price(BigDecimal.valueOf(1000))
                .availableQuantity(23.0)
                .category(category)
                .build();

        category.setProducts(List.of(product));
        categoryRepository.save(category);
        productRepository.save(product);
    }



    @Test
    void shouldFindAllByIdInOrderByIdTest() {
        //given
        List<Integer> ids = new ArrayList<>();
        ids.add(product.getId());
        //when
        List<Product> products = productRepository.findAllByIdInOrderById(ids);
        //then
        assertThat(products).isNotNull();
    }

    @Test
    void shouldFindAllByCategoryIdTest() {
        //when
        List<Product> products = productRepository.findAllByCategoryId(category.getId());
        //then
        assertThat(products).isNotNull();
        assertThat(products.get(0).getName()).isEqualTo(product.getName());

    }
}