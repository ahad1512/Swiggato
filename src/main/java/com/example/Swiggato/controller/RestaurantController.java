package com.example.Swiggato.controller;

import com.example.Swiggato.dto.request.FoodRequest;
import com.example.Swiggato.dto.request.RestaurantRequest;
import com.example.Swiggato.dto.response.RestaurantResponse;
import com.example.Swiggato.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    final RestaurantService restaurantService;
    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/add")
    public ResponseEntity addRestaurant (@RequestBody RestaurantRequest restaurantRequest){
       RestaurantResponse restaurantResponse = restaurantService.addRestaurant(restaurantRequest);
       return new ResponseEntity<>(restaurantResponse, HttpStatus.CREATED);
    }

    @PutMapping("/update/status")
    public ResponseEntity changeOpenedStatus(@RequestParam("id") int id){
        try {
            String message = restaurantService.changeOpenedStatus(id);
            return new ResponseEntity(message,HttpStatus.FOUND);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add/food")
    public ResponseEntity addFoodItemToRestaurant(@RequestBody FoodRequest foodRequest){
     try {
         RestaurantResponse restaurantResponse = restaurantService.addFoodItemToRestaurant(foodRequest);
         return new ResponseEntity(restaurantResponse,HttpStatus.FOUND);
     }
     catch (Exception e){
         return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
     }
    }
}
