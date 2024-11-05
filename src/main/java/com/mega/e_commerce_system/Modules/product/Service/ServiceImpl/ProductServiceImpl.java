package com.mega.e_commerce_system.Modules.product.Service.ServiceImpl;

import com.mega.e_commerce_system.Exceptions.CategoryNotFoundException;
import com.mega.e_commerce_system.Exceptions.ProductNotFoundException;
import com.mega.e_commerce_system.Exceptions.ProductPurchaseException;
import com.mega.e_commerce_system.Modules.product.Repository.ProductRepository;
import com.mega.e_commerce_system.Modules.product.Payload.CategoryResponse;
import com.mega.e_commerce_system.Modules.product.Repository.CategoryRepository;
import com.mega.e_commerce_system.Modules.payment.Payload.ProductPurchaseRequest;
import com.mega.e_commerce_system.Modules.product.Entities.Category;
import com.mega.e_commerce_system.Modules.product.Entities.Product;
import com.mega.e_commerce_system.Modules.product.Payload.ProductPurchaseResponse;
import com.mega.e_commerce_system.Modules.product.Payload.ProductRequest;
import com.mega.e_commerce_system.Modules.product.Payload.ProductResponse;
import com.mega.e_commerce_system.Modules.product.Service.ProductService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    @Override
    public Integer createProduct(ProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(()-> new CategoryNotFoundException("Category","categoryId", request.getCategoryId()));

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .availableQuantity(request.getAvailableQuantity())
                .price(request.getPrice())
                .category(category)
                .build();
        Product savedProduct = productRepository.save(product);
        return savedProduct.getId();
    }

    @Override
    public List<ProductPurchaseResponse> purchaseProduct(List<ProductPurchaseRequest> request) {
        //getting list of productIds from the request
        List<Integer> productIds = request.stream()
                .map(ProductPurchaseRequest::getProductId)
                .toList();

        //finding products by id in order by id
        List<Product> savedProducts = productRepository.findAllByIdInOrderById(productIds);

        //checking the Availability
        if(productIds.size() != savedProducts.size())
        {
            throw  new ProductPurchaseException("one or more products are not in stock");
        }

        //sorting the product id's of the requested products
        var purchaseRequests = request.stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::getProductId))
                .toList();

        // list of purchased products
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();

        //checking quantity availability of each product and proceeding the purchasing process
        for(int i = 0; i < savedProducts.size(); i++)
        {
            var product = savedProducts.get(i);
            var productRequest = purchaseRequests.get(i);

            if(product.getAvailableQuantity() < productRequest.getQuantity())
            {
                throw new ProductPurchaseException("Insufficient stock quantity for product with Id::"+ productRequest.getProductId());
            }

            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.getQuantity();
            product.setAvailableQuantity(newAvailableQuantity);

            productRepository.save(product);
            purchasedProducts.add(mapToProductPurchaseResponse(product,productRequest.getQuantity()));
        }
        return purchasedProducts;
    }

    private ProductPurchaseResponse mapToProductPurchaseResponse(Product product, Double quantity) {

        return ProductPurchaseResponse.builder()
                .productId(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(quantity)
                .build();
    }

    @Override
    public ProductResponse getProductById(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException("Product","productId",productId));
        log.info("product category ::",product.getCategory());
        return mapToProductResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .availableQuantity(product.getAvailableQuantity())
                .price(product.getPrice())
                .createdDate(product.getCreatedDate())
                .lastUpdatedDate(product.getLastUpdatedDate())
                .categoryResponse(CategoryResponse.builder()
                        .id(product.getCategory().getId())
                        .name(product.getCategory().getName())
                        .description(product.getCategory().getDescription())
                        .build())
                .build();
    }

    @Override
    public void deleteProductById(Integer productId)
    {
        if(!productRepository.existsById(productId))
        {
           throw  new ProductNotFoundException("Product","productId",productId);
        }
        productRepository.deleteById(productId);
    }

    @Override
    public Integer updateProductById(Integer productId,ProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException("Product","productId",productId));

        modifyProduct(product,request);

        Product savedProduct = productRepository.save(product);
        return savedProduct.getId();
    }

    @Override
    public List<ProductResponse> getAllByCategoryId(Integer categoryId) {
        List<Product> products = productRepository.findAllByCategoryId(categoryId);
        return products.stream().map(this::mapToProductResponse).toList();
    }

    private void modifyProduct(Product product, ProductRequest request) {

        if(StringUtils.isNotBlank(request.getName()))
        {
            product.setName(request.getName());
        }
        if(StringUtils.isNotBlank(request.getDescription()))
        {
            product.setDescription(request.getDescription());
        }
        if(request.getAvailableQuantity() != null && request.getAvailableQuantity() > 0)
        {
            product.setAvailableQuantity(request.getAvailableQuantity());
        }
        if(request.getPrice() != null)
        {
            product.setPrice(request.getPrice());
        }
        if(request.getCategoryId() != null)
        {
            product.setCategory(Category.builder().id(request.getCategoryId()).build());
        }

    }
}
