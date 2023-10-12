package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.response.FoodResponse;
import com.example.Swiggato.dto.response.OrderResponse;
import com.example.Swiggato.model.Cart;
import com.example.Swiggato.model.FoodItem;
import com.example.Swiggato.model.OrderEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderEntityTransformer {

    public static OrderEntity prepareOrderEntity(Cart cart){
        return OrderEntity.builder()
                .orderId(String.valueOf(UUID.randomUUID()))
                .orderTotal(cart.getCartTotal())
                .build();
    }

    public static OrderResponse OrderEntityToOrderResponse (OrderEntity orderEntity){
        List<FoodResponse> foodResponseList = new ArrayList<>();
        for (FoodItem foodItem : orderEntity.getFoodItems()){
            foodResponseList.add(FoodItemTransformer.FoodItemToFoodResponse(foodItem));
        }
        return OrderResponse.builder()
                .orderId(orderEntity.getOrderId())
                .orderTotal(orderEntity.getOrderTotal())
                .orderTime(orderEntity.getOrderTime())
                .restaurantName(orderEntity.getRestaurant().getName())
                .customerName(orderEntity.getCustomer().getName())
                .customerMobile(orderEntity.getCustomer().getMobileNo())
                .deliveryPartnerName(orderEntity.getDeliveryPartner().getName())
                .deliveryPartnerMobile(orderEntity.getDeliveryPartner().getMobileNo())
                .foodItems(foodResponseList)
                .build();
    }
}
