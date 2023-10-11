package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.request.RestaurantRequest;
import com.example.Swiggato.dto.response.MenuResponse;
import com.example.Swiggato.dto.response.RestaurantResponse;
import com.example.Swiggato.model.Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantTransformer {

    public static Restaurant RestaurantRequestToRestaurant (RestaurantRequest restaurantRequest){
        return  Restaurant.builder()
                .name(restaurantRequest.getName())
                .contactNo(restaurantRequest.getContactNumber())
                .location(restaurantRequest.getLocation())
                .restaurantCategory(restaurantRequest.getRestaurantCategory())
                .opened(true)
                .orders(new ArrayList<>())
                .availableMenuItems(new ArrayList<>())
                .build();
    }
    public static RestaurantResponse RestaurantToRestaurantResponse (Restaurant restaurant){
//        List<MenuResponse> menu = restaurant.getAvailableMenuItems()
//                .stream()
//                .map(foodItem -> MenuItemTransformer.FoodItemToFoodResponse(foodItem))
//                .collect(Collectors.toList());
        List<MenuResponse> menu = getMenuOfRestaurant(restaurant);

       return RestaurantResponse.builder()
               .name(restaurant.getName())
               .contactNumber(restaurant.getContactNo())
               .location(restaurant.getLocation())
               .opened(restaurant.isOpened())
               .menu(menu)
               .build();
    }
    public static List<MenuResponse> getMenuOfRestaurant(Restaurant restaurant){
        List<MenuResponse> menu = restaurant.getAvailableMenuItems()
                .stream()
                .map(menuItem -> MenuItemTransformer.MenuItemToMenuResponse(menuItem))
                .collect(Collectors.toList());

        return menu;
    }
}
