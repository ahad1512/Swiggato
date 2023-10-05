package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.request.FoodRequest;
import com.example.Swiggato.dto.response.FoodResponse;
import com.example.Swiggato.model.FoodItem;

public class FoodItemTransformer {

    public static FoodItem FoodRequestToFoodItem(FoodRequest foodRequest){
        return FoodItem.builder()
                .dishName(foodRequest.getDishName())
                .veg(foodRequest.isVeg())
                .price(foodRequest.getPrice())
                .foodCategory(foodRequest.getCategory())
                .available(foodRequest.isAvailable())
                .build();
    }

    public static FoodResponse FoodItemToFoodResponse(FoodItem foodItem){
        return FoodResponse.builder()
                .dishName(foodItem.getDishName())
                .foodCategory(foodItem.getFoodCategory())
                .price(foodItem.getPrice())
                .veg(foodItem.isVeg())
                .build();
    }
}
