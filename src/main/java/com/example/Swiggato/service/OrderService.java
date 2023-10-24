package com.example.Swiggato.service;

import com.example.Swiggato.dto.response.OrderResponse;
import com.example.Swiggato.model.Cart;

import java.util.ArrayList;

public interface OrderService {
    OrderResponse placeOrder(String customerMobileNo);

    default void clearCart(Cart cart) {
        cart.setCartTotal(0);
        cart.setFoodItems(new ArrayList<>());
    }
}
