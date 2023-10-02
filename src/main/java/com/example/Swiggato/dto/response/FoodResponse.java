package com.example.Swiggato.dto.response;

import com.example.Swiggato.Enum.FoodCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodResponse {

    String dishName;

    double price;

    FoodCategory foodCategory;

    boolean veg;
}
