package com.mega.e_commerce_system.Modules.product.Contoller;

import com.mega.e_commerce_system.Modules.product.Payload.CategoryRequest;
import com.mega.e_commerce_system.Modules.product.Payload.CategoryResponse;
import com.mega.e_commerce_system.Modules.product.Payload.ProductResponse;
import com.mega.e_commerce_system.Modules.product.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService ;

    @PostMapping
    public ResponseEntity<Integer> createNewCategory(@Valid  @RequestBody CategoryRequest request)
    {
        Integer categoryId = categoryService.createCategory(request);

        return new ResponseEntity<>(categoryId , HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(@Valid @RequestBody CategoryRequest request , @PathVariable Integer categoryId)
    {
         CategoryResponse response = categoryService.updateCategory(request,categoryId);

        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Integer categoryId)
    {
         CategoryResponse response = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(response ,HttpStatus.OK);
    }

    @GetMapping("/products/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategoryId(@PathVariable Integer categoryId)
    {
        List<ProductResponse> response = categoryService.getProductsByCategoryId(categoryId);
        return new ResponseEntity<>(response ,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategoryById()
    {
        return new ResponseEntity<>(this.categoryService.getAllCategories(), HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Integer categoryId)
    {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.accepted().build();
    }


}
