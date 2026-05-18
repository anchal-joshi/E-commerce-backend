package com.ecommerce.project.dto;

import jakarta.validation.constraints.Min;

public class UpdateQuantityRequest {

    @Min(value = 0, message = "Quantity cannot be negative")
    private int quantity;

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
