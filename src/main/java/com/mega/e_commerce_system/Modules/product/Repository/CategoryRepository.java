package com.mega.e_commerce_system.Modules.product.Repository;

import com.mega.e_commerce_system.Modules.product.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
