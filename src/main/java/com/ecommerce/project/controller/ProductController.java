package com.ecommerce.project.controller;

import com.ecommerce.project.dto.ProductRequest;
import com.ecommerce.project.dto.ProductResponse;
import com.ecommerce.project.service.ProductService;
import com.ecommerce.project.service.impl.ProductServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private ProductServiceImpl productService;

    @GetMapping("api/products")
    public ResponseEntity<List<ProductResponse>>getProducts(){
        List<ProductResponse>allProducts = productService.getAllProducts();
        return ResponseEntity.ok(allProducts);
    }

    @GetMapping("api/product/{id}")
    public ResponseEntity<ProductResponse>getProduct(@PathVariable Long id){
        return ResponseEntity.ok(productService.getSingleProduct(id));
    }

    @PostMapping("/api/products")
    public ResponseEntity<ProductResponse>addProduct(@RequestBody ProductRequest request){
        return ResponseEntity.ok(productService.addProduct(request));
    }

    @PutMapping("/api/products/{id}")
    public ResponseEntity<ProductResponse>updateProduct(@RequestBody ProductRequest request,
                                                        @PathVariable Long id){
        ProductResponse productResponse = productService.updateProduct(id, request);
        return ResponseEntity.ok(productResponse);
    }

    @DeleteMapping("/api/products/{id}")
    public ResponseEntity<String>deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.ok("Deleted");
    }
}
