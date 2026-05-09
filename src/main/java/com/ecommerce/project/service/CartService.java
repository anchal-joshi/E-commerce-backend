package com.ecommerce.project.service;

import com.ecommerce.project.dto.CartItemRequest;
import com.ecommerce.project.dto.CartResponse;

public interface CartService {
    public CartResponse addToCart(CartItemRequest request);
    public CartResponse updateQuantity(Long id, CartItemRequest request);
    public String deleteItem(Long id);
    public CartResponse viewCart();
}
 