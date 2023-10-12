package com.example.Swiggato.service;

import com.example.Swiggato.dto.request.FoodRequest;
import com.example.Swiggato.dto.response.CartStatusResponse;
import com.example.Swiggato.dto.response.FoodResponse;
import com.example.Swiggato.exceptions.CustomerNotFoundException;
import com.example.Swiggato.exceptions.MenuItemNotFoundException;
import com.example.Swiggato.exceptions.RestaurantNotFoundException;
import com.example.Swiggato.model.Cart;
import com.example.Swiggato.model.Customer;
import com.example.Swiggato.model.FoodItem;
import com.example.Swiggato.model.MenuItem;
import com.example.Swiggato.repository.CartRepository;
import com.example.Swiggato.repository.CustomerRepository;
import com.example.Swiggato.repository.FoodRepository;
import com.example.Swiggato.repository.MenuRepository;
import com.example.Swiggato.transformer.CartTransformer;
import com.example.Swiggato.transformer.FoodItemTransformer;
import com.example.Swiggato.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    final MenuRepository menuRepository;
    final CustomerRepository customerRepository;
    final CartRepository cartRepository;
    final ValidationUtils validationUtils;
    final FoodRepository foodRepository;

    @Autowired
    public CartService(MenuRepository menuRepository, CustomerRepository customerRepository, CartRepository cartRepository, ValidationUtils validationUtils, FoodRepository foodRepository) {
        this.menuRepository = menuRepository;
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
        this.validationUtils = validationUtils;
        this.foodRepository = foodRepository;
    }

    public CartStatusResponse addFoodItemToCart(FoodRequest foodRequest) {
        // validate customer
        if(!validationUtils.validateCustomerMobile(foodRequest.getCustomerMobileNo())){
            throw new CustomerNotFoundException("Customer doesn't exists");
        }
        Customer customer = customerRepository.findByMobileNo(foodRequest.getCustomerMobileNo()); // get customer
        // validate menu item
        if (!validationUtils.validateMenuItem(foodRequest.getMenuItemId())){
            throw new MenuItemNotFoundException("Item not available in the restaurant!!!");
        }
        MenuItem menuItem = menuRepository.findById(foodRequest.getMenuItemId()).get();//get menu
        //check restaurant is open or closed
        if (!menuItem.getRestaurant().isOpened()){
            throw new RestaurantNotFoundException("Restaurant is currently closed !!!");
        }
        // check whether item is available or not
        if (!menuItem.isAvailable()){
            throw new MenuItemNotFoundException("Given dish is out of stock for now!!!");
        }
        //prepare food item
        FoodItem foodItem = FoodItemTransformer.PrepareFoodItem(menuItem,foodRequest);

        Cart cart = customer.getCart(); // get cart
        FoodItem savedFood = foodRepository.save(foodItem);
        // calculate cart total
        double cartTotal =0 ;
        cart.getFoodItems().add(savedFood);
        for (FoodItem food : cart.getFoodItems()){
            cartTotal += food.getRequiredQuantity()*food.getMenuItem().getPrice();
        }
        savedFood.setCart(cart);
        cart.setCartTotal(cartTotal);
        menuItem.getFoodItems().add(savedFood);

        //save cart and menu
        Cart savedCart = cartRepository.save(cart);
        MenuItem savedMenuItem = menuRepository.save(menuItem);

        // prepare response
        return CartTransformer.prepareCartStatusResponse(savedCart);
    }
}
