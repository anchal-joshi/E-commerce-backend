package com.ecommerce.project.service.impl;
import com.ecommerce.project.dto.AuthResponse;
import com.ecommerce.project.dto.LoginRequest;
import com.ecommerce.project.dto.RegisterRequest;
import com.ecommerce.project.entity.Cart;
import com.ecommerce.project.entity.User;
import com.ecommerce.project.exception.InvalidCredentialsException;
import com.ecommerce.project.exception.UserAlreadyExistsException;
import com.ecommerce.project.repositories.CartRepository;
import com.ecommerce.project.repositories.UserRepository;
import com.ecommerce.project.service.AuthService;
import com.ecommerce.project.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CartRepository cartRepository;

    //REGISTER
    @Override
    public AuthResponse register(RegisterRequest request){
        if (userRepository.existsByEmail(request.getEmail())){
            throw new UserAlreadyExistsException("Email already exists.");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_USER");

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setItems(new ArrayList<>());

        user.setCart(cart);

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(
                token,
                user.getEmail(),
                user.getRole()
        );
    }

    //Login
    @Override
    public AuthResponse login(LoginRequest request) {

        String email = request.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with this email"));


        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials.");
        }

        String token = jwtUtil.generateToken(email);

        System.out.println("LOGIN REQUEST EMAIL: "+ email);
        return new AuthResponse(
                token,
                user.getEmail(),
                user.getRole()
        );
    }


}
