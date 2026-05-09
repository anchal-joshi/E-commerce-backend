package com.ecommerce.project.repositories;

import com.ecommerce.project.entity.Cart;
import com.ecommerce.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long > {
    boolean existsByEmail(String email);
    User findByEmail(String email);
    Cart findByCart(Cart cart);
}
