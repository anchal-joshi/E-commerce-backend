package com.ecommerce.project.service.impl;

import com.ecommerce.project.dto.OrderItemResponse;
import com.ecommerce.project.dto.OrderResponse;
import com.ecommerce.project.entity.*;
import com.ecommerce.project.exception.EmptyCartException;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.repositories.CartRepository;
import com.ecommerce.project.repositories.OrderRepository;
import com.ecommerce.project.repositories.ProductRepository;
import com.ecommerce.project.repositories.UserRepository;
import com.ecommerce.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public OrderResponse placeOrder() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with email: "+ email
                        ));

        Cart cart = user.getCart();

        if (cart == null || cart.getItems().isEmpty()){
            throw new EmptyCartException("Your Cart is empty.");
        }
        Order order = new Order();
        order.setUser(user);
        order.setStatus("PLACED");

        List<OrderItem>orderItems = new ArrayList<>();
        List<OrderItemResponse> itemResponses = new ArrayList<>();

        int totalPrice = 0;

        for (CartItem cartItem: cart.getItems()){
            Product product = cartItem.getProduct();

            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException(
                        "Not enough stock for product: " + product.getName()
                );
            }
            product.setStock(
                    product.getStock() - cartItem.getQuantity()
            );

            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItems.add(orderItem);
            totalPrice += cartItem.getQuantity() * product.getPrice();

            OrderItemResponse response =
                    new OrderItemResponse(
                            product.getId(),
                            product.getName(),
                            cartItem.getQuantity(),
                            product.getPrice()
                    );
            itemResponses.add(response);

        }

        order.setItems(orderItems);
        order.setTotal_price(totalPrice);
        order.setCreatedDate(LocalDate.now());

        cart.getItems().clear();

        cartRepository.save(cart);
        orderRepository.save(order);

        return new OrderResponse(
                order.getId(),
                order.getStatus(),
                itemResponses,
                order.getTotal_price(),
                order.getCreatedDate()
        );
    }

    @Override
    public List<OrderResponse> getOrderHistory() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: "+ email));


        List<Order>allOrders = user.getOrders();
        List<OrderResponse>allOrdersResponse = new ArrayList<>();
        for (Order order : allOrders) {

            List<OrderItemResponse> itemResponses = new ArrayList<>();

            for (OrderItem item : order.getItems()) {

                OrderItemResponse itemResponse =
                        new OrderItemResponse(
                                item.getProduct().getId(),
                                item.getProduct().getName(),
                                item.getQuantity(),
                                item.getPrice()
                        );

                itemResponses.add(itemResponse);
            }

            OrderResponse orderResponse = new OrderResponse(
                    order.getId(),
                    order.getStatus(),
                    itemResponses,
                    order.getTotal_price(),
                    order.getCreatedDate()
            );

            allOrdersResponse.add(orderResponse);
        }
        return allOrdersResponse;
    }



    @Override
    public OrderResponse updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order with the ID not found"));
        order.setStatus(status);
        orderRepository.save(order);
        List<OrderItemResponse>itemResponses = new ArrayList<>();
        for(OrderItem item: order.getItems()){
            OrderItemResponse response =
                    new OrderItemResponse(
                            item.getProduct().getId(),
                            item.getProduct().getName(),
                            item.getQuantity(),
                            item.getPrice()
                    );
            itemResponses.add(response);
        }
        return new OrderResponse(
                order.getId(),
                order.getStatus(),
                itemResponses,
                order.getTotal_price(),
                order.getCreatedDate()
        );
    }
}
