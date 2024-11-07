package com.mega.e_commerce_system.Product;

import com.mega.e_commerce_system.Exceptions.ProductNotFoundException;
import com.mega.e_commerce_system.Exceptions.ProductPurchaseException;
import com.mega.e_commerce_system.Modules.payment.Payload.ProductPurchaseRequest;
import com.mega.e_commerce_system.Modules.product.Entities.Category;
import com.mega.e_commerce_system.Modules.product.Entities.Product;
import com.mega.e_commerce_system.Modules.product.Payload.ProductPurchaseResponse;
import com.mega.e_commerce_system.Modules.product.Payload.ProductRequest;
import com.mega.e_commerce_system.Modules.product.Repository.CategoryRepository;
import com.mega.e_commerce_system.Modules.product.Repository.ProductRepository;
import com.mega.e_commerce_system.Modules.product.Service.ServiceImpl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Spy
    private ModelMapper modelMapper;
    @InjectMocks
    private ProductServiceImpl underTest;
    private Product product1,product2;
    private ProductRequest request;
    private Category category;
    @BeforeEach
    void setUp() {
        category = Category.builder()
                .id(1)
                .name("Smart Phones")
                .description("all smart phones")
                .build();
        product1 = Product.builder()
                .id(1)
                .name("Product 1")
                .description("Description 1")
                .price(BigDecimal.valueOf(100.0))
                .availableQuantity(10.0)
                .build();

        product2 = Product.builder()
                .id(2)
                .name("Product 2")
                .description("Description 2")
                .price(BigDecimal.valueOf(150.0))
                .availableQuantity(5.0)
                .build();
        request = request.builder()
                .id(1)
                .name("Realme C3")
                .description("Smart phone with android operating system")
                .price(BigDecimal.valueOf(1000))
                .availableQuantity(23.0)
                .categoryId(1)
                .build();
    }

    @Test
    void shouldCreateProductTest() {
        //given
         when(categoryRepository.findById(request.getCategoryId())).thenReturn(Optional.ofNullable(category));
         product1 = modelMapper.map(request,Product.class);
         product1.setCategory(category);
         when(productRepository.save(any(Product.class))).thenReturn(product1);
        //when
        underTest.createProduct(request);
        //then
        verify(categoryRepository).findById(request.getCategoryId());
        verify(productRepository).save(any(Product.class));
    }


    @Test
    void purchaseProduct_SuccessfulPurchaseTest() {
        // given
        ProductPurchaseRequest request1 = new ProductPurchaseRequest(1, 2.0);
        ProductPurchaseRequest request2 = new ProductPurchaseRequest(2, 3.0);
        List<ProductPurchaseRequest> purchaseRequests = List.of(request1, request2);

        when(productRepository.findAllByIdInOrderById(List.of(1, 2))).thenReturn(List.of(product1, product2));

        // when
        List<ProductPurchaseResponse> responses = underTest.purchaseProduct(purchaseRequests);

        // Assert
        assertThat(2).isEqualTo(responses.size());
        assertThat(8.0).isEqualTo(product1.getAvailableQuantity());  // 10 - 2
        assertThat(2.0).isEqualTo(product2.getAvailableQuantity());  // 5 - 3
        verify(productRepository).save(product1);
        verify(productRepository).save(product2);
    }

    @Test
    void purchaseProduct_ProductNotFoundTest() {
        // Arrange
        ProductPurchaseRequest request1 = new ProductPurchaseRequest(1, 2.0);
        ProductPurchaseRequest request2 = new ProductPurchaseRequest(3, 1.0); // Product with ID 3 doesn't exist
        List<ProductPurchaseRequest> purchaseRequests = List.of(request1, request2);

        when(productRepository.findAllByIdInOrderById(List.of(1, 3))).thenReturn(List.of(product1)); // Only product1 found

        // Assert
        ProductPurchaseException exception = assertThrows(ProductPurchaseException.class,
                () -> underTest.purchaseProduct(purchaseRequests));
        assertThat("one or more products are not in stock").isEqualTo(exception.getMessage());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void purchaseProduct_InsufficientQuantityTest() {
        // Arrange
        ProductPurchaseRequest request1 = new ProductPurchaseRequest(1, 2.0);
        ProductPurchaseRequest request2 = new ProductPurchaseRequest(2, 10.0); // Requesting more than available quantity
        List<ProductPurchaseRequest> purchaseRequests = List.of(request1, request2);

        when(productRepository.findAllByIdInOrderById(List.of(1, 2))).thenReturn(List.of(product1, product2));

        //Assert
        ProductPurchaseException exception = assertThrows(ProductPurchaseException.class,
                () -> underTest.purchaseProduct(purchaseRequests));
        assertThat("Insufficient stock quantity for product with Id::2").isEqualTo(exception.getMessage());
        verify(productRepository, never()).save(product2);        // product2 should not be saved due to insufficient stock
    }

    @Test
    void shouldGetProductByIdTest() {
        //given
        product1.setCategory(category);
        when(productRepository.findById(product1.getId())).thenReturn(Optional.ofNullable(product1));
        //when
        underTest.getProductById(product1.getId());
        //then
        verify(productRepository).findById(product1.getId());
    }
    @Test
    void getProductById_ThrowsExceptionProductNotFoundTest() {
        //given
        Integer invalidId = 3333;
        when(productRepository.findById(invalidId)).thenReturn(Optional.empty());
        //when
        assertThrows(ProductNotFoundException.class,()->underTest.getProductById(invalidId));
        //then
        verify(productRepository).findById(invalidId);
    }

    @Test
    void shouldGetAllProductsTest() {
        //when
        underTest.getAllProducts();
        //then
        verify(productRepository).findAll();
    }

    @Test
    void shouldDeleteProductByIdTest() {
        //given
        when(productRepository.existsById(product1.getId())).thenReturn(true);
        //when
        underTest.deleteProductById(product1.getId());
        //then
        verify(productRepository).existsById(product1.getId());
        verify(productRepository).deleteById(product1.getId());
    }
    @Test
    void deleteProductById_throwsExceptionProductNotFoundTest() {
        //given
        Integer invalidId = 4444;
        when(productRepository.existsById(invalidId)).thenReturn(false);
        //then
        assertThrows(ProductNotFoundException.class,()-> underTest.deleteProductById(invalidId));
        verify(productRepository).existsById(invalidId);
        verify(productRepository,never()).deleteById(invalidId);
    }

    @Test
    void shouldUpdateProductByIdTest() {
        //given
        when(productRepository.findById(product1.getId())).thenReturn(Optional.ofNullable(product1));
        when(productRepository.save(any(Product.class))).thenReturn(product1);
        //when
        underTest.updateProductById(product1.getId(),request);
        //then
        verify(productRepository).findById(product1.getId());
        verify(productRepository).save(any(Product.class));
    }
    @Test
    void updateProductById_ThrowsExceptionProductNotFoundTest() {
        //given
        Integer invalidId = 5555;
        when(productRepository.findById(invalidId)).thenReturn(Optional.empty());
        //when
        assertThrows(ProductNotFoundException.class,()-> underTest.updateProductById(invalidId,request));
        //then
        verify(productRepository).findById(invalidId);
        verify(productRepository,never()).save(any(Product.class));
    }

    @Test
    void shouldGetAllByCategoryIdTest() {
        //when
        underTest.getAllByCategoryId(category.getId());
        //then
        verify(productRepository).findAllByCategoryId(category.getId());
    }
}