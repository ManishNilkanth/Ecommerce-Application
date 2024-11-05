package com.mega.e_commerce_system.Modules.product.Service.ServiceImpl;

import com.mega.e_commerce_system.Modules.product.Repository.CategoryRepository;
import com.mega.e_commerce_system.Modules.product.Entities.Category;
import com.mega.e_commerce_system.Exceptions.CategoryNotFoundException;
import com.mega.e_commerce_system.Modules.product.Payload.CategoryRequest;
import com.mega.e_commerce_system.Modules.product.Payload.CategoryResponse;
import com.mega.e_commerce_system.Modules.product.Payload.ProductResponse;
import com.mega.e_commerce_system.Modules.product.Service.CategoryService;
import com.mega.e_commerce_system.Modules.product.Service.ProductService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final ProductService productService;
    private final ModelMapper modelMapper;



    @Override
    public Integer createCategory(CategoryRequest request) {
        var category = modelMapper.map(request,Category.class);
        return categoryRepository.save(category).getId();
    }

    @Override
    public CategoryResponse updateCategory(CategoryRequest request, Integer categoryId) {
        var category = categoryRepository
                .findById(categoryId).orElseThrow(()-> new CategoryNotFoundException("Category","CategoryId",categoryId));
        modifyCategory(category,request);
        var savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryResponse.class);
    }

    private void modifyCategory(Category category, CategoryRequest request) {

        if(StringUtils.isNotBlank(request.getName()))
        {
            category.setName(request.getName());
        }
        if(StringUtils.isNotBlank(request.getDescription()))
        {
            category.setDescription(request.getDescription());
        }


    }

    @Override
    public CategoryResponse getCategoryById(Integer categoryId) {
        var category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new CategoryNotFoundException("Category","categoryId",categoryId));

        return modelMapper.map(category,CategoryResponse.class);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> modelMapper.map(category,CategoryResponse.class))
                .toList();
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(()-> new CategoryNotFoundException("Category","categoryId",categoryId));
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public List<ProductResponse> getProductsByCategoryId(Integer categoryId) {

        if(!categoryRepository.existsById(categoryId))
        {
            throw new CategoryNotFoundException("Category","categoryId",categoryId);
        }
        return  productService.getAllByCategoryId(categoryId);
    }
}
