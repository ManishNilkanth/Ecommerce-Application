package com.mega.e_commerce_system.Modules.product.Service;

import com.mega.e_commerce_system.Modules.product.Payload.ProductPurchaseResponse;
import com.mega.e_commerce_system.Modules.product.Payload.ProductRequest;
import com.mega.e_commerce_system.Modules.product.Payload.ProductResponse;
import com.mega.e_commerce_system.Modules.payment.Payload.ProductPurchaseRequest;

import java.util.List;

public interface ProductService {
    Integer createProduct(ProductRequest request);

    List<ProductPurchaseResponse> purchaseProduct(List<ProductPurchaseRequest> request);

    ProductResponse getProductById(Integer productId);

    List<ProductResponse> getAllProducts();

    void deleteProductById(Integer productId);

    Integer updateProductById(Integer productId,ProductRequest productRequest);

    List<ProductResponse> getAllByCategoryId(Integer categoryId);
}
