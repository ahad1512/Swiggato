package com.example.Swiggato.service;

import com.example.Swiggato.dto.request.FoodRequest;
import com.example.Swiggato.dto.request.RestaurantRequest;
import com.example.Swiggato.dto.response.RestaurantResponse;
import com.example.Swiggato.exceptions.RestaurantNotFoundException;
import com.example.Swiggato.model.FoodItem;
import com.example.Swiggato.model.Restaurant;
import com.example.Swiggato.repository.RestaurantRepository;
import com.example.Swiggato.transformer.FoodItemTransformer;
import com.example.Swiggato.transformer.RestaurantTransformer;
import com.example.Swiggato.utils.ValidationUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantService {
    final RestaurantRepository restaurantRepository;
    final ValidationUtils validationUtils;

    public RestaurantService(RestaurantRepository restaurantRepository, ValidationUtils validationUtils) {
        this.restaurantRepository = restaurantRepository;
        this.validationUtils = validationUtils;
    }

    public RestaurantResponse addRestaurant(RestaurantRequest restaurantRequest) {
        //dto -->> model
        Restaurant restaurant =RestaurantTransformer.RestaurantRequestToRestaurant(restaurantRequest);
        //save restaurant
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        //model -->> dto
        return RestaurantTransformer.RestaurantToRestaurantResponse(savedRestaurant);
    }

    public String changeOpenedStatus(int id) {
        //check whether id is valid or not
        if (!validationUtils.validateRestaurantId(id)) {
            throw new RestaurantNotFoundException("Restaurant doesn't exist!!");
        }
        Restaurant restaurant = restaurantRepository.findById(id).get();
        restaurant.setOpened(!restaurant.isOpened()); // if open -->> close , if close -->> open
        restaurantRepository.save(restaurant);

        if (restaurant.isOpened()){
            return "Restaurant is opened now";
        }
        return "Restaurant is closed";
    }

    public RestaurantResponse addFoodItemToRestaurant(FoodRequest foodRequest) {
        //verify id is correct or not
        if (!validationUtils.validateRestaurantId(foodRequest.getRestaurantId())) {
            throw new RestaurantNotFoundException("Restaurant doesn't exist!!");
        }
        Restaurant restaurant = restaurantRepository.findById(foodRequest.getRestaurantId()).get();
        //foodRequest -->> foodItem , Dto -->> Model
        FoodItem foodItem = FoodItemTransformer.FoodRequestToFoodItem(foodRequest);
        foodItem.setRestaurant(restaurant);

        // Add food to restaurant
        restaurant.getAvailableFoodItems().add(foodItem);
        //save restaurant and food
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        //prepare response
        return RestaurantTransformer.RestaurantToRestaurantResponse(savedRestaurant);
    }
}
