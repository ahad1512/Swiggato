package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.request.FoodRequest;
import com.example.Swiggato.dto.response.FoodResponse;
import com.example.Swiggato.model.Cart;
import com.example.Swiggato.model.FoodItem;
import com.example.Swiggato.model.MenuItem;

public class FoodItemTransformer {

    public static FoodItem PrepareFoodItem (MenuItem menuItem, Cart cart, FoodRequest foodRequest){
        return FoodItem.builder()
                .menuItem(menuItem)
                .cart(cart)
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
}
