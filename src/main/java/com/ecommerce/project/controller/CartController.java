package com.ecommerce.project.controller;

import com.ecommerce.project.dto.CartItemRequest;
import com.ecommerce.project.dto.CartResponse;
import com.ecommerce.project.dto.UpdateQuantityRequest;
import com.ecommerce.project.entity.Cart;
import com.ecommerce.project.service.impl.CartServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {

    @Autowired
    private CartServiceImpl cartService;

    @GetMapping("/api/cart")
    public ResponseEntity<CartResponse>getCart(){
        return ResponseEntity.ok(cartService.viewCart());
    }

    @PostMapping("/api/cart")
    public ResponseEntity<CartResponse>addItem(@Valid @RequestBody CartItemRequest request){

        return ResponseEntity.ok(cartService.addToCart(request));
    }

    @PutMapping("/api/cart/{id}")
    public ResponseEntity<CartResponse>update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateQuantityRequest request){

        return ResponseEntity.ok(cartService.updateQuantity(id, request));
    }

    @DeleteMapping("/api/cart/{id}")
    public ResponseEntity<String>delete(@PathVariable Long id){
        cartService.deleteItem(id);
        return ResponseEntity.ok("Cart Item deleted.");
    }

}
