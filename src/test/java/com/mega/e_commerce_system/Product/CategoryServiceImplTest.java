package com.mega.e_commerce_system.Product;

import com.mega.e_commerce_system.Exceptions.CategoryNotFoundException;
import com.mega.e_commerce_system.Modules.product.Entities.Category;
import com.mega.e_commerce_system.Modules.product.Payload.CategoryRequest;
import com.mega.e_commerce_system.Modules.product.Payload.CategoryResponse;
import com.mega.e_commerce_system.Modules.product.Repository.CategoryRepository;
import com.mega.e_commerce_system.Modules.product.Service.ServiceImpl.CategoryServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private CategoryServiceImpl underTest;

    private CategoryRequest request;
    private Category category;
    private final Integer invalidId = 3423;

    @BeforeEach
    void setUp() {
        request = CategoryRequest.builder()
                .id(1)
                .name("Smart Phones")
                .description("All smart phones")
                .build();

        category = modelMapper.map(request, Category.class);
    }

    @Test
    void shouldCreateCategory() {
        // given
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        // when
        underTest.createCategory(request);

        // then
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void shouldUpdateCategory() {
        // given
        when(categoryRepository.findById(request.getId())).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        // when
        CategoryResponse response = underTest.updateCategory(request, request.getId());

        // then
        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo(category.getName());
        assertThat(response.getDescription()).isEqualTo(category.getDescription());
        verify(categoryRepository).findById(request.getId());
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentCategory() {
        // given
        when(categoryRepository.findById(invalidId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(CategoryNotFoundException.class, () -> underTest.updateCategory(request, invalidId));
        verify(categoryRepository).findById(invalidId);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void shouldGetCategoryById() {
        // given
        when(categoryRepository.findById(request.getId())).thenReturn(Optional.of(category));

        // when
        CategoryResponse response = underTest.getCategoryById(request.getId());

        // then
        assertThat(response).isNotNull();
        verify(categoryRepository).findById(request.getId());
    }

    @Test
    void shouldThrowExceptionWhenGettingNonExistentCategoryById() {
        // given
        when(categoryRepository.findById(invalidId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(CategoryNotFoundException.class, () -> underTest.getCategoryById(invalidId));
        verify(categoryRepository).findById(invalidId);
    }

    @Test
    void shouldGetAllCategories() {
        // when
        underTest.getAllCategories();

        // then
        verify(categoryRepository).findAll();
    }

    @Test
    void shouldDeleteCategory() {
        // given
        when(categoryRepository.findById(request.getId())).thenReturn(Optional.of(category));

        // when
        underTest.deleteCategory(request.getId());

        // then
        verify(categoryRepository).findById(request.getId());
        verify(categoryRepository).deleteById(request.getId());
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentCategory() {
        // given
        when(categoryRepository.findById(invalidId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(CategoryNotFoundException.class, () -> underTest.deleteCategory(invalidId));
        verify(categoryRepository).findById(invalidId);
        verify(categoryRepository, never()).deleteById(invalidId);
    }
}
