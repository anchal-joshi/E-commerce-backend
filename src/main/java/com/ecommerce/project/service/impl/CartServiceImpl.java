package com.ecommerce.project.service.impl;

import com.ecommerce.project.dto.CartItemRequest;
import com.ecommerce.project.dto.CartItemResponse;
import com.ecommerce.project.dto.CartResponse;
import com.ecommerce.project.dto.UpdateQuantityRequest;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by email :" + email));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        Cart cart = user.getCart();

        CartItem existingItem = null;

        if (request.getQuantity() > product.getStock()) {
            throw new RuntimeException("Insufficient stock");
        }
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
                    cartItem.getProduct().getId(),
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
    public CartResponse updateQuantity(Long id, UpdateQuantityRequest request){
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with email: "+ email));

        Cart cart = user.getCart();

        CartItem existingItem = cartItemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Cart item not found"));


        //Security Check
        if (!existingItem.getCart().getId().equals(cart.getId())){
            throw new ResourceNotFoundException("cart item does not belong to user");
        }

        if (request.getQuantity() == 0){
            cart.getItems().remove(existingItem);
            cartItemRepository.delete(existingItem);
        } else if (request.getQuantity() < 0) {
            throw new ResourceNotFoundException("Quantity cannot be negative");
        }
        else {
            existingItem.setQuantity(request.getQuantity());
            cartItemRepository.save(existingItem);
        }


        int totalPrice = 0;
        for (CartItem item: cart.getItems()){
            totalPrice +=
                    item.getProduct().getPrice() * item.getQuantity();
        }


        List<CartItemResponse>itemResponses = new ArrayList<>();
        for (CartItem item: cart.getItems()){
            CartItemResponse response = new CartItemResponse(
                    item.getId(),
                    item.getProduct().getId(),
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getProduct().getPrice()
            );

            itemResponses.add(response);
        }
        return new CartResponse(itemResponses, totalPrice);


    }

    @Override
    public String deleteItem(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: "+ email));

        Cart cart = user.getCart();
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (!cartItem.getCart().getId().equals(cart.getId())){
            throw new ResourceNotFoundException(
                    "Cart item does not belong to this user"
            );
        }

        cartItemRepository.delete(cartItem);

        return "Item deleted";
    }

    @Override
    public CartResponse viewCart() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: "+ email));

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
                    cartItem.getId(),
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
