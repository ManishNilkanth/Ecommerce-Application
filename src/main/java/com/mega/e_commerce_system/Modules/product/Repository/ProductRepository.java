package com.mega.e_commerce_system.Modules.product.Repository;

import com.mega.e_commerce_system.Modules.product.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByIdInOrderById(List<Integer> productIds);

    List<Product> findAllByCategoryId(Integer categoryId);
}
