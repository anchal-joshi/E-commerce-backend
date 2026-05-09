package com.ecommerce.project.service;

import com.ecommerce.project.dto.ProductRequest;
import com.ecommerce.project.dto.ProductResponse;
import com.ecommerce.project.entity.Product;

import java.util.List;

public interface ProductService {
    public ProductResponse addProduct(ProductRequest request);
    public ProductResponse updateProduct(Long id, ProductRequest request);
    public String deleteProduct(Long id);
    public List<ProductResponse> getAllProducts();
    public ProductResponse getSingleProduct(Long id);
}
