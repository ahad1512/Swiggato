package com.example.Swiggato.service.Impl;

import com.example.Swiggato.dto.request.FoodRequest;
import com.example.Swiggato.dto.response.CartStatusResponse;
import com.example.Swiggato.exceptions.CustomerNotFoundException;
import com.example.Swiggato.exceptions.MenuItemNotFoundException;
import com.example.Swiggato.exceptions.RestaurantNotFoundException;
import com.example.Swiggato.model.*;
import com.example.Swiggato.repository.CartRepository;
import com.example.Swiggato.repository.CustomerRepository;
import com.example.Swiggato.repository.FoodRepository;
import com.example.Swiggato.repository.MenuRepository;
import com.example.Swiggato.service.CartService;
import com.example.Swiggato.transformer.CartTransformer;
import com.example.Swiggato.transformer.FoodItemTransformer;
import com.example.Swiggato.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    final MenuRepository menuRepository;
    final CustomerRepository customerRepository;
    final CartRepository cartRepository;
    final ValidationUtils validationUtils;
    final FoodRepository foodRepository;

    @Autowired
    public CartServiceImpl(MenuRepository menuRepository, CustomerRepository customerRepository, CartRepository cartRepository, ValidationUtils validationUtils, FoodRepository foodRepository) {
        this.menuRepository = menuRepository;
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
        this.validationUtils = validationUtils;
        this.foodRepository = foodRepository;
    }

    @Override
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

        Cart cart = customer.getCart(); // get cart

        //having item from same restaurant
        if(cart.getFoodItems().size()!=0){
            Restaurant currRestaurant = cart.getFoodItems().get(0).getMenuItem().getRestaurant();
            Restaurant newRestaurant = menuItem.getRestaurant();

            if (!currRestaurant.equals(newRestaurant)){
                List<FoodItem> foodItems = cart.getFoodItems();
                for (FoodItem foodItem : foodItems){
                    foodItem.setCart(null);
                    foodItem.setMenuItem(null);
                    foodItem.setOrder(null);
                }
                cart.setCartTotal(0);
                cart.getFoodItems().clear();
                foodRepository.deleteAll(foodItems);
            }
        }
        boolean alreadyExists = false;
        FoodItem savedFood = null;
        if (cart.getFoodItems().size()!=0){
            for (FoodItem foodItem : cart.getFoodItems()){
                if (foodItem.getMenuItem().getId() == menuItem.getId()){
                    savedFood = foodItem;
                    int curr = foodItem.getRequiredQuantity();
                    foodItem.setRequiredQuantity(curr+foodRequest.getRequiredQuantity());
                    alreadyExists = true;
                    break;
                }
            }
        }
        if(!alreadyExists){
            FoodItem foodItem = FoodItemTransformer.PrepareFoodItem(menuItem,foodRequest);
            savedFood = foodRepository.save(foodItem);
            cart.getFoodItems().add(savedFood);
            menuItem.getFoodItems().add(savedFood);
            savedFood.setCart(cart);
        }
        // calculate cart total
        double cartTotal =0 ;
        for (FoodItem food : cart.getFoodItems()){
            cartTotal += food.getRequiredQuantity()*food.getMenuItem().getPrice();
        }
        cart.setCartTotal(cartTotal);
        //save cart and menu
        Cart savedCart = cartRepository.save(cart);
        MenuItem savedMenuItem = menuRepository.save(menuItem);

        // prepare response
        return CartTransformer.prepareCartStatusResponse(savedCart);
    }
}
