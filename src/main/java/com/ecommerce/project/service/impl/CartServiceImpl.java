package com.ecommerce.project.service.impl;

import com.ecommerce.project.dto.CartItemRequest;
import com.ecommerce.project.dto.CartItemResponse;
import com.ecommerce.project.dto.CartResponse;
import com.ecommerce.project.entity.Cart;
import com.ecommerce.project.entity.CartItem;
import com.ecommerce.project.entity.Product;
import com.ecommerce.project.entity.User;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.repositories.CartItemRepository;
import com.ecommerce.project.repositories.CartRepository;
import com.ecommerce.project.repositories.ProductRepository;
import com.ecommerce.project.repositories.UserRepository;
import com.ecommerce.project.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;


    @Override
    public CartResponse addToCart(CartItemRequest request) {

        String email = SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();
        User user = userRepository.findByEmail(email);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        Cart cart = user.getCart();

        CartItem existingItem = null;

        for(CartItem cartItem : cart.getItems()){
            if (cartItem.getProduct().getId().equals(product.getId())){
                existingItem = cartItem;
                break;
            }
        }

        if (existingItem != null){
            existingItem.setQuantity(
                    existingItem.getQuantity() + request.getQuantity()
            );
        }
        else {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(request.getQuantity());

            cart.getItems().add(item);

        }
        cartRepository.save(cart);

        int totalPrice = 0;
        for (CartItem cartItem: cart.getItems()){
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        List<CartItemResponse>itemResponses = new ArrayList<>();
        for (CartItem cartItem: cart.getItems()){
            CartItemResponse response = new CartItemResponse(
                    cartItem.getId(),
                    cartItem.getProduct().getName(),
                    cartItem.getQuantity(),
                    cartItem.getProduct().getPrice()
            );
            itemResponses.add(response);
        }

        return new CartResponse(
                itemResponses,
                totalPrice
        );
    }

    @Override
    public CartResponse updateQuantity(Long id, CartItemRequest request){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        Cart cart = user.getCart();
        CartItem existingItem = null;

        for(CartItem cartItem : cart.getItems()){
            if (cartItem.getProduct().getId().equals(product.getId())){
                existingItem = cartItem;
                break;
            }
        }

        if (existingItem == null){
            throw new ResourceNotFoundException("Cart Item not found!");
        }

        if(request.getQuantity() == 0){
            cart.getItems().remove(existingItem);
        } else if (request.getQuantity() <0) {
            throw new ResourceNotFoundException("Quantity cannot be negative");
        }
        else {
            existingItem.setQuantity(request.getQuantity());
            cartItemRepository.save(existingItem);
        }

        int totalPrice = 0;
        for (CartItem cartItem: cart.getItems()){
            totalPrice +=
                    cartItem.getProduct().getPrice()
                    *cartItem.getQuantity();
        }

        List<CartItemResponse>itemResponses = new ArrayList<>();
        for (CartItem cartItem: cart.getItems()){
            CartItemResponse response = new CartItemResponse(
                    cartItem.getId(),
                    cartItem.getProduct().getName(),
                    cartItem.getQuantity(),
                    cartItem.getProduct().getPrice()
            );

            itemResponses.add(response);
        }


        return new CartResponse(
                itemResponses,
                totalPrice
        );
    }

    @Override
    public String deleteItem(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        Cart cart = user.getCart();
        CartItem existingItem = null;

        for(CartItem cartItem : cart.getItems()){
            if (cartItem.getProduct().getId().equals(product.getId())){
                existingItem = cartItem;
                break;
            }
        }

        cartItemRepository.delete(existingItem);

        return "Item deleted";
    }

    @Override
    public CartResponse viewCart() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        Cart cart = user.getCart();
        int totalPrice = 0;
        for (CartItem cartItem: cart.getItems()){
            totalPrice +=
                    cartItem.getProduct().getPrice()
                            *cartItem.getQuantity();
        }

        List<CartItemResponse> itemResponse = new ArrayList<>();
        for (CartItem cartItem: cart.getItems()){
            CartItemResponse response = new CartItemResponse(
                    cartItem.getProduct().getId(),
                    cartItem.getProduct().getName(),
                    cartItem.getQuantity(),
                    cartItem.getProduct().getPrice()
            );
            itemResponse.add(response);
        }
        return new CartResponse(
                itemResponse,
                totalPrice
        );
    }
}
