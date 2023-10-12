package com.example.Swiggato.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {

    double cartTotal;

   List<FoodResponse> foodItems;
}
