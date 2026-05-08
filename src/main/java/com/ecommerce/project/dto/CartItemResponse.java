package com.ecommerce.project.dto;

public class CartItemResponse {
    private Long productId;
    private String ProductName;
    private int quantity;
    private int price;

    public CartItemResponse(Long productId, String productName, int quantity, int price) {
        this.productId = productId;
        ProductName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public CartItemResponse() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
