package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.request.MenuRequest;
import com.example.Swiggato.dto.response.MenuResponse;
import com.example.Swiggato.model.MenuItem;

public class MenuItemTransformer {

    public static MenuItem MenuRequestToMenuItem(MenuRequest menuRequest){
        return MenuItem.builder()
                .dishName(menuRequest.getDishName())
                .veg(menuRequest.isVeg())
                .price(menuRequest.getPrice())
                .category(menuRequest.getCategory())
                .available(menuRequest.isAvailable())
                .build();
    }

    public static MenuResponse MenuItemToMenuResponse(MenuItem menuItem){
        return MenuResponse.builder()
                .dishName(menuItem.getDishName())
                .foodCategory(menuItem.getCategory())
                .price(menuItem.getPrice())
                .veg(menuItem.isVeg())
                .build();
    }
}
