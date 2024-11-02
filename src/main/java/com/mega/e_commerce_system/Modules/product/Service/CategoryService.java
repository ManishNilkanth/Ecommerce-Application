package com.mega.e_commerce_system.Modules.product.Service;

import com.mega.e_commerce_system.Modules.product.Payload.CategoryResponse;
import com.mega.e_commerce_system.Modules.product.Payload.ProductResponse;
import com.mega.e_commerce_system.Modules.product.Payload.CategoryRequest;

import java.util.List;

public interface CategoryService {
    Integer createCategory(CategoryRequest request);

    CategoryResponse updateCategory(CategoryRequest request, Integer categoryId);

    CategoryResponse getCategoryById(Integer categoryId);

    List<CategoryResponse> getAllCategories();

    void deleteCategory(Integer categoryId);

    List<ProductResponse> getProductsByCategoryId(Integer categoryId);
}
