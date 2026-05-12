package com.ecommerce.project.service.impl;

import com.ecommerce.project.dto.ProductRequest;
import com.ecommerce.project.dto.ProductResponse;
import com.ecommerce.project.entity.Product;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.repositories.ProductRepository;
import com.ecommerce.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductResponse addProduct(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getQuantity());
        product.setCategory_id(request.getCategory_id());
        productRepository.save(product);

        return new ProductResponse(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: "+ id));
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getQuantity());
        productRepository.save(product);
        return new ProductResponse(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );
    }

    @Override
    public String deleteProduct(Long id) {
        if (!productRepository.existsById(id)){
            throw new ResourceNotFoundException("Product not found with ID: "+ id);
        }
        productRepository.deleteById(id);
        return "Product with ID: "+ id + " deleted.";
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> allProducts = productRepository.findAll();
        List<ProductResponse>responseList = new ArrayList<>();
        for(Product product : allProducts){
            ProductResponse response = new ProductResponse(
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getStock()
            );

            responseList.add(response);
        }

        return responseList;
    }

    @Override
    public ProductResponse getSingleProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: "+ id));
        return new ProductResponse(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );
    }
}
