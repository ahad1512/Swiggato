package com.example.Swiggato.service;

import com.example.Swiggato.dto.request.FoodRequest;
import com.example.Swiggato.dto.response.CartStatusResponse;

public interface CartService {
    CartStatusResponse addFoodItemToCart(FoodRequest foodRequest);
}
