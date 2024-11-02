package com.mega.e_commerce_system.Modules.product.Contoller;

import com.mega.e_commerce_system.Modules.product.Payload.ProductPurchaseResponse;
import com.mega.e_commerce_system.Modules.product.Payload.ProductRequest;
import com.mega.e_commerce_system.Modules.product.Payload.ProductResponse;
import com.mega.e_commerce_system.Modules.product.Service.ProductService;
import com.mega.e_commerce_system.Modules.payment.Payload.ProductPurchaseRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Integer> createProduct(@RequestBody @Valid ProductRequest request)
    {
        Integer productId = productService.createProduct(request);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProduct(@RequestBody @Valid List<ProductPurchaseRequest> request)
    {
        List<ProductPurchaseResponse> responses = productService.purchaseProduct(request);
        return new ResponseEntity<>(responses,HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Integer productId)
    {
        ProductResponse response = productService.getProductById(productId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts()
    {
        List<ProductResponse> responses = productService.getAllProducts();
        return new ResponseEntity<>(responses,HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Integer productId)
    {
        productService.deleteProductById(productId);
        return ResponseEntity.accepted().build();
    }


    @PutMapping("/{productId}")
    public ResponseEntity<Integer> updateProductById(@PathVariable Integer productId,@Valid @RequestBody ProductRequest request)
    {
        Integer id  = productService.updateProductById(productId,request);
        return new ResponseEntity<>(id,HttpStatus.OK);
    }
}
