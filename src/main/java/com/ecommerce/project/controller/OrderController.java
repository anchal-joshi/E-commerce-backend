package com.ecommerce.project.controller;

import com.ecommerce.project.dto.OrderResponse;
import com.ecommerce.project.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping("/api/orders")
    public ResponseEntity<OrderResponse>placeOrder(){
        return ResponseEntity.ok(orderService.placeOrder());
    }

    @GetMapping("/api/orders")
    public ResponseEntity<List<OrderResponse>>getOrderHistory(){
        return ResponseEntity.ok(orderService.getOrderHistory());
    }

    @PutMapping("/api/orders/{id}/status")
    public ResponseEntity<OrderResponse>updateStatus(@PathVariable Long id,
                                                     @RequestBody String status){
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }
}
