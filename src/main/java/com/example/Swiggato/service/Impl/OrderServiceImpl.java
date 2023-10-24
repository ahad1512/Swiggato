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
import com.example.Swiggato.transformer.FoodItemTransformer;
import com.example.Swiggato.transformer.OrderEntityTransformer;
import com.example.Swiggato.utils.ValidationUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    final ValidationUtils validationUtils;
    final OrderRepository orderRepository;
    final CustomerRepository customerRepository;
    final DeliveryPartnerRepository deliveryPartnerRepository;
    final RestaurantRepository restaurantRepository;

    final JavaMailSender javaMailSender;
    public OrderServiceImpl(ValidationUtils validationUtils, OrderRepository orderRepository, CustomerRepository customerRepository, DeliveryPartnerRepository deliveryPartnerRepository, RestaurantRepository restaurantRepository, JavaMailSender javaMailSender) {
        this.validationUtils = validationUtils;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.deliveryPartnerRepository = deliveryPartnerRepository;
        this.restaurantRepository = restaurantRepository;
        this.javaMailSender = javaMailSender;
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

        // making order list for email
        String orderList="";
        for (FoodItem foodItem: savedOrder.getFoodItems()){
            orderList += "Dish name: "+ foodItem.getMenuItem().getDishName() + "\n"+
                    "Price: Rs "+ foodItem.getMenuItem().getPrice()+ "\n"+
                    "Quantity: "+ foodItem.getRequiredQuantity()+"\n"+
                    "\n";
        }

        // send an email
        String text =
                "Dear "+customer.getName()+",\n" +
                "\n" +
                "We're thrilled to confirm your recent order on Swiggato! Thank you for choosing us to satisfy your cravings. Your delicious meal is on its way to you.\n" +
                "\n" +
                "Order Details:\n" +
                "\n" +
                "Order Number: "+savedOrder.getOrderId()+"\n" +
                "Date & Time: "+savedOrder.getOrderTime()+"\n" +
                "Delivery Address: "+customer.getAddress()+"\n" +
                        "\n"+
                "Order Summary:\n" +"\n" + orderList+
                        "Restaurant Name: "+savedOrder.getRestaurant().getName()+"\n\n"+
                "Total Amount: Rs "+savedOrder.getOrderTotal()+"\n" +
                        "\n"+
                "Delivery Information:\n" +
                "\n" +
                "Delivery Driver: "+savedOrder.getDeliveryPartner().getName()+"\n"+
                        "Contact: "+savedOrder.getDeliveryPartner().getMobileNo()+"\n" +
                        "\n"+
                "We're working hard to prepare your order, and we'll keep you updated throughout the process. If you have any questions or need assistance, please feel free to reply to this email or call our customer support\n" +
                "\n" +
                "We hope you enjoy your meal and have a fantastic dining experience. Your satisfaction is our priority, and we look forward to serving you again soon.\n" +
                "\n" +
                "Thank you for choosing Swiggato!\n" +
                "\n" +
                "Best regards,\n" +
                "Swiggato\n";

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("springaccio18@gmail.com");
        simpleMailMessage.setTo(customer.getEmail());
        simpleMailMessage.setSubject("Your Food Delivery Order Confirmation !!");
        simpleMailMessage.setText(text);

        javaMailSender.send(simpleMailMessage);

        //prepare response
        return OrderEntityTransformer.OrderEntityToOrderResponse(savedOrder);
    }

}
