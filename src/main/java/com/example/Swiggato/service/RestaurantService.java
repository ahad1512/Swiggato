package com.example.Swiggato.service;

import com.example.Swiggato.dto.request.MenuRequest;
import com.example.Swiggato.dto.request.RestaurantRequest;
import com.example.Swiggato.dto.response.MenuResponse;
import com.example.Swiggato.dto.response.RestaurantResponse;
import com.example.Swiggato.exceptions.RestaurantNotFoundException;
import com.example.Swiggato.model.MenuItem;
import com.example.Swiggato.model.Restaurant;
import com.example.Swiggato.repository.RestaurantRepository;
import com.example.Swiggato.transformer.MenuItemTransformer;
import com.example.Swiggato.transformer.RestaurantTransformer;
import com.example.Swiggato.utils.ValidationUtils;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public RestaurantResponse addMenuItemToRestaurant(MenuRequest menuRequest) {
        //verify id is correct or not
        if (!validationUtils.validateRestaurantId(menuRequest.getRestaurantId())) {
            throw new RestaurantNotFoundException("Restaurant doesn't exist!!");
        }
        Restaurant restaurant = restaurantRepository.findById(menuRequest.getRestaurantId()).get();
        //menuRequest -->> menuItem , Dto -->> Model
        MenuItem menuItem = MenuItemTransformer.MenuRequestToMenuItem(menuRequest);
        menuItem.setRestaurant(restaurant);

        // Add food to restaurant
        restaurant.getAvailableMenuItems().add(menuItem);
        //save restaurant and food
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        //prepare response
        return RestaurantTransformer.RestaurantToRestaurantResponse(savedRestaurant);
    }

    public List<MenuResponse> getMenuOfRestaurant(int id) {
        //validation check
        if(!validationUtils.validateRestaurantId(id)){
            throw new RestaurantNotFoundException("Restaurant doesn't exist");
        }
        Restaurant restaurant = restaurantRepository.findById(id).get();
        //getting menu of restaurant
        return RestaurantTransformer.getMenuOfRestaurant(restaurant);
    }
}
