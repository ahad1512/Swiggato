package com.example.Swiggato.utils;

import com.example.Swiggato.model.Customer;
import com.example.Swiggato.model.MenuItem;
import com.example.Swiggato.model.Restaurant;
import com.example.Swiggato.repository.CustomerRepository;
import com.example.Swiggato.repository.MenuRepository;
import com.example.Swiggato.repository.RestaurantRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ValidationUtils {

    final RestaurantRepository restaurantRepository;
    final CustomerRepository customerRepository;

    final MenuRepository menuRepository;
    public ValidationUtils(RestaurantRepository restaurantRepository, CustomerRepository customerRepository, MenuRepository menuRepository) {
        this.restaurantRepository = restaurantRepository;
        this.customerRepository = customerRepository;
        this.menuRepository = menuRepository;
    }

    public boolean validateRestaurantId(int id){
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        return restaurantOptional.isPresent();
    }
    public boolean validateCustomerMobile(String mobileNo){
        Customer customer = customerRepository.findByMobileNo(mobileNo);
        return customer != null;
    }
    public boolean validateMenuItem (int id){
        Optional<MenuItem> menuItemOptional = menuRepository.findById(id);
        return menuItemOptional.isPresent();
    }
}
