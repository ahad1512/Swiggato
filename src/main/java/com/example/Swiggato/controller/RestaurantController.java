package com.example.Swiggato.controller;

import com.example.Swiggato.dto.request.MenuRequest;
import com.example.Swiggato.dto.request.RestaurantRequest;
import com.example.Swiggato.dto.response.MenuResponse;
import com.example.Swiggato.dto.response.RestaurantResponse;
import com.example.Swiggato.service.Impl.RestaurantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    final RestaurantServiceImpl restaurantServiceImpl;
    @Autowired
    public RestaurantController(RestaurantServiceImpl restaurantServiceImpl) {
        this.restaurantServiceImpl = restaurantServiceImpl;
    }

    @PostMapping("/add")
    public ResponseEntity addRestaurant (@RequestBody RestaurantRequest restaurantRequest){
       RestaurantResponse restaurantResponse = restaurantServiceImpl.addRestaurant(restaurantRequest);
       return new ResponseEntity<>(restaurantResponse, HttpStatus.CREATED);
    }

    @PutMapping("/update/status")
    public ResponseEntity changeOpenedStatus(@RequestParam("id") int id){
        try {
            String message = restaurantServiceImpl.changeOpenedStatus(id);
            return new ResponseEntity(message,HttpStatus.FOUND);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add/food")
    public ResponseEntity addMenuItemToRestaurant(@RequestBody MenuRequest menuRequest){
     try {
         RestaurantResponse restaurantResponse = restaurantServiceImpl.addMenuItemToRestaurant(menuRequest);
         return new ResponseEntity(restaurantResponse,HttpStatus.FOUND);
     }
     catch (Exception e){
         return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
     }
    }
    //get menu of a restaurant;
    @GetMapping("/get/menu")
    public ResponseEntity getMenuOfRestaurant(@RequestParam("id") int id){
        try {
            List<MenuResponse> menu = restaurantServiceImpl.getMenuOfRestaurant(id);
            return new ResponseEntity(menu,HttpStatus.FOUND);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
