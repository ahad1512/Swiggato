package com.example.Swiggato.service.Impl;

import com.example.Swiggato.dto.response.OrderResponse;
import com.example.Swiggato.exceptions.CustomerNotFoundException;
import com.example.Swiggato.exceptions.EmptyCartException;
import com.example.Swiggato.model.*;
import com.example.Swiggato.repository.CustomerRepository;
import com.example.Swiggato.repository.DeliveryPartnerRepository;
import com.example.Swiggato.repository.OrderRepository;
import com.example.Swiggato.repository.RestaurantRepository;
import com.example.Swiggato.service.OrderService;
import com.example.Swiggato.transformer.OrderEntityTransformer;
import com.example.Swiggato.utils.ValidationUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrderServiceImpl implements OrderService {
    final ValidationUtils validationUtils;
    final OrderRepository orderRepository;
    final CustomerRepository customerRepository;
    final DeliveryPartnerRepository deliveryPartnerRepository;
    final RestaurantRepository restaurantRepository;
    public OrderServiceImpl(ValidationUtils validationUtils, OrderRepository orderRepository, CustomerRepository customerRepository, DeliveryPartnerRepository deliveryPartnerRepository, RestaurantRepository restaurantRepository) {
        this.validationUtils = validationUtils;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.deliveryPartnerRepository = deliveryPartnerRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public OrderResponse placeOrder(String customerMobileNo) {
        //validate customer
        if(!validationUtils.validateCustomerMobile(customerMobileNo)){
            throw new CustomerNotFoundException("Customer doesn't exists !!!");
        }
        Customer customer = customerRepository.findByMobileNo(customerMobileNo);
       //verify if card is empty or not
        Cart cart = customer.getCart();
        if(cart.getFoodItems().size()==0){
            throw new EmptyCartException("Sorry !! Your cart is empty");
        }
        Restaurant restaurant = cart.getFoodItems().get(0).getMenuItem().getRestaurant();
        // Randomly assign delivery partner
        DeliveryPartner deliveryPartner = deliveryPartnerRepository.findRandomDeliveryPartner();
        // prepare orderEntity
        OrderEntity order = OrderEntityTransformer.prepareOrderEntity(cart);
        // save order
        OrderEntity savedOrder = orderRepository.save(order);
        //set remaining attributes
        order.setCustomer(customer);
        order.setDeliveryPartner(deliveryPartner);
        order.setRestaurant(restaurant);
        order.setFoodItems(cart.getFoodItems());
        // handle bidirectional mapping relations
        customer.getOrders().add(savedOrder);
        deliveryPartner.getOrders().add(savedOrder);
        restaurant.getOrders().add(savedOrder);
        // from cart to order
        for (FoodItem foodItem: cart.getFoodItems()){
            foodItem.setCart(null);
            foodItem.setOrder(savedOrder);
        }
        clearCart(cart);

        customerRepository.save(customer);
        deliveryPartnerRepository.save(deliveryPartner);
        restaurantRepository.save(restaurant);

        //prepare response
        return OrderEntityTransformer.OrderEntityToOrderResponse(savedOrder);
    }

}
