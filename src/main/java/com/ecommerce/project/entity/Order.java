package com.ecommerce.project.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    public Order() {
    }

    public Order(Long id, User user, String status, int total_price, List<OrderItem> items) {
        this.id = id;
        this.user = user;
        this.status = status;
        this.total_price = total_price;
        this.items = items;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private int total_price;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem>items;

    public List<OrderItem> getItems() {

        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }
}
