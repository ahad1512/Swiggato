package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.response.CartResponse;
import com.example.Swiggato.dto.response.CartStatusResponse;
import com.example.Swiggato.dto.response.FoodResponse;
import com.example.Swiggato.model.Cart;
import com.example.Swiggato.model.FoodItem;

import java.util.ArrayList;
import java.util.List;

public class CartTransformer {

    public static CartResponse CartToCartResponse(Cart cart){
        List<FoodResponse> foodResponseList = new ArrayList<>();

        for (FoodItem foodItem : cart.getFoodItems()) {
            foodResponseList.add(FoodItemTransformer.FoodItemToFoodResponse(foodItem));
        }
        return CartResponse.builder()
                .cartTotal(cart.getCartTotal())
                .foodItems(foodResponseList)
                .build();
    }
    public static CartStatusResponse prepareCartStatusResponse (Cart cart){
        List<FoodResponse> foodResponseList = new ArrayList<>();

        for (FoodItem foodItem : cart.getFoodItems()){
            foodResponseList.add(FoodItemTransformer.FoodItemToFoodResponse(foodItem));
        }

        return CartStatusResponse.builder()
                .cartTotal(cart.getCartTotal())
                .customerAddress(cart.getCustomer().getAddress())
                .customerMobile(cart.getCustomer().getMobileNo())
                .customerName(cart.getCustomer().getName())
                .foodList(foodResponseList)
                .build();
    }
    public static CartResponse newCartResponse(Cart cart){
        return CartResponse.builder()
                .cartTotal(0)
                .foodItems(new ArrayList<>())
                .build();
    }
}
