package com.ecommerce.project.repositories;

import com.ecommerce.project.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem>findItemsByCartId(Long cartId);
}
