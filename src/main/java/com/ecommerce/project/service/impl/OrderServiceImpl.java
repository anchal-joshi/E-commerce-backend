package com.ecommerce.project.service.impl;

import com.ecommerce.project.dto.OrderItemResponse;
import com.ecommerce.project.dto.OrderResponse;
import com.ecommerce.project.entity.*;
import com.ecommerce.project.repositories.OrderRepository;
import com.ecommerce.project.repositories.UserRepository;
import com.ecommerce.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderResponse placeOrder() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        Cart cart = user.getCart();
        if (cart == null || cart.getItems().isEmpty()){
            throw new RuntimeException("Your Cart is empty.");
        }
        Order order = new Order();
        order.setUser(user);
        order.setStatus("PLACED");
        List<OrderItem>orderItems = new ArrayList<>();
        int totalPrice = 0;
        for (CartItem cartItem: cart.getItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            totalPrice += cartItem.getQuantity()
                    * cartItem.getProduct().getPrice();

            orderItems.add(orderItem);

        }

        List<OrderItemResponse>itemResponses = new ArrayList<>();
        for(OrderItem orderItem: orderItems){
            OrderItemResponse response = new OrderItemResponse(
                    orderItem.getProduct().getId(),
                    orderItem.getProduct().getName(),
                    orderItem.getQuantity(),
                    orderItem.getPrice()
            );

            itemResponses.add(response);
        }

        order.setItems(orderItems);
        order.setTotal_price(totalPrice);

        cart.getItems().clear();
        orderRepository.save(order);


        return new OrderResponse(
                order.getId(),
                order.getStatus(),
                itemResponses,
                order.getTotal_price(),
                new Date(System.currentTimeMillis())
        );
    }

    @Override
    public List<OrderResponse> getOrderHistory() {
        return List.of();
    }

    @Override
    public OrderResponse updateOrderStatus() {
        return null;
    }
}
