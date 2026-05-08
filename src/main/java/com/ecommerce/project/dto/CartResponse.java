package com.ecommerce.project.dto;

import com.ecommerce.project.entity.CartItem;

import java.util.List;

public class CartResponse {

    private List<CartItemResponse>cartItems;
    private int totalPrice;

    public CartResponse(List<CartItemResponse> cartItems, int totalPrice) {
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
    }

    public CartResponse() {
    }

    public List<CartItemResponse> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemResponse> cartItems) {
        this.cartItems = cartItems;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
