package com.ecommerce.project.service;

import com.ecommerce.project.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    public OrderResponse placeOrder();
    public List<OrderResponse>getOrderHistory();
    public OrderResponse updateOrderStatus(Long id, String status);
}
