package com.ecommerce.project.dto;

import com.ecommerce.project.entity.OrderItem;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class OrderResponse {
    private Long orderId;
    private String status;
    private List<OrderItemResponse>items;
    private int totalPrice;
    private Date createdAt;

    public OrderResponse(Long orderId, String status, List<OrderItemResponse> items, int totalPrice, Date createdAt) {
        this.orderId = orderId;
        this.status = status;
        this.items = items;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
    }

    public OrderResponse() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
