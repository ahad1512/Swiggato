package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.request.FoodRequest;
import com.example.Swiggato.dto.response.FoodResponse;
import com.example.Swiggato.model.Cart;
import com.example.Swiggato.model.FoodItem;
import com.example.Swiggato.model.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class FoodItemTransformer {

    public static FoodItem PrepareFoodItem (MenuItem menuItem, FoodRequest foodRequest){
        return FoodItem.builder()
                .menuItem(menuItem)
                .requiredQuantity(foodRequest.getRequiredQuantity())
                .totalCost(foodRequest.getRequiredQuantity()*menuItem.getPrice())
                .build();
    }
    public static FoodResponse FoodItemToFoodResponse(FoodItem foodItem){
        return FoodResponse.builder()
                .veg(foodItem.getMenuItem().isVeg())
                .price(foodItem.getMenuItem().getPrice())
                .dishName(foodItem.getMenuItem().getDishName())
                .category(foodItem.getMenuItem().getCategory())
                .quantityAdded(foodItem.getRequiredQuantity())
                .restaurantName(foodItem.getMenuItem().getRestaurant().getName())
                .build();
    }
    public static List<FoodResponse> getFoodItemListResponse (List<FoodItem> foodItems){
        List<FoodResponse> foodResponseList = new ArrayList<>();
        for (FoodItem foodItem : foodItems){
            foodResponseList.add(FoodItemTransformer.FoodItemToFoodResponse(foodItem));
        }
        return foodResponseList;
    }
}
